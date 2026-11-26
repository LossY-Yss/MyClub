package com.mdkj.dto.club;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索班级DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubPageDTO extends PageDTO {
    @Schema(description = "班级名称")
    private String title;
    @Schema(description = "班级标签")
    private String tag;
    @Schema(description = "方向ID")
    private Long fkDirectionId;
}
