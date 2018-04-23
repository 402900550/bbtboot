package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.entity.TimeTable;
import com.hfkj.bbt.repository.TimeTableRepository;
import com.hfkj.bbt.systemanage.IGradeService;
import com.hfkj.bbt.systemanage.ITimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableServiceImpl implements ITimeTableService {
    @Autowired
    private TimeTableRepository timeTableRepository;
    /**
     *  //返回学校年级
     *
     * @param grade@return
     */
    public List<TimeTable> findByGrade(Grade grade){
        return timeTableRepository.findByGradeOrderByStart(grade);
    }

    public int saveTables(List<TimeTable> array){
        try {
            for(TimeTable table :array){
                timeTableRepository.save(table);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int deleteTable(Long id){
        try {
                timeTableRepository.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public void initTimeLists(Grade grade){
        List<TimeTable> list = new ArrayList();
        TimeTable timeTable1 = new TimeTable("第一节","08:00","08:40",grade);
        TimeTable timeTable2 = new TimeTable("第二节","09:40","10:20",grade);
        TimeTable timeTable3 = new TimeTable("第三节","10:30","11:10",grade);
        TimeTable timeTable4 = new TimeTable("第四节","11:20","12:00",grade);
        TimeTable timeTable5 = new TimeTable("第五节","14:00","14:40",grade);
        TimeTable timeTable6 = new TimeTable("第六节","14:50","15:30",grade);
        TimeTable timeTable7 = new TimeTable("第七节","15:40","16:20",grade);
        TimeTable timeTable8 = new TimeTable("第八节","16:30","17:10",grade);
        list.add(timeTable1);
        list.add(timeTable2);
        list.add(timeTable3);
        list.add(timeTable4);
        list.add(timeTable5);
        list.add(timeTable6);
        list.add(timeTable7);
        list.add(timeTable8);
        timeTableRepository.save(list);
        }




}
