package com.fjnu.assetsManagement.vo;

import com.fjnu.assetsManagement.dto.AccessMenuDto;
import com.fjnu.assetsManagement.dto.AccessRouteDto;
import lombok.Data;

import java.util.List;

@Data
public class SysInfoVo {
    private List<Long> userPermissions;
    private List<AccessMenuDto> accessMenus;
    private List<AccessRouteDto> accessRoutes;
}
