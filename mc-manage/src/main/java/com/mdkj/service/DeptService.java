package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.dept.DeptExcel;
import com.mdkj.dto.dept.DeptInsertDTO;
import com.mdkj.dto.dept.DeptPageDTO;
import com.mdkj.dto.dept.DeptUpdateDTO;
import com.mdkj.entity.Dept;
import com.mdkj.vo.DeptVO;

import java.util.List;

public interface DeptService {
    //查詢--全部
    List<DeptVO> list();

    //查詢--分頁條件
    PageInfo<Dept> page(DeptPageDTO dto);

    //查询--单条
    Dept select(Long id);

    //添加--单条
    int insert(DeptInsertDTO dto);

    //修改--单条
    int update(DeptUpdateDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //查询--获取Excel
    List<DeptExcel> getExcelData();
}
