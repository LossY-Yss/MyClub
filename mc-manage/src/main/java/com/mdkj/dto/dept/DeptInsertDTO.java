package com.mdkj.dto.dept;

import com.mdkj.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "部门添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptInsertDTO implements Serializable {

    @Schema(description = "部门名称")
    @NotEmpty(message = "部门名称不能为空")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "部门描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;

    @Schema(description = "房间外键")
    @NotNull(message = "房间外键不能为空")
    private Long fkRoomId;
}