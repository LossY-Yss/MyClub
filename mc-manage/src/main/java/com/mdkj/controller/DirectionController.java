package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.Direction.DirectionExcel;
import com.mdkj.dto.Direction.DirectionInsertDTO;
import com.mdkj.dto.Direction.DirectionPageDTO;
import com.mdkj.dto.Direction.DirectionUpdateDTO;
import com.mdkj.entity.Direction;
import com.mdkj.result.Result;
import com.mdkj.service.DirectionService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.DirectionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "方向接口")
@RestController
@RequestMapping("/api/v1/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @Operation(summary = "查询方向列表")
    @GetMapping("/list")
    public Result<List<DirectionVO>> list() {
        return new Result<>(directionService.list());
    }

    @Operation(summary = "查询方向分页列表")
    @GetMapping("/page")
    public Result<PageInfo<Direction>> page(@ParameterObject @Validated DirectionPageDTO dto) {
        return new Result<>(directionService.page(dto));
    }

    @Operation(summary = "查询方向详情")
    @GetMapping("/select/{id}")
    public Result<Direction> select(@PathVariable Long id) {
        return new Result<>(directionService.select(id));
    }

    @Operation(summary = "新增方向")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated DirectionInsertDTO dto) {
        return new Result<>(directionService.insert(dto));
    }

    @Operation(summary = "更新方向")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated DirectionUpdateDTO dto) {
        return new Result<>(directionService.update(dto));
    }

    @Operation(summary = "删除方向")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return new Result<>(directionService.delete(id));
    }

    @Operation(summary = "批量删除方向")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("list") List<Long> list) {
        return new Result<>(directionService.deleteBatch(list));
    }

    @Operation(summary = "导出方向列表")
    @GetMapping("/export")
    public void export(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "方向列表.xlsx", directionService.export());
    }
}
