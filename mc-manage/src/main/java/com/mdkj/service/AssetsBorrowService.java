package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.assetsBorrow.AssetsBorrowExcel;
import com.mdkj.dto.assetsBorrow.AssetsBorrowInsertDTO;
import com.mdkj.dto.assetsBorrow.AssetsBorrowPageDTO;
import com.mdkj.dto.assetsBorrow.AssetsBorrowUpdateDTO;
import com.mdkj.entity.AssetsBorrow;

import java.util.List;

public interface AssetsBorrowService {

    //查询--条件分页
    PageInfo<AssetsBorrow> page(AssetsBorrowPageDTO dto);

    //查询--全部记录
    List<AssetsBorrow> selectAll();

    //查询--单个记录
    AssetsBorrow select(Long id);

    //添加--单个记录
    int insert(AssetsBorrowInsertDTO dto);

    //修改--单个记录
    int update(AssetsBorrowUpdateDTO dto);

    //删除--单个记录
    int delete(Long id);

    //删除--多个记录
    int deleteBatch(List<Long> ids);

    //获取Excel数据
    List<AssetsBorrowExcel> getExcelData();

    //归还申请资产
    int returnAssets(Long id);
}
