package com.hfkj.bbt.assetmanage;

import com.alibaba.fastjson.JSONObject;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.vo.ClassRoomAccessoryVo;

import java.util.List;

public interface IAccessoryInputService {

    List<DevelopType> findAllDevelop();

    /**
     * 根据教学楼查询教室列表
     * @param buildingId
     * @return
     */
    List<Object[]> findClassRoomsByBuilding(Long buildingId);

    List<AccessoryName> findAllAccessName();

    List<SpecificationModel> findAllModel();

    List<Brand> findAllBrand();

    JSONObject findClassAndGrade(Long roomId);

    String deleteAccessoryById(Long accessoryId);

    void doSaveAccessories(ClassRoomAccessoryVo accessoryVo);

    List<Company> findAllCompany();

}
