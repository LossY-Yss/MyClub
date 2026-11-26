package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.dto.room.RoomInsertDTO;
import com.mdkj.dto.room.RoomPageDTO;
import com.mdkj.dto.room.RoomUpdateDTO;
import com.mdkj.entity.Room;
import com.mdkj.result.Result;
import com.mdkj.service.RoomService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.RoomVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 油桃老师
 **/
@Tag(name = "房间模块")
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Operation(summary = "查询-全部记录")
    @GetMapping("/list")
    public Result<List<RoomVo>> list(){
        return new Result<>(roomService.selectRoomAll());
    }

    @Operation(summary = "查询-单条记录")
    @GetMapping("/select/{id}")
    public Result<Room> select(@PathVariable("id") Long id){
        return new Result<>(roomService.select(id));
    }

    @Operation(summary = "查询-条件分页")
    @GetMapping("/page")
    public Result<PageInfo<Room>> page(@Validated @ParameterObject RoomPageDTO dto){
        return new Result<>(roomService.page(dto));
    }

    @Operation(summary = "新增-单条记录")
    @PostMapping("/insert")
    public Result<Integer> insert(@RequestBody @Validated RoomInsertDTO dto){
        return new Result<>(roomService.insert(dto));
    }

    @Operation(summary = "删除-单条记录")
    @DeleteMapping("/delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id){
        return new Result<>(roomService.delete(id));
    }

    @Operation(summary = "删除-多条记录")
    @DeleteMapping("/deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids){
        return new Result<>(roomService.deleteBatch(ids));
    }

    @Operation(summary = "修改-单条记录")
    @PutMapping("/updated")
    public Result<Integer> update(@RequestBody @Validated RoomUpdateDTO dto){
        return new Result<>(roomService.update(dto));
    }

    @Operation(summary = "查询-报表打印")
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp){
        EasyExcelUtil.download(resp,"房间统计表",roomService.getExcelData());
    }
}
