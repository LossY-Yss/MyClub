package com.mdkj.vo;

import com.mdkj.entity.Emp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "登录VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    private String token;

    private Emp emp;
}
