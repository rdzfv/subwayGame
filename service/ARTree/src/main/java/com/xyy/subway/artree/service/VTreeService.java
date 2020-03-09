package com.xyy.subway.artree.service;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.artree.dto.UserTreeDTO;
import com.xyy.subway.artree.dto.VTreeTypeDetailDTO;
import com.xyy.subway.artree.entity.*;
import com.xyy.subway.artree.error.BusinessException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
public interface VTreeService {
    JSONObject newAVtreeByUserId(int userId, String name) throws BusinessException;
    ArrayList<VTreeTypeDetailDTO> checkTreeTypeDetail(int type) throws BusinessException;
    JSONObject getVTreeByUserId(int userId) throws BusinessException;
    JSONObject water(int userId, int userId2) throws BusinessException;
    String getATip() throws BusinessException;
    Gift2 getAGift2() throws BusinessException;
    Boolean getGiftActually(int id, int userId) throws BusinessException;
    List<UserTreeOpRecord> getNearFiveActivities(int userId) throws BusinessException;
    UserTree getVFriendTreeByUserId(int userId) throws BusinessException;
    VTreeType getVTreeDetail() throws BusinessException;
    VTreeUser getUserInfo(int userId) throws BusinessException;
    List<Gift2> getAllWaterGiftt2() throws BusinessException;
    List<Gift2> updateWaterGiftt2(Gift2 gift2) throws BusinessException;
    List<Gift2> addWaterGiftt2(Gift2 gift2) throws BusinessException;
    List<Tips> getAllTips() throws BusinessException;
    List<Tips> updateTipsById(int tipId, String content) throws BusinessException;
    List<Tips> addTipsById(String content) throws BusinessException;
    List<Tips> deleteTipsById(int tipId, String content) throws BusinessException;
}
