package com.hfkj.bbt.dataAnalysis;

import java.util.List;

public interface IUsedRecordService {

    List getSchoolClassWorkSchedule();

    List getSchoolClassWorkScheduleAll();

    List getTeacherWorkSchedule();

    List getClassWorkSchedule();

    List getSubjectWorkSchedule();
}
