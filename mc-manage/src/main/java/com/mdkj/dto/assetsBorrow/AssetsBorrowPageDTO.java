package com.mdkj.dto.assetsBorrow;

import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索资产申请记录的DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsBorrowPageDTO extends PageDTO {

    @Schema(description = "资产ID")
    private Long fkAssetsId;

    @Schema(description = "员工ID")
    private Long fkEmpId;
}
