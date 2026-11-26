package com.mdkj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO implements Serializable {
    private Long id;
    private String realname;
}
