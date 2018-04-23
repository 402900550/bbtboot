package com.hfkj.bbt.dataAnalysis;

import java.util.List;

public interface IDataAnalysisService {


    /**
     * 区县学校使用率展示
     * @return
     */
    List loadDistinctSchoolALL(String distinctCode);

    /**
     * 区县学科使用课时数展示
     * @return
     */
    List loadDistinctSchoolSubject(String distinctCode);

    /**
     * 区县教师使用率展示
     * @return
     */
    List loadDistinctSchoolTeacher(String distinctCode);

    /**
     * 区县班级使用率展示
     * @return
     */
    List loadDistinctSchoolClass(String distinctCode);

    /**
     * 区县设备完好数完好率展示
     * @return
     */
    List loadDistinctEquipmentAccesssory(String distinctCode);

    /**
     * 区县设备使用率展示
     * @return
     */
    List loadDistinctEquipmentClassRoom(String distinctCode);

    /**
     * 区县设备运维使用率
     * @param distinctCode
     * @return
     */
    List loadOpertionEquipment(String distinctCode);
}
