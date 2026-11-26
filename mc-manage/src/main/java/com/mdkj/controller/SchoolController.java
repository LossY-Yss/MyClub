package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.school.SchoolInsertDTO;
import com.mdkj.dto.school.SchoolPageDTO;
import com.mdkj.dto.school.SchoolUpdateDTO;
import com.mdkj.entity.School;
import com.mdkj.result.Result;
import com.mdkj.service.SchoolService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.SchoolVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 油桃老师
 **/
@Tag(name = "学校模块")
@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Operation(summary = "查询--全部记录")
    @GetMapping("/list")
    public Result<List<SchoolVo>> list(){
        return new Result(schoolService.selectSchoolAll());
    }

    @Operation(summary = "查询--单条记录")
    @GetMapping("/select/{id}")
    public Result<School> select(@PathVariable("id") Long id){
        return new Result(schoolService.select(id));
    }

    @Operation(summary = "查询 - 条件分页")
    @GetMapping("/page")
    public Result<PageInfo<School>> page(@Validated @ParameterObject SchoolPageDTO dto){
        return new Result<>(schoolService.page(dto));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated SchoolInsertDTO dto) {
        return new Result<>(schoolService.insert(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(schoolService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(schoolService.deleteBatch(ids));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated SchoolUpdateDTO dto) {
        return new Result<>(schoolService.update(dto));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "学校统计表", schoolService.getExcelData());
    }

}
