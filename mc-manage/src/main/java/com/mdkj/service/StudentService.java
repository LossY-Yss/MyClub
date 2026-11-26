package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.student.StudentExcel;
import com.mdkj.dto.student.StudentInsertDTO;
import com.mdkj.dto.student.StudentPageDTO;
import com.mdkj.dto.student.StudentUpdateDTO;
import com.mdkj.entity.Student;
import com.mdkj.vo.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    int insert(StudentInsertDTO dto);
    Student select(Long id);
    List<StudentVO> list();
    PageInfo<Student> page(StudentPageDTO dto);
    int update(StudentUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);
    List<StudentExcel> getExcelData();
    String uploadAvatar(MultipartFile newFile, Long id);
}
