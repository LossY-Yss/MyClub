package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.school.SchoolExcel;
import com.mdkj.dto.school.SchoolInsertDTO;
import com.mdkj.dto.school.SchoolPageDTO;
import com.mdkj.dto.school.SchoolUpdateDTO;
import com.mdkj.entity.School;
import com.mdkj.vo.SchoolVo;

import java.util.List;

public interface SchoolService {

    //查询--全部记录
    List<SchoolVo> selectSchoolAll();

    //查询--单条记录
    School select(Long id);

    //查询--条件分页
    PageInfo<School> page(SchoolPageDTO dto);

    //添加--单条
    int insert(SchoolInsertDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //修改--单条
    Integer update(SchoolUpdateDTO dto);

    //获取学校记录的Excel
    List<SchoolExcel> getExcelData();
}
