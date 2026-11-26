package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.role.RoleExcel;
import com.mdkj.dto.role.RoleInsertDTO;
import com.mdkj.dto.role.RolePageDTO;
import com.mdkj.dto.role.RoleUpdateDTO;
import com.mdkj.entity.Role;
import com.mdkj.vo.RoleVO;

import java.util.List;

public interface RoleService {

    //添加--单条
    int insert(RoleInsertDTO dto);
    //查询--单条
    Role select(Long id);
    //查询--全部
    List<RoleVO> list();
    //查询--分页
    PageInfo<Role> page(RolePageDTO dto);
    //修改--单条
    int update(RoleUpdateDTO dto);
    //删除--单条
    int delete(Long id);
    //删除--多条
    int deleteBatch(List<Long> ids);
    //查询--报表
    List<RoleExcel> getExcelData();
    //查询--员工角色
    List<Role> listByEmpId(Long empId);
    //修改--员工角色
    int updateByEmpId(Long empId,List<Long> roleIds);
}
