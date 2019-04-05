package com.fz.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Menu {
    private Long id;

    private String text;

    private String url;

    private Menu parent;

}