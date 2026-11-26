package com.mdkj.dto.course;

import com.mdkj.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Schema(description = "课程添加DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInsertDTO implements Serializable {

    @Schema(description = "课程名称")
    @NotEmpty(message = "课程名称不能为空")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;

    @Schema(description = "课程分类")
    @NotEmpty(message = "课程分类不能为空")
    private String category;

    @Schema(description = "课程顺序")
    @NotNull(message = "课程顺序不能为空")
    @Min(value = 0, message = "课程顺序不能小于0")
    private Integer idx;

    @Schema(description = "课程学时")
    @NotNull(message = "课程学时不能为空")
    @Min(value = 0, message = "课程学时不能小于0")
    private Integer hours;

    @Schema(description = "课程描述")
    @Pattern(regexp = MC.Regex.INFO_RE, message = MC.Regex.INFO_RE_MSG)
    private String info;
}
