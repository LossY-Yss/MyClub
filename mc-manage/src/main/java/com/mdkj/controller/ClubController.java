package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.club.ClubInsertDTO;
import com.mdkj.dto.club.ClubPageDTO;
import com.mdkj.dto.club.ClubUpdateDTO;
import com.mdkj.entity.Club;
import com.mdkj.result.Result;
import com.mdkj.service.ClubService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.ClubVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "班级模块")
@RestController
@RequestMapping("/api/v1/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Operation(summary = "查询 - 全部记录")
    @GetMapping("/list")
    public Result<List<ClubVO>> list() {
        return new Result<>(clubService.list());
    }

    @Operation(summary = "查询 - 分页查询")
    @GetMapping("/page")
    public Result<PageInfo<Club>> page(@Validated @ParameterObject ClubPageDTO dto) {
        return new Result<>(clubService.page(dto));
    }

    @Operation(summary = "查询 - 单条记录")
    @GetMapping("/select/{id}")
    public Result<Club> select(@PathVariable("id") Long id) {
        return new Result<>(clubService.select(id));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated ClubInsertDTO dto) {
        return new Result<>(clubService.insert(dto));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated ClubUpdateDTO dto) {
        return new Result<>(clubService.update(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(clubService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(clubService.deleteBatch(ids));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "班级统计表", clubService.getExcelData());
    }

    @Operation(summary = "修改 - 结班")
    @PutMapping("/endClub/{id}")
    public Result<Integer> endClub(@PathVariable("id") Long id) {
        return new Result<>(clubService.endClub(id));
    }
}
