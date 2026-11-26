package com.mdkj.dto.login;

import com.mdkj.constant.MC;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "登录DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Schema(description = "用户名")
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = MC.Regex.USERNAME_RE, message = MC.Regex.USERNAME_RE_MSG)
    private String username;

    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = MC.Regex.PASSWORD_RE, message = MC.Regex.PASSWORD_RE_MSG)
    private String password;
}
