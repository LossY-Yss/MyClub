package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.*;
import com.mdkj.dto.assets.AssetsInsertDTO;
import com.mdkj.dto.assets.AssetsPageDTO;
import com.mdkj.dto.assets.AssetsUpdateDTO;
import com.mdkj.entity.Assets;
import com.mdkj.entity.School;
import com.mdkj.result.Result;
import com.mdkj.service.AssetsService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.AssetsVO;
import com.mdkj.vo.SchoolVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 油桃老师
 **/
@Tag(name = "资源模块")
@RestController
@RequestMapping("/api/v1/assets")
public class AssetsController {
    @Autowired
    private AssetsService assetsService;

    @Operation(summary = "查询--全部记录")
    @GetMapping("/list")
    public Result<List<AssetsVO>> list(){
        return new Result(assetsService.selectAssetsAll());
    }

    @Operation(summary = "查询--单条记录")
    @GetMapping("/select/{id}")
    public Result<Assets> select(@PathVariable("id") Long id){
        return new Result(assetsService.select(id));
    }

    @Operation(summary = "查询 - 条件分页")
    @GetMapping("/page")
    public Result<PageInfo<Assets>> page(@Validated @ParameterObject AssetsPageDTO dto){
        return new Result<>(assetsService.page(dto));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated AssetsInsertDTO dto) {
        return new Result<>(assetsService.insert(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(assetsService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(assetsService.deleteBatch(ids));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("/update")
    public Result<Integer> update(@RequestBody @Validated AssetsUpdateDTO dto) {
        return new Result<>(assetsService.update(dto));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "学校统计表", assetsService.getExcelData());
    }

    @Operation(summary = "上传 - 资产图片")
    @PostMapping("/uploadPicture/{id}")
    public Result<String> uploadPicture(@RequestParam("pictureFile") MultipartFile pictureFile,
                                        @PathVariable("id") Long id){
        return new Result<>(assetsService.uploadPicture(pictureFile,id));
    }

}
