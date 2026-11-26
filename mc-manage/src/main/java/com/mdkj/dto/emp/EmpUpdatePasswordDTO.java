package com.mdkj.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "更新员工密码DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpUpdatePasswordDTO {

    @NotNull(message = "员工ID不能为空")
    @Schema(description = "员工ID")
    private Long id;

    @NotNull(message = "旧密码不能为空")
    @Schema(description = "旧密码")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;
}
