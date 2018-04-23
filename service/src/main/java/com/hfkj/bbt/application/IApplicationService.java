package com.hfkj.bbt.application;

import com.hfkj.bbt.vo.restvo.ControlVo;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30 0030.
 */
public interface IApplicationService {

//实时数据
    List findTimeData(String schoolName);

    List<Object[]> loadEnvironment(String schoolCode);
//查看详情
    List showDetail(String school);
    //开关
    String turnOnOff(ControlVo controlVo);
    //图表数据
    List<Object[]> loadUsedPercentChart();

    //查询使用记录
    List showUsedRecord();


    List<Object[]> loadClassesUsedDetail(Long classId);
}
