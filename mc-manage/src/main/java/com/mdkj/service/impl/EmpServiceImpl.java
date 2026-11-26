package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.constant.MC;
import com.mdkj.dto.emp.*;
import com.mdkj.dto.login.LoginDTO;
import com.mdkj.entity.Emp;
import com.mdkj.exception.IllegalParamException;
import com.mdkj.exception.ServerErrorException;
import com.mdkj.mapper.EmpMapper;
import com.mdkj.service.EmpService;
import com.mdkj.util.JwtUtil;
import com.mdkj.util.MinioUtil;
import com.mdkj.vo.EmpVO;
import com.mdkj.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<EmpVO> list() {
        return empMapper.list(new EmpPageDTO())
                .stream()
                .map(emp -> BeanUtil.copyProperties(emp, EmpVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<Emp> page(EmpPageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(empMapper.list(dto));
    }

    @Override
    public Emp select(Long id) {
        return empMapper.select(id);
    }

    @Override
    public int update(EmpUpdateDTO dto) {
        Emp emp = empMapper.select(dto.getId());
        if (ObjectUtil.isNull(emp)) {
            throw new ServerErrorException("记录不存在");
        }
        BeanUtil.copyProperties(dto, emp);
        // 设置默认值
        emp.setUpdated(LocalDateTime.now());
        // DB修改
        int result = empMapper.update(emp);
        if (result <= 0) {
            throw new ServerErrorException("DB修改失败");
        }
        return result;
    }

    @Override
    public String uploadAvatar(MultipartFile newFile, Long id) {
        //查询员工记录
        Emp emp = empMapper.select(id);
        if (ObjectUtil.isNull(emp)) {
            throw new ServerErrorException("记录不存在");
        }
        //获取旧文件（当前的员工头像）
        String avatar = emp.getAvatar();
        //生成新的文件名
        String newFileName = MinioUtil.randomFilename(newFile);
        //更新员工表中头像字段
        emp.setAvatar(newFileName);
        if (empMapper.update(emp) <= 0) {
            throw new ServerErrorException("DB更新失败");
        }
        try {
            //如果旧文件不是默认头像 删除旧文件
            if (!MC.Emp.DEFAULT_AVATAR.equals(avatar)) {
                MinioUtil.delete(avatar, MC.MinIO.AVATAR_DIR, MC.MinIO.BUCKET_NAME);
            }
            //上传新文件
            MinioUtil.upload(newFile, newFileName, MC.MinIO.AVATAR_DIR, MC.MinIO.BUCKET_NAME);
        } catch (Exception e) {
            throw new ServerErrorException("MinIo上传失败");
        }
        return newFileName;
    }

    @Override
    public int updatedPassword(EmpUpdatePasswordDTO dto) {
        //获取传递过来的旧密码和新密码
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        //查询员工记录
        Emp emp = empMapper.select(dto.getId());
        if(ObjectUtil.isNull(emp)){
            throw new ServerErrorException("记录不存在");
        }
        //判断密码
        if(!oldPassword.equals(emp.getPassword())){
            throw new IllegalParamException("原密码有误");
        }
        //更新密码
        int i = empMapper.updatePassword(dto);
        if(i <= 0){
            throw new ServerErrorException("修改密码失败");
        }
        return i;
    }

    @Override
    public int insert(EmpInsertDTO dto) {
        String info = dto.getInfo();
        String idcard = dto.getIdcard();
        // 拷贝属性
        Emp emp = BeanUtil.copyProperties(dto, Emp.class);
        // 密码加密
        emp.setPassword(SecureUtil.md5(emp.getPassword()));
        // 设置默认值
        emp.setInfo(StrUtil.isBlank(info) ? "暂无描述" : info);
        emp.setAvatar(MC.Emp.DEFAULT_AVATAR);
        emp.setGender(IdcardUtil.getGenderByIdCard(idcard));
        emp.setAge(IdcardUtil.getAgeByIdCard(idcard));
        emp.setProvince(IdcardUtil.getProvinceByIdCard(idcard));
        emp.setAddress("暂未添加详细住址");
        emp.setVersion(0L);
        emp.setDeleted(0);
        emp.setCreated(LocalDateTime.now());
        emp.setUpdated(LocalDateTime.now());
        // DB添加
        int result = empMapper.insert(emp);
        if (result <= 0) {
            throw new ServerErrorException("DB添加失败");
        }
        return result;
    }

    @Override
    public int delete(Long id) {
        int result = empMapper.delete(id);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑删除失败");
        }
        return result;
    }

    @Override
    public int deleteBatch(List<Long> ids) {
        int result = empMapper.deleteBatch(ids);
        if (result <= 0) {
            throw new ServerErrorException("DB逻辑批删失败");
        }
        return result;
    }

    @Override
    public List<EmpExcel> getExcelData() {
        return empMapper.list(new EmpPageDTO())
                .stream()
                .map(emp -> {
                    EmpExcel empExcel = BeanUtil.copyProperties(emp, EmpExcel.class);
                    if (ObjectUtil.isNotNull(emp.getDept())) {
                        empExcel.setDeptName(emp.getDept().getTitle());
                    }
                    empExcel.setGender(MC.Emp.genderFormat(emp.getGender()));
                    return empExcel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public LoginVO loginByAccount(LoginDTO dto) {
        //密码加密
        String password = SecureUtil.md5(dto.getPassword());
        //设置密码
        dto.setPassword(password);
        //DB查询
        Emp emp = empMapper.selectByAccount(dto);
        //非空判断
        if(ObjectUtil.isNull(emp)){
            throw new IllegalParamException("账号或密码有误");
        }
        //生成Token令牌
        String token = JwtUtil.build(emp.getId(), emp.getRealname(), emp.getAvatar());
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setEmp(emp);
        return loginVO;
    }

}
