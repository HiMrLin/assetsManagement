package com.fjnu.assetsManagement.dto;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccessRouteDto {
    private Long id;
    private String level;
    private Integer seq;
    private String name;
    private String path;
    private String component;
    private String componentPath;
    private MetaDto meta = new MetaDto();
    private List<AccessRouteDto> children = Lists.newArrayList();

}
