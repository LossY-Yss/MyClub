package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.role.RoleExcel;
import com.mdkj.dto.role.RoleInsertDTO;
import com.mdkj.dto.role.RolePageDTO;
import com.mdkj.dto.role.RoleUpdateDTO;
import com.mdkj.entity.EmpRole;
import com.mdkj.entity.Role;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.RoleMapper;
import com.mdkj.service.RoleService;
import com.mdkj.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int insert(RoleInsertDTO dto) {
        String info = dto.getInfo();
        // 拷贝属性
        Role role = BeanUtil.copyProperties(dto, Role.class);
        // 设置默认值
        role.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        role.setVersion(0L);
        role.setDeleted(0);
        role.setCreated(LocalDateTime.now());
        role.setUpdated(LocalDateTime.now());
        // DB添加
        int result = roleMapper.insert(role);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public Role select(Long id) {
        Role result = roleMapper.select(id);
        if (ObjectUtil.isNull(result)) {
            throw new ServerErrorException("记录不存在");
        }
        return result;
    }

    @Override
    public List<RoleVO> list() {
        return roleMapper.list(new RolePageDTO())
                .stream()
                .map(role -> BeanUtil.copyProperties(role, RoleVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<Role> page(RolePageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(roleMapper.list(dto));
    }

    @Override
    public int update(RoleUpdateDTO dto) {
        Role role = roleMapper.select(dto.getId());
        if (ObjectUtil.isNull(role)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, role);
        // 设置默认值
        role.setUpdated(LocalDateTime.now());
        // DB修改
        int result = roleMapper.update(role);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        int result = roleMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        int result = roleMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<RoleExcel> getExcelData() {
        return roleMapper.list(new RolePageDTO())
                .stream()
                .map(role -> BeanUtil.copyProperties(role, RoleExcel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> listByEmpId(Long empId) {
        return roleMapper.listByEmpId(empId);
    }

    @Transactional
    @Override
    public int updateByEmpId(Long empId, List<Long> roleIds) {
        //删除该员工的全部中间表记录
        int deleteResult = roleMapper.deleteEmpRoleByEmpId(empId);
        //创建该员工的新角色列表
        List<EmpRole> empRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            EmpRole empRole = new EmpRole();
            empRole.setFkEmpId(empId);
            empRole.setFkRoleId(roleId);
            empRole.setVersion(0L);
            empRole.setDeleted(0);
            empRole.setCreated(LocalDateTime.now());
            empRole.setUpdated(LocalDateTime.now());
            empRoles.add(empRole);
        }
        //批量添加该员工的角色记录（中间表）
        int insertResult = roleMapper.insertRoleBatch(empRoles);
        return deleteResult + insertResult;
    }


}
