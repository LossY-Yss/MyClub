package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.constant.MC;
import com.mdkj.dto.menu.MenuExcel;
import com.mdkj.dto.menu.MenuInsertDTO;
import com.mdkj.dto.menu.MenuPageDTO;
import com.mdkj.dto.menu.MenuUpdateDTO;
import com.mdkj.entity.Menu;
import com.mdkj.entity.RoleMenu;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.MenuMapper;
import com.mdkj.service.MenuService;
import com.mdkj.vo.MenuVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Menu select(Long id) {
        return menuMapper.select(id);
    }

    @Override
    public List<MenuVO> list() {
        return menuMapper.list(new MenuPageDTO())
                .stream()
                .map(menu -> {
                    MenuVO menuVO = BeanUtil.copyProperties(menu, MenuVO.class);
                    if (ObjectUtil.isNotNull(menu.getParent())) {
                        menuVO.setParentTitle(menu.getParent().getTitle());
                    } else {
                        menuVO.setParentTitle("无");
                    }
                    return menuVO;

                }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<Menu> page(MenuPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(menuMapper.list(dto));
    }

    @Override
    public int insert(MenuInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Menu menu = BeanUtil.copyProperties(dto, Menu.class);
        // 设置默认值
        menu.setVersion(0L);
        menu.setDeleted(0);
        menu.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        menu.setCreated(LocalDateTime.now());
        menu.setUpdated(LocalDateTime.now());
        // DB添加
        int result = menuMapper.insert(menu);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public int update(MenuUpdateDTO dto) {
        Menu menu = menuMapper.select(dto.getId());
        if (ObjectUtil.isNull(menu)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, menu);
        // 设置默认值
        menu.setUpdated(LocalDateTime.now());
        // DB修改
        int result = menuMapper.update(menu);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        int result = menuMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        int result = menuMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<MenuExcel> getExcelData() {
        return menuMapper.list(new MenuPageDTO())
                .stream()
                .map(menu -> {
                    MenuExcel menuExcel = BeanUtil.copyProperties(menu, MenuExcel.class);
                    if (menu.getPid().equals(MC.Menu.ROOT_ID)) {
                        menuExcel.setParentTitle("无");
                    } else {
                        menuExcel.setParentTitle(menu.getParent().getTitle());
                    }
                    return menuExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> listByRoleId(Long roleId) {
        return menuMapper.listByRoleId(roleId);
    }

    @Transactional
    @Override
    public int updateByRoleId(Long roleId, List<Long> menuIds) {
        //删除该角色的全部中间表记录
        int deleteResult = menuMapper.deleteRoleMenuByRoleId(roleId);
        //创建该角色的新菜单列表
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setFkRoleId(roleId);
            roleMenu.setFkMenuId(menuId);
            roleMenu.setVersion(0L);
            roleMenu.setDeleted(0);
            roleMenu.setCreated(LocalDateTime.now());
            roleMenu.setUpdated(LocalDateTime.now());
            roleMenus.add(roleMenu);
        }
        //批量添加该角色的菜单记录（中间表）
        int insertResult = menuMapper.insertRoleMenuBatch(roleMenus);
        return deleteResult + insertResult;
    }

    @Override
    public List<Menu> listByEmpId(Long empId) {
        return menuMapper.listByEmpId(empId);
    }
}
