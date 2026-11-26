package com.mdkj.dto.Direction;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索方向DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectionPageDTO extends PageDTO {
    @Schema(description = "方向名称")
    private String title;
}
