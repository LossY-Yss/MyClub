package com.mdkj.dto.room;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "房间分页查询DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPageDTO extends PageDTO {

    @Schema(description = "房间标题")
    private String title;
}
