package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.emp.*;
import com.mdkj.dto.login.LoginDTO;
import com.mdkj.entity.Emp;
import com.mdkj.vo.EmpVO;
import com.mdkj.vo.LoginVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmpService {
    //查询--全部
    List<EmpVO> list();

    //查询--条件分页
    PageInfo<Emp> page(EmpPageDTO dto);

    //查询--单条
    Emp select(Long id);

    //修改--单条
    int update(EmpUpdateDTO dto);

    //上传员工头像
    String uploadAvatar(MultipartFile newFile, Long id);

    //修改--登录密码
    int updatedPassword(EmpUpdatePasswordDTO dto);

    //添加--单条
    int insert(EmpInsertDTO dto);

    //删除--单条
    int delete(Long id);

    //删除--多条
    int deleteBatch(List<Long> ids);

    //导出Excel
    List<EmpExcel> getExcelData();

    //账号登录
    LoginVO loginByAccount(LoginDTO dto);
}
