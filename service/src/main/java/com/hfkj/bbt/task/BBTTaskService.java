package com.hfkj.bbt.task;

import com.hfkj.bbt.activity.IActService;
import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.dataAnalysis.IUsedRecordService;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IEquipment;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import javax.lang.model.element.NestingKind;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

@Service
@EnableScheduling
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BBTTaskService {

    private static final Logger LOG=Logger.getLogger(BBTTaskService.class);
    @Autowired
    private IUsedRecordService usedRecordService;
    @Autowired
    private EquipmentSchoolRepository equipmentSchoolRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private EquipmentTeacherRrpository equipmentTeacherRrpository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private EquipmentClassRepository equipmentClassRepository;
    @Autowired
    private EquipmentSubjectRepository equipmentSubjectRepository;
    @Autowired
    private IEquipment equipmentservice;
    @Autowired
    private IActService actService;
    @Autowired
    private OpertionRepository opertionRepository;
    /**
     * 晚上23点59分触发 例子
     */
    @Scheduled(cron = "0 59 23 * * ?")
    @Transactional(readOnly = false)
    public void run(){

    }


    /**
     * 当天23点10分触发 保存每个学校每天的使用课时
     */
    @Scheduled(cron = "0 10 23 * * ?")
    @Transactional(readOnly = false)
    public void workScheduleUsed(){
        LOG.info("保存每天每个学校使用设备的课时------------开始");
        List<Object[]> list = usedRecordService.getSchoolClassWorkSchedule();
        List<Object[]> list1 = usedRecordService.getSchoolClassWorkScheduleAll();
        EquipmentSchool equipmentSchool;
        if (ComUtil.listNotNull(list1)){
            for (Object[] objects1:list1){
                equipmentSchool = new EquipmentSchool();
                equipmentSchool.setNumberAll(valueOf(objects1[0]));
                equipmentSchool.setSchool(schoolRepository.findOne(valueOf(objects1[1])));
                equipmentSchool.setNewDate(new Date());
                for (Object[] objects:list){
                    if(objects1[1].equals(objects[0])) {
                        equipmentSchool.setNumber(valueOf(objects[1]));
                    }
                }
                equipmentSchoolRepository.save(equipmentSchool);
            }
        }
        LOG.info("保存每天每个学校使用设备的课时------------结束");
    }

    /**
     * 当天23点20分触发 保存每个教师每天的使用课时
     */
    @Scheduled(cron = "0 20 23 * * ?")
    @Transactional(readOnly = false)
    public void workScheduleTeacherUsed(){
        LOG.info("保存每天每个教师使用设备的课时------------开始");
        List<Object[]> list = usedRecordService.getTeacherWorkSchedule();
        EquipmentTeacher equipmentTeacher;
        if (ComUtil.listNotNull(list)){
            for (Object[] objects:list){
                equipmentTeacher = new EquipmentTeacher();
                equipmentTeacher.setUser(userRepository.findOne(Long.valueOf(valueOf(objects[0]))));
                equipmentTeacher.setNumber(valueOf(objects[1]));
                equipmentTeacher.setNumberAll(valueOf(objects[2]));
                equipmentTeacher.setNewDate(new Date());
                equipmentTeacherRrpository.save(equipmentTeacher);
            }
        }
        LOG.info("保存每天每个教师使用设备的课时------------结束");
    }

    /**
     * 当天23点30分触发 保存每个班级每天的使用课时
     */
    @Scheduled(cron = "0 30 23 * * ?")
    @Transactional(readOnly = false)
    public void workScheduleClassUsed(){
        LOG.info("保存每天每个班级使用设备的课时------------开始");
        List<Object[]> list = usedRecordService.getClassWorkSchedule();
        EquipmentClass equipmentClass;
        if (ComUtil.listNotNull(list)){
            for (Object[] objects:list){
                equipmentClass = new EquipmentClass();
                equipmentClass.setClasses(classRepository.findOne(Long.valueOf(valueOf(objects[0]))));
                equipmentClass.setNumber(valueOf(objects[1]));
                equipmentClass.setNumberAll(valueOf(objects[2]));
                equipmentClass.setNewDate(new Date());
                equipmentClassRepository.save(equipmentClass);
            }
        }
        LOG.info("保存每天每个班级使用设备的课时------------结束");
    }

    /**
     * 当天23点40分触发 保存每个科目每天的使用课时
     */
    @Scheduled(cron = "0 40 23 * * ?")
    @Transactional(readOnly = false)
    public void workScheduleSubjectUsed(){
        LOG.info("保存每天每个科目使用设备的课时------------开始");
        List<Object[]> list = usedRecordService.getSubjectWorkSchedule();
        EquipmentSubject equipmentSubject;
        if (ComUtil.listNotNull(list)){
            for (Object[] objects:list){
                equipmentSubject = new EquipmentSubject();
                equipmentSubject.setSubject(subjectRepository.findOne(Long.valueOf(valueOf(objects[0]))));
                equipmentSubject.setNumber(valueOf(objects[1]));
                equipmentSubject.setSchool(schoolRepository.findOne(valueOf(objects[2])));
                equipmentSubject.setNewDate(new Date());
                equipmentSubjectRepository.save(equipmentSubject);
            }
        }
        LOG.info("保存每天每个科目使用设备的课时------------结束");
    }

    /**
     * 7天未使用生成异常
     */
    @Scheduled(cron = "0 50 23 * * ?")
    @Transactional(readOnly = false)
    public void checkUsed7Day(){
        LOG.info("检测设备7天使用情况------------开始");
        List<Object[]> list = equipmentservice.getOperationsEquipment();
        for(Object[] objects:list){
            if(Integer.valueOf(String.valueOf(objects[2]))==0){
                Operations operations=new Operations();
                operations.setClasses(classRepository.findOne(Long.valueOf(String.valueOf(objects[1]))));
                School school = schoolRepository.findOne(String.valueOf(objects[0]));
                operations.setSchool(school);
                operations.setBirthType(Operations.AUTOA);
                operations.setExceptionDescription("设备七天未使用");
                operations.setStartDate(new Date());
                Map<String, Object> param = new HashMap<>();
                param.put(Constants.SCHOOLCODE, Constants.SCHOOLCODE + ":" + school.getSchoolCode());
                String processId = actService.startProcessByKey(Operations.AUTOA, param);
                operations.setProcessId(processId);
                opertionRepository.save(operations);
            }
        }
        LOG.info("检测设备7天使用情况------------结束");
    }


    /**
     * 频繁使用
     */
    @Scheduled(cron = "0 59 23 * * ?")
    @Transactional(readOnly = false)
    public void toManyUsed(){
        LOG.info("检测设备是否频繁使用情况------------开始");
        List<Object[]> list = equipmentservice.getFrequentlyEquipment();
        for(Object[] objects:list){
            if(Integer.valueOf(String.valueOf(objects[2]))>10){
                Operations operations=new Operations();
                operations.setClasses(classRepository.findOne(Long.valueOf(String.valueOf(objects[1]))));
                School school = schoolRepository.findOne(String.valueOf(objects[0]));
                operations.setSchool(school);
                operations.setBirthType(Operations.AUTOA);
                operations.setExceptionDescription("设备频繁使用");
                operations.setStartDate(new Date());
                Map<String, Object> param = new HashMap<>();
                param.put(Constants.SCHOOLCODE, Constants.SCHOOLCODE + ":" + school.getSchoolCode());
                String processId = actService.startProcessByKey(Operations.AUTOA, param);
                operations.setProcessId(processId);
                opertionRepository.save(operations);
            }
        }
        LOG.info("检测设备是否频繁使用情况------------结束");

    }

}






















