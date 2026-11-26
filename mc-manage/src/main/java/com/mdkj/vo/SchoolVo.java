package com.mdkj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolVo {
    private Long id;
    private String title;
    private String college;
    private String major;
    private String clazz;
    private String address;
}
