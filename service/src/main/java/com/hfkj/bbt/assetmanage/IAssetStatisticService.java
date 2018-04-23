package com.hfkj.bbt.assetmanage;

import java.util.List;

public interface IAssetStatisticService {

    /**
     * 加载所有学校资产数据
     * @return
     */
    List<Object[]> loadAssetStatis();
    /**
     * 加载单个学校资产数据
     * @return
     */
    List<Object[]> loadSchoolAssetStatis(String schoolCode);

    /**
     * 加载班级资产数据
     * @return
     */
    List<Object[]> loadClassAssetStatis(Long classId);

    /**
     * 设备完好率统计图形
     * @return
     */
    List loadSchoolEqAccrssery();
}
