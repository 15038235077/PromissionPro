package com.fz.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PageListRes
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 22:45
 * @Version 1.0.0
 **/
@Getter@Setter
public class PageListRes {
    private Long total;
    private List<?> rows = new ArrayList<>();
}
