package com.mdkj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptVO implements Serializable {
    /** 主键 */
    private Long id;
    /** 部门名称 */
    private String title;
}