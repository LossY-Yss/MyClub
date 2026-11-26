package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.assets.AssetsExcel;
import com.mdkj.dto.assets.AssetsInsertDTO;
import com.mdkj.dto.assets.AssetsPageDTO;
import com.mdkj.dto.assets.AssetsUpdateDTO;
import com.mdkj.entity.Assets;
import com.mdkj.vo.AssetsVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssetsService {

    //查询--全部记录
    List<AssetsVO> selectAssetsAll();

    //查询--单条记录
    Assets select(Long id);

    //查询--条件分页
    PageInfo<Assets> page(AssetsPageDTO dto);

    //添加--单条
    int insert(AssetsInsertDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改--单条
    Integer update(AssetsUpdateDTO dto);

    //获取学校记录的Excel
    List<AssetsExcel> getExcelData();

    //上传资产图片
    String uploadPicture(MultipartFile newFile, Long id);
}
