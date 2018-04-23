package com.hfkj.bbt.systemanage;

import java.util.List;

public interface IEquipment {

    /**
     * 首页学校使用率展示
     * @return
     */
    List loadEquipmentSchoolAll();

    /**
     * 首页学科使用课时数展示
     * @return
     */
    List loadEquipmentSubjectAll();

    /**
     * 首页教师使用率展示
     * @return
     */
    List loadEquipmentTeacherAll();

    /**
     * 首页设备完好数完好率展示
     * @return
     */
    List loadEquipmentAccesssory();

    /**
     * 首页资产图表
     * @return
     */
    List loadEquipmentSchoolT();

//    /**
//     * 查询所有设备号
//     * @return
//     */
//    List loadClassRoomEquipmentNo();

    /**
     * 七天未使用
     * @return
     */
    List getOperationsEquipment();

    /**
     * 频繁使用
     * @return
     */
    List getFrequentlyEquipment();
}
