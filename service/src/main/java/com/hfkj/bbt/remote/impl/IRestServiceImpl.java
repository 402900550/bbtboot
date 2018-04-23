package com.hfkj.bbt.remote.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.remote.IRestService;


import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.DateUtil;
import com.hfkj.bbt.vo.restvo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017-12-27.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class IRestServiceImpl implements IRestService {


    private static Logger log = Logger.getLogger(IRestServiceImpl.class);
    private DataVo dataVo;
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UsedRecordRepository usedRecordRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RunTotalTimeRepository runTotalTimeRepository;

    @Override
    @Transactional(readOnly = false)
    public String doAnalysisData(String sendData) {
        try {
            this.dataVo = JSON.parseObject(sendData, DataVo.class);
        } catch (JSONException e) {
            log.error("数据格式不正确!");
            return "数据格式不正确!";
        }
        Header header = dataVo.getHeader();
        int dataType = getIntValue(header.getDataType());
        String equipmentNo = header.getEquipmentNo();
        int equipmentType = Integer.valueOf(header.getEquipmentType());

        Equipment equipment = equipmentRepository.findByEquipmentNoAndEquipmentType(equipmentNo, equipmentType);
        if (equipment == null) {
            equipment = new Equipment();
            equipment.setEquipmentNo(header.getEquipmentNo());
            equipment.setEquipmentType(getIntValue(header.getEquipmentType()));
        }
        equipment.setEquipmentIp(header.getIp());
        equipment.setCollectTime(new Date());
        String returnMsg1236 = getReturnMsg1236();
        //分支判断
        switch (dataType) {
            //数据类型1-设备实时状态命令班班通控制状态改变
            case 1:
                //记录实时运行状态
                equipmentRunStatus(equipment);
                //创建使用记录
                doSaveOrUpdateUsedRecord();
                equipmentRepository.save(equipment);
                //创建返回给设备的信息
                log.info("返回给网关的信息:" + returnMsg1236);
                return returnMsg1236;

            case 2:
                //数据类型2--电控 电控状态改变发送的命令
                updateDriveByWire(equipment);
                equipmentRepository.save(equipment);
                log.info("返回给网关的信息:" + returnMsg1236);
                return returnMsg1236;
            case 3:
                //数据类型3--环境监测
                updateEnvironment(equipment);
                equipmentRepository.save(equipment);
                log.info("返回给网关的信息:" + returnMsg1236);
                return returnMsg1236;
            case 4:
                //数据类型4---请求教师数据
                String returnMsg4 = getReturnMsg4();
                log.info("返回给网关的信息:" + returnMsg4);
                return returnMsg4;
            case 5:
                //数据类型5----教师认证
                String returnMsg5 = getReturnMsg5();
                log.info("返回给网关的信息:" + returnMsg5);
                return returnMsg5;
            case 6:
                //数据类型6------异常信息上报
                log.info("返回给网关的信息:" + returnMsg1236);
                return returnMsg1236;
            case 7:
                //数据类型7------异常信息上报
                String returnMsg7 = getReturnMsg7();
                log.info("返回给网关的信息:" + returnMsg7);
                return returnMsg7;
            case 8:
                String failType = header.getFailType();
                String msg="";
                if("1".equals(failType)){
                    msg="网络通信失败";
                }
                if("2".equals(failType)){
                    msg="485通信失败";
                }
                equipment.setRemark(msg);
                equipmentRepository.save(equipment);
                log.info("收到通讯失败的消息!");
            default:
                log.info("返回给网关的信息:exception");
                return "exception";
        }

    }

    private void updateEnvironment(Equipment equipment) {
        Environment environment = dataVo.getEnvironment();
        Header header = dataVo.getHeader();
        equipment.setPm25(environment.getPm25());
        equipment.setHumidity(environment.getHumidity());
        equipment.setIlluminate(environment.getIlluminate());
        equipment.setNoise(environment.getNoise());
        equipment.setTemperature(environment.getTemperature());

    }

    private void updateDriveByWire(Equipment equipment) {
        RunTime runtime = dataVo.getRuntime();
        equipment.setLightsStatus(getIntValue(runtime.getLights()));
        equipment.setSocketsStatus(getIntValue(runtime.getSockets()));
        equipment.setAirconditionStatus(getIntValue(runtime.getAirConditioning()));
        equipment.setFanStatus(getIntValue(runtime.getFan()));
        equipment.setSocketsPower(getIntValue(runtime.getPower1()));
        equipment.setLightPower(getIntValue(runtime.getPower2()));
        equipment.setFanPower(getIntValue(runtime.getPower3()));
        equipment.setAirconditionPower(getIntValue(runtime.getPower4()));
    }



    private String getReturnMsg7() {
        DataVo returnVo = new DataVo();
        Header returnHeader = new Header();
        Header header = dataVo.getHeader();
        returnHeader.setDataType(header.getDataType());
        returnHeader.setEquipmentNo(header.getEquipmentNo());
        returnHeader.setEquipmentType(header.getEquipmentType());
        returnHeader.setSchoolCode(header.getSchoolCode());
        returnHeader.setIp(header.getIp());
        returnHeader.setSuccess("1");
        returnHeader.setTime(DateUtil.tranDateToString("yyyyMMddHHmmss",new Date())+DateUtil.getCurrentWeekDay());
        returnVo.setHeader(returnHeader);

        Total total = dataVo.getTotal();
        RunTotalTime runTotalTime=new RunTotalTime();
        runTotalTime.setCollectTime(new Timestamp(new Date().getTime()));
        runTotalTime.setSchoolCode(header.getSchoolCode());
        runTotalTime.setEquipmentNo(header.getEquipmentNo());
        if (header.getEquipmentType()!=null){
            runTotalTime.setEquipmentType(Integer.valueOf(header.getEquipmentType()));
        }
        runTotalTime.setTime1(total.getBbtTotal());
        runTotalTime.setTime2(total.getPcTotal());
        runTotalTime.setTime3(total.getDisplayTotal());
        runTotalTime.setTime4(total.getDropTotal());
        runTotalTime.setTime5(total.getZtTotal());
        runTotalTimeRepository.save(runTotalTime);
        return JSON.toJSONString(returnVo);
    }
    private String getReturnMsg1236() {
        DataVo returnVo = new DataVo();
        Header returnHeader = new Header();
        Header header = dataVo.getHeader();
        returnHeader.setDataType(header.getDataType());
        returnHeader.setEquipmentNo(header.getEquipmentNo());
        returnHeader.setEquipmentType(header.getEquipmentType());
        returnHeader.setSchoolCode(header.getSchoolCode());
        returnHeader.setIp(header.getIp());
        returnHeader.setSuccess("1");
        returnHeader.setTime(DateUtil.tranDateToString("yyyyMMddHHmmss",new Date()));
        returnVo.setHeader(returnHeader);
        return JSON.toJSONString(returnVo);
    }


    private String getReturnMsg4() {
        DataVo returnVo = new DataVo();
        Header returnHeader = new Header();
        Header header = dataVo.getHeader();
        returnHeader.setDataType(header.getDataType());
        returnHeader.setEquipmentNo(header.getEquipmentNo());
        returnHeader.setEquipmentType(header.getEquipmentType());
        returnHeader.setSchoolCode(header.getSchoolCode());
        returnHeader.setIp(header.getIp());
        returnHeader.setSuccess("1");

        returnVo.setHeader(returnHeader);

        String version = header.getVersion();
        School school = schoolRepository.findOne(header.getSchoolCode());
        if (school!=null){
            //若版本号相同则不发送教师数据
            if (version.equals(String.valueOf(school.getVersion()))) {
                returnHeader.setVersion(version);
                return JSON.toJSONString(returnVo);
            }
            returnHeader.setVersion(String.valueOf(school.getVersion()));
            //查询数据库中的教师
            StringBuffer teacherData = new StringBuffer();
            List<User> userBySchoolCode = userRepository.findBySchool(schoolRepository.findOne(header.getSchoolCode()));
            if (ComUtil.listNotNull(userBySchoolCode)) {
                for (User user : userBySchoolCode) {
                    String icardNo = user.getIcardNo();
                    if (ComUtil.stringIsNotNull(icardNo)) {
                        teacherData.append(icardNo).append("#");
                    }
                }
            }
            returnVo.setTeacherData(teacherData.toString());
        }else {
            returnVo.setTeacherData("");
            returnHeader.setVersion(version);
        }

        return JSON.toJSONString(returnVo);
    }

    private String getReturnMsg5() {
        DataVo returnVo = new DataVo();
        Header returnHeader = new Header();
        Header header = dataVo.getHeader();
        returnHeader.setDataType(header.getDataType());
        returnHeader.setEquipmentNo(header.getEquipmentNo());
        returnHeader.setEquipmentType(header.getEquipmentType());
        returnHeader.setSchoolCode(header.getSchoolCode());
        returnHeader.setIp(header.getIp());
        returnHeader.setSuccess("1");
        returnHeader.setIcardNo(header.getIcardNo());

        returnVo.setHeader(returnHeader);
        User user = userRepository.findByIcardNo(header.getIcardNo());
        if (user != null) {
            returnHeader.setIcardNo(user.getIcardNo());
        } else {
            returnHeader.setIcardNo("");
        }
        return JSON.toJSONString(returnVo);
    }

    /**
     * 记录使用状态
     */
    private void doSaveOrUpdateUsedRecord() {

        Timestamp currentTime = new Timestamp(new Date().getTime());
        RunTime runtime = dataVo.getRuntime();
        Header header = dataVo.getHeader();
        int workStatus = getIntValue(runtime.getWorkStatus());
        int drop = getIntValue(runtime.getDrop());
        int display = getIntValue(runtime.getDisplay());
        int pc = getIntValue(runtime.getPc());
        int source = getIntValue(runtime.getPc());

        List<UsedRecord> records = usedRecordRepository.findByEquipmentOrderByCollectTimeDesc(equipmentRepository.findOne(header.getEquipmentNo()));
        UsedRecord record=null;
        if(ComUtil.listNotNull(records)){
            record=records.get(0);
        }
        if (record == null) {
            //使用记录为空则说明未使用
            //创建一条使用记录 无论是否开关
            record = createNewRecord(header, runtime);
            usedRecordRepository.save(record);

        } else {
            //若不为空
            //先判断这个最近记录设备的工作状态 若为1表示正在使用 0表示关闭状态
            Integer zt5 = record.getZt5();
            //其他最近状态
            Integer zt2 = record.getZt2();
            Integer zt3 = record.getZt3();
            Integer zt4 = record.getZt4();
            Integer zt1 = record.getZt1();
            //根据两个状态 判断 若都为1表示 其他状态改变  若最近的为1当前为0表示关闭 若最近为0当前为1表示重新开启
            if (zt5 == 1 && workStatus == 1) {
                //判断是否同一人使用
                if (record.getIcardNo().equals(header.getIcardNo())) {
                    //进入其他状态变更记录时间
                    //之前记录状态为关 现在状态为开 则说明开启电脑记录开启时间
                    if (zt2 == 0 && pc == 1) {
                        record.setZt2Start(currentTime);
                    }
                    //之前记录状态为开 现在状态为关 则说明关闭电脑
                    if (zt2 == 1 && pc == 0) {
                        record.setZt2End(currentTime);
                    }

                    if (zt3 == 0 && display == 1) {
                        record.setZt3Start(currentTime);
                    }
                    if (zt3 == 1 && display == 0) {
                        record.setZt3End(currentTime);
                    }

                    if (zt4 == 0 && drop == 1) {
                        record.setZt4Start(currentTime);
                    }
                    if (zt4 == 1 && drop == 0) {
                        record.setZt4End(currentTime);
                    }

                    if (zt1 == 1 && source == 2) {
                        record.setZt1Start(currentTime);
                    }
                    if (zt1 == 2 && source == 1) {
                        record.setZt1End(currentTime);
                    }
                    record.setZt1(source);
                    record.setZt2(pc);
                    record.setZt3(display);
                    record.setZt4(drop);
                    usedRecordRepository.save(record);
                } else {
                    //此时表示上一个人 未关闭设备 此处后续记录此人
                    UsedRecord newRecord = createNewRecord(header, runtime);
                    usedRecordRepository.save(newRecord);
                    if (zt1 == 1) {
                        record.setZt1(0);
                        record.setZt1End(currentTime);
                    }
                    if (zt2 == 1) {
                        record.setZt2(0);
                        record.setZt2End(currentTime);
                    }
                    if (zt3 == 1) {
                        record.setZt3(0);
                        record.setZt3End(currentTime);
                    }
                    if (zt4 == 1) {
                        record.setZt4(0);
                        record.setZt4End(currentTime);
                    }
                    record.setZt5(0);
                    record.setZt5End(currentTime);
                    usedRecordRepository.save(record);
                }



            }
            if (zt5 == 0 && workStatus == 1) {
                //进入重新开启机器 需要新建一条记录
                record = createNewRecord(header, runtime);
                usedRecordRepository.save(record);
            }

            if (zt5 == 1 && workStatus == 0) {
                //表示设备关闭 设置结束时间
                record.setZt5(workStatus);
                record.setZt5End(currentTime);
                if (zt2 == 1) {
                    record.setZt2End(currentTime);
                }
                if (zt3 == 1) {
                    record.setZt3End(currentTime);
                }
                if (zt4 == 1) {
                    record.setZt4End(currentTime);
                }
                if (zt1 == 2) {
                    record.setZt1End(currentTime);
                }
                record.setZt1(1);
                record.setZt2(0);
                record.setZt3(0);
                record.setZt4(0);
                usedRecordRepository.save(record);

            }
        }

    }

    /**
     * 创建新的使用记录
     *
     * @param header
     * @param runTime
     * @return
     */
    private UsedRecord createNewRecord(Header header, RunTime runTime) {
        Timestamp currentTime = new Timestamp(new Date().getTime());

        Integer pc = getIntValue(runTime.getPc());
        Integer display = getIntValue(runTime.getDisplay());
        Integer drop = getIntValue(runTime.getDrop());
        Integer workStatus = getIntValue(runTime.getWorkStatus());
        Integer xy = getIntValue(runTime.getSource());

        UsedRecord record = new UsedRecord();
        record.setSchool(schoolRepository.findOne(header.getSchoolCode()));
        record.setZt5Start(currentTime);
        record.setIcardNo(header.getIcardNo());
        record.setEquipment(equipmentRepository.findOne(header.getEquipmentNo()));
        record.setCollectTime(currentTime);
        //设置设备开启时间
        record.setZt5Start(currentTime);
        record.setZt1(xy);
        record.setZt2(pc);
        record.setZt3(display);
        record.setZt4(drop);
        record.setZt5(workStatus);
        if (pc == 1) {
            record.setZt2Start(currentTime);
        }
        if (display == 1) {
            record.setZt3Start(currentTime);
        }
        if (drop == 1) {
            record.setZt4Start(currentTime);
        }
        if (xy == 2) {
            record.setZt1Start(currentTime);
        }
        //如果传过来的状态是关闭 则设置关闭时间
        if (workStatus == 0) {
            record.setZt5End(currentTime);
        }
        return record;
    }

    /**
     * 分析设备运行状态
     *
     * @param equipment
     */
    private void equipmentRunStatus(Equipment equipment) {
        RunTime runtime = dataVo.getRuntime();
        Header header = dataVo.getHeader();
        equipment.setWorkStatus(getIntValue(runtime.getWorkStatus()));
        equipment.setPcStatus(getIntValue(runtime.getPc()));
        equipment.setDisplayStatus(getIntValue(runtime.getDisplay()));
        equipment.setMsgsourceStatus(getIntValue(runtime.getSource()));
        equipment.setDropStatus(getIntValue(runtime.getDrop()));
        equipment.setIsUsed(1);
        equipment.setPower(dataVo.getPower());
        equipment.setIcardNo(header.getIcardNo());
        equipment.setHeartTime(DateUtil.tranStringToDate("yyyyMMddHHmmss",header.getTime()));
    }

    /**
     * 得到int值
     *
     * @param value
     * @return
     */
    private int getIntValue(String value) {
        if (value != null) {
            return Integer.valueOf(value);
        }
        return 0;
    }

    public DataVo getDataVo() {
        return dataVo;
    }

    public void setDataVo(DataVo dataVo) {
        this.dataVo = dataVo;
    }
}
