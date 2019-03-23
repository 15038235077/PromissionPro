package com.fz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName QueryVo
 * @Description TODO
 * @Author fz
 * @Date 2019/3/23 12:39
 * @Version 1.0.0
 **/
@Getter@Setter@ToString
public class QueryVo {
    private int page;
    private int rows;
    private String keyword;
}
