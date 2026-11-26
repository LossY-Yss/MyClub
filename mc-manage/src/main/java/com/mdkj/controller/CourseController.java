package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.course.CourseExcel;
import com.mdkj.dto.course.CourseInsertDTO;
import com.mdkj.dto.course.CoursePageDTO;
import com.mdkj.dto.course.CourseUpdateDTO;
import com.mdkj.entity.Course;
import com.mdkj.result.Result;
import com.mdkj.service.CourseService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "课程接口")
@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Operation(summary = "查询课程列表")
    @GetMapping("/list")
    public Result<List<CourseVO>> list() {
        return new Result<>(courseService.list());
    }

    @Operation(summary = "查询课程分页列表")
    @GetMapping("/page")
    public Result<PageInfo<Course>> page(@ParameterObject @Validated CoursePageDTO dto) {
        return new Result<>(courseService.page(dto));
    }

    @Operation(summary = "查询课程详情")
    @GetMapping("/select/{id}")
    public Result<Course> select(@PathVariable Long id) {
        return new Result<>(courseService.select(id));
    }

    @Operation(summary = "新增课程")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated CourseInsertDTO dto) {
        return new Result<>(courseService.insert(dto));
    }

    @Operation(summary = "更新课程")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated CourseUpdateDTO dto) {
        return new Result<>(courseService.update(dto));
    }

    @Operation(summary = "删除课程")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return new Result<>(courseService.delete(id));
    }

    @Operation(summary = "批量删除课程")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("list") List<Long> list) {
        return new Result<>(courseService.deleteBatch(list));
    }

    @Operation(summary = "导出课程列表")
    @GetMapping("/export")
    public void export(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "课程列表.xlsx", courseService.export());
    }
}