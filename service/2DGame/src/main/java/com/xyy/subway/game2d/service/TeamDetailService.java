package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.dto.VTeamTypeDetailDTO;
import com.xyy.subway.game2d.error.BusinessException;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/11 15:38
 * @description
 */
public interface TeamDetailService {
    List<VTeamTypeDetailDTO> checkOtherBuildingTeamDetail(int defaultId) throws BusinessException;
    List<VBuildTeamTypeDetailDTO> checkBuildingTeamDetail(int defaultId) throws BusinessException;
}
