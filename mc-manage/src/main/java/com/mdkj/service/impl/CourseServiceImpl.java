package com.mdkj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mdkj.dto.course.CourseExcel;
import com.mdkj.dto.course.CourseInsertDTO;
import com.mdkj.dto.course.CoursePageDTO;
import com.mdkj.dto.course.CourseUpdateDTO;
import com.mdkj.entity.Course;
import com.mdkj.mapper.CourseMapper;
import com.mdkj.service.CourseService;
import com.mdkj.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseVO> list() {
        return courseMapper.list(new CoursePageDTO())
                .stream().map(course -> BeanUtil.copyProperties(course, CourseVO.class)).collect(Collectors.toList());
    }

    @Override
    public PageInfo<Course> page(CoursePageDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(courseMapper.list(dto));
    }

    @Override
    public Course select(Long id) {
        return courseMapper.select(id);
    }

    @Override
    public int insert(CourseInsertDTO dto) {
        Course course = BeanUtil.copyProperties(dto, Course.class);
        course.setVersion(0L);
        course.setDeleted(0);
        course.setCreated(LocalDateTime.now());
        course.setUpdated(LocalDateTime.now());
        return courseMapper.insert(course);
    }

    @Override
    public int update(CourseUpdateDTO dto) {
        Course course = BeanUtil.copyProperties(dto, Course.class);
        course.setUpdated(LocalDateTime.now());
        return courseMapper.update(course);
    }

    @Override
    public int delete(Long id) {
        return courseMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> list) {
        return courseMapper.deleteBatch(list);
    }

    @Override
    public List<CourseExcel> export() {
        List<Course> courses = courseMapper.list(new CoursePageDTO());
        List<CourseExcel> courseExcels = courses.stream().map(course -> BeanUtil.copyProperties(course, CourseExcel.class)).collect(Collectors.toList());
        return courseExcels;
    }
}