package com.mdkj.dto.course;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索课程DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePageDTO extends PageDTO {
    @Schema(description = "课程名称")
    private String title;
    @Schema(description = "课程分类")
    private String category;
}
