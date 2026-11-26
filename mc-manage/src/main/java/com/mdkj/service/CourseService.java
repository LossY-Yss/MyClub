package com.mdkj.service;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.course.CourseExcel;
import com.mdkj.dto.course.CourseInsertDTO;
import com.mdkj.dto.course.CoursePageDTO;
import com.mdkj.dto.course.CourseUpdateDTO;
import com.mdkj.entity.Course;
import com.mdkj.vo.CourseVO;
import java.util.List;

public interface CourseService {

    List<CourseVO> list();

    PageInfo<Course> page(CoursePageDTO dto);

    Course select(Long id);

    int insert(CourseInsertDTO dto);

    int update(CourseUpdateDTO dto);

    int delete(Long id);

    int deleteBatch(List<Long> list);

    List<CourseExcel> export();
}