package com.mdkj.controller;

import com.github.pagehelper.PageInfo;
import com.mdkj.constant.MC;
import com.mdkj.dto.menu.MenuInsertDTO;
import com.mdkj.dto.menu.MenuPageDTO;
import com.mdkj.dto.menu.MenuUpdateDTO;
import com.mdkj.entity.Menu;
import com.mdkj.result.Result;
import com.mdkj.service.MenuService;
import com.mdkj.util.EasyExcelUtil;
import com.mdkj.vo.MenuVO;
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
@Tag(name = "菜单模块")
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Operation(summary = "查询--单条")
    @GetMapping("/select/{id}")
    public Result<Menu> select(@PathVariable("id") Long id) {
        return new Result<>(menuService.select(id));
    }

    @Operation(summary = "查询--全部")
    @GetMapping("/list")
    public Result<List<MenuVO>> list() {
        return new Result<>(menuService.list());
    }

    @Operation(summary = "查询--条件分页")
    @GetMapping("/page")
    public Result<PageInfo> page(@Validated @ParameterObject MenuPageDTO dto) {
        return new Result<>(menuService.page(dto));
    }

    @Operation(summary = "新增 - 单条新增")
    @PostMapping("insert")
    public Result<Integer> insert(@RequestBody @Validated MenuInsertDTO dto) {
        return new Result<>(menuService.insert(dto));
    }

    @Operation(summary = "修改 - 单条修改")
    @PutMapping("update")
    public Result<Integer> update(@RequestBody @Validated MenuUpdateDTO dto) {
        return new Result<>(menuService.update(dto));
    }

    @Operation(summary = "删除 - 单条删除")
    @DeleteMapping("delete/{id}")
    public Result<Integer> delete(@PathVariable("id") Long id) {
        return new Result<>(menuService.delete(id));
    }

    @Operation(summary = "删除 - 批量删除")
    @DeleteMapping("deleteBatch")
    public Result<Integer> deleteBatch(@RequestParam("ids") List<Long> ids) {
        return new Result<>(menuService.deleteBatch(ids));
    }

    @Operation(summary = "查询 - 报表打印")
    @SneakyThrows
    @GetMapping("/excel")
    public void excel(HttpServletResponse resp) {
        EasyExcelUtil.download(resp, "菜单统计表", menuService.getExcelData());
    }

    @Operation(summary = "查询 - 角色菜单")
    @GetMapping("/listByRoleId/{roleId}")
    public Result<List<Menu>> listByRoleId(@PathVariable("roleId") Long roleId) {
        return new Result<>(menuService.listByRoleId(roleId));
    }

    @Operation(summary = "修改 - 角色菜单")
    @PutMapping("/updatedByRoleId")
    public Result<Integer> updatedByRoleId(@RequestParam("roleId") Long roleId,
                                           @RequestParam("menuIds") List<Long> menuIds) {
        return new Result<>(menuService.updateByRoleId(roleId, menuIds));
    }

    @Operation(summary = "查询 - 员工菜单")
    @GetMapping("/listByEmpId/{empId}")
    public Result<List<Menu>> listByEmpId(@PathVariable("empId") Long empId){
        return new Result<>(menuService.listByEmpId(empId));
    }
}
