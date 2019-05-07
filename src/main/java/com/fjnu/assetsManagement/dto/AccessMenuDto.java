package com.fjnu.assetsManagement.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccessMenuDto {
    private Long id;
    private String level;
    private Integer seq;
    private String title;
    private String path;
    private String icon;
    private List<AccessMenuDto> children = Lists.newArrayList();

}
