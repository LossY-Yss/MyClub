package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.menu.MenuExcel;
import com.mdkj.dto.menu.MenuInsertDTO;
import com.mdkj.dto.menu.MenuPageDTO;
import com.mdkj.dto.menu.MenuUpdateDTO;
import com.mdkj.entity.Menu;
import com.mdkj.vo.MenuVO;

import java.util.List;

public interface MenuService {

    //查询--单条
    //查询--单条
    Menu select(Long id);
    //查询--全部
    List<MenuVO> list();
    //查询--条件分页
    PageInfo<Menu> page(MenuPageDTO dto);
    //添加--单条
    int insert(MenuInsertDTO dto);
    //修改--单条
    int update(MenuUpdateDTO dto);
    //删除--单条
    int delete(Long id);
    //删除--多条
    int deleteBatch(List<Long> ids);
    //查询--报表
    List<MenuExcel> getExcelData();
    //查询--角色菜单
    List<Menu> listByRoleId(Long roleId);
    //修改--角色菜单
    int updateByRoleId(Long roleId,List<Long> menuIds);
    //查询--员工菜单
    List<Menu> listByEmpId(Long empId);
}
