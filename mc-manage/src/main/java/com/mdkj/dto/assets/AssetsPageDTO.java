package com.mdkj.dto.assets;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/** @author  */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索资产DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsPageDTO extends PageDTO {

    @Schema(description = "资产名称")
    private String title;
}
