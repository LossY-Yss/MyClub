package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.constant.MC;
import com.mdkj.dto.assets.AssetsExcel;
import com.mdkj.dto.assets.AssetsInsertDTO;
import com.mdkj.dto.assets.AssetsPageDTO;
import com.mdkj.dto.assets.AssetsUpdateDTO;
import com.mdkj.entity.Assets;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.AssetsMapper;
import com.mdkj.service.AssetsService;
import com.mdkj.util.MinioUtil;
import com.mdkj.vo.AssetsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsServiceImpl implements AssetsService {

    @Autowired
    private AssetsMapper assetsMapper;

    @Override
    public List<AssetsVO> selectAssetsAll() {
        return assetsMapper.selectAssetsAll();
    }

    @Override
    public Assets select(Long id) {
        return assetsMapper.select(id);
    }

    @Override
    public PageInfo<Assets> page(AssetsPageDTO dto) {
        //开启分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        //执行查询
        List<Assets> list = assetsMapper.list(dto);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(AssetsInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Assets assets = BeanUtil.copyProperties(dto, Assets.class);
        // 设置默认值
        assets.setPicture(MC.Assets.DEFAULT_ASSETS);
        assets.setStock(assets.getTotal());
        assets.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        assets.setVersion(0L);
        assets.setDeleted(0);
        assets.setCreated(LocalDateTime.now());
        assets.setUpdated(LocalDateTime.now());
        // DB添加
        int result = assetsMapper.insert(assets);
        return result;
    }

    @Override
    public int delete(Long id) {
        return assetsMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        return assetsMapper.deleteBatch(ids);
    }

    @Override
    public Integer update(AssetsUpdateDTO dto) {
        //根据id查询要修改的数据是否存在
        Assets assets = assetsMapper.select(dto.getId());
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("记录不存在");
        }
        //将dto（要修改的数据）的属性拷贝到 room（当前的数据）实现属性覆盖
        BeanUtil.copyProperties(dto, assets);
        //设置默认值
        assets.setUpdated(LocalDateTime.now());
        //执行mapper修改
        return assetsMapper.update(assets);
    }

    @Override
    public List<AssetsExcel> getExcelData() {
        return assetsMapper.list(new AssetsPageDTO())
                .stream()
                .map(assets -> BeanUtil.copyProperties(assets, AssetsExcel.class))
                .collect(Collectors.toList());
    }

    @Override
    public String uploadPicture(MultipartFile newFile, Long id) {
        //根据id查询资产记录
        Assets assets = assetsMapper.select(id);
        if (ObjectUtil.isNull(assets)) {
            throw new ServerErrorException("记录不存在");
        }
        //获取原有的文件名字
        String oldFile = assets.getPicture();
        //生成新的文件名
        String newFileName = MinioUtil.randomFilename(newFile);
        //更新DB中文件名字
        assets.setPicture(newFileName);
        if (assetsMapper.update(assets) <= 0) {
            throw new ServerErrorException("DB更新失败");
        }
        try {
            //删除MinIo旧文件（默认文件不删除）
            if (!MC.Assets.DEFAULT_ASSETS.equals(oldFile)) {
                MinioUtil.delete(oldFile, MC.MinIO.ASSETS_DIR, MC.MinIO.BUCKET_NAME);
            }
            //上传新文件
            MinioUtil.upload(newFile, newFileName, MC.MinIO.ASSETS_DIR, MC.MinIO.BUCKET_NAME);
        } catch (Exception e) {
            throw new ServerErrorException("MinIo操作失败" + e.getMessage());
        }
        return newFileName;
    }


}
