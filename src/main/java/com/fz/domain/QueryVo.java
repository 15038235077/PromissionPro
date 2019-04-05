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
    //分页
    private int page;
    //行数
    private int rows;
    //关键字
    private String keyword;
}
