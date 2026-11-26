package com.mdkj.dto.role;

import com.mdkj.constant.MC;
import com.mdkj.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "按条件分页搜索角色DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePageDTO extends PageDTO {
    @Schema(description = "角色名称")
    @Pattern(regexp = MC.Regex.TITLE_RE, message = MC.Regex.TITLE_RE_MSG)
    private String title;
}
