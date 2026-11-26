package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.assetsBorrow.AssetsBorrowExcel;
import com.mdkj.dto.assetsBorrow.AssetsBorrowInsertDTO;
import com.mdkj.dto.assetsBorrow.AssetsBorrowPageDTO;
import com.mdkj.dto.assetsBorrow.AssetsBorrowUpdateDTO;
import com.mdkj.entity.Assets;
import com.mdkj.entity.AssetsBorrow;
import com.mdkj.exception.IllegalParamException;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.AssetsBorrowMapper;
import com.mdkj.mapper.AssetsMapper;
import com.mdkj.result.Result;
import com.mdkj.service.AssetsBorrowService;
import org.apache.poi.util.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsBorrowServiceImpl implements AssetsBorrowService {

    @Autowired
    private AssetsBorrowMapper assetsBorrowMapper;
    @Autowired
    private AssetsMapper assetsMapper;

    @Override
    public PageInfo<AssetsBorrow> page(AssetsBorrowPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(assetsBorrowMapper.list(dto));
    }

    @Override
    public List<AssetsBorrow> selectAll() {
        return assetsBorrowMapper.list(new AssetsBorrowPageDTO());
    }

    @Override
    public AssetsBorrow select(Long id) {
        return assetsBorrowMapper.select(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(AssetsBorrowInsertDTO dto) {
        String info = dto.getInfo();
        Long fkAssetsId = dto.getFkAssetsId();
        Integer count = dto.getCount();
        //判断申请时间和预计归还时间
        if (dto.getBorrowTime().isAfter(dto.getExpectedReturnTime())) {
            throw new IllegalParamException("申请时间不能晚于预计归还时间");
        }
        //查询资产信息
        Assets assets = assetsMapper.select(fkAssetsId);
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("资产不存在");
        }
        //判断剩余库存
        Integer stock = assets.getStock();
        if (count > stock) {
            throw new IllegalParamException("库存不足，仅剩" + stock + assets.getStockUnit());
        }
        //DB添加：添加申请记录
        AssetsBorrow assetsBorrow = BeanUtil.copyProperties(dto, AssetsBorrow.class);
        assetsBorrow.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        assetsBorrow.setVersion(0L);
        assetsBorrow.setDeleted(0);
        assetsBorrow.setCreated(LocalDateTime.now());
        assetsBorrow.setUpdated(LocalDateTime.now());
        int insert = assetsBorrowMapper.insert(assetsBorrow);
        if (insert <= 0) {
            throw new ServerErrorException("添加申请记录失败");
        }
        //DB修改：修改资产表库存
        stock -= count;
        assets.setStock(stock);
        Integer update = assetsMapper.update(assets);
        if (update <= 0) {
            throw new ServerErrorException("资产库存扣减失败");
        }
        return insert + update;
    }

    @Override
    public int update(AssetsBorrowUpdateDTO dto) {
        //确保前端传递的资产申请ID数据是存在的
        AssetsBorrow assetsBorrow = assetsBorrowMapper.select(dto.getId());
        if (ObjectUtil.isNull(assetsBorrow)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, assetsBorrow);
        //设置更新时间
        assetsBorrow.setUpdated(LocalDateTime.now());
        //DB修改
        return assetsBorrowMapper.update(assetsBorrow);
    }

    @Override
    public int delete(Long id) {
        return assetsBorrowMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return assetsBorrowMapper.deleteBatch(ids);
    }

    @Override
    public List<AssetsBorrowExcel> getExcelData() {
        return assetsBorrowMapper.list(new AssetsBorrowPageDTO()).stream().map(assetsBorrow -> {
            AssetsBorrowExcel excel = new AssetsBorrowExcel();
            BeanUtil.copyProperties(assetsBorrow, excel);
            //给资产名称赋值
            if (ObjectUtil.isNotNull(assetsBorrow.getAssets())) {
                excel.setAssetsTitle(assetsBorrow.getAssets().getTitle());
            }
            //给员工姓名赋值
            if (ObjectUtil.isNotNull(assetsBorrow.getEmp())) {
                excel.setEmpName(assetsBorrow.getEmp().getRealname());
            }
            //设置是否已归还
            excel.setIsReturn(ObjectUtil.isNotNull(assetsBorrow.getReturnTime()) ? "已归还" : "未归还");
            //返回当前处理后的excel
            return excel;
        }).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public int returnAssets(Long id) {
        //查询资产申请记录
        AssetsBorrow assetsBorrow = assetsBorrowMapper.select(id);
        if (ObjectUtil.isNull(assetsBorrow)) {
            throw new ServerErrorException("资产申请记录不存在");
        }
        //查询资产信息
        Assets assets = assetsMapper.select(assetsBorrow.getFkAssetsId());
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("资产不存在");
        }
        //判断是否已归还
        if (ObjectUtil.isNotNull(assetsBorrow.getReturnTime())) {
            throw new ServerErrorException("资产已归还");
        }
        //DB修改：修改资产申请记录
        assetsBorrow.setReturnTime(LocalDateTime.now());
        int update = assetsBorrowMapper.update(assetsBorrow);
        if (update <= 0) {
            throw new ServerErrorException("修改资产申请记录失败");
        }
        //DB修改：修改资产库存
        assets.setStock(assets.getStock() + assetsBorrow.getCount());
        Integer update1 = assetsMapper.update(assets);
        if (update1 <= 0) {
            throw new ServerErrorException("修改资产库存失败");
        }
        return update + update1;
    }

}
