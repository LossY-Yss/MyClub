package com.mdkj.dto.school;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "学校分页查询DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolPageDTO extends PageDTO {

    @Schema(description = "学校标题")
    private String title;
}
