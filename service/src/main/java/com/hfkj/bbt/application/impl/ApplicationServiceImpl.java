package com.hfkj.bbt.application.impl;

import com.alibaba.fastjson.JSON;
import com.hfkj.bbt.application.IApplicationService;
import com.hfkj.bbt.constant.Constants;
import com.hfkj.bbt.entity.Equipment;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.entity.User;
import com.hfkj.bbt.repository.AccessoryRepository;
import com.hfkj.bbt.repository.EquipmentRepository;
import com.hfkj.bbt.repository.SchoolRepository;
import com.hfkj.bbt.repository.UserRepository;
import com.hfkj.bbt.util.ComUtil;
import com.hfkj.bbt.util.HttpClientApi;
import com.hfkj.bbt.util.UserUtil;
import com.hfkj.bbt.vo.restvo.ControlVo;
import com.hfkj.bbt.vo.restvo.DataVo;
import com.hfkj.bbt.vo.restvo.Header;
import com.hfkj.bbt.vo.restvo.RunTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30 0030.
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApplicationServiceImpl implements IApplicationService {

    private static final Logger log = Logger.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private AccessoryRepository accessoryRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询实时数据每个学校
     *
     * @param schoolName
     * @return
     */
    @Override
    public List<Object[]> findTimeData(String schoolName) {
        String jpql = "SELECT " +
                "  s.name " +
                " , (SELECT count(distinct c1 ) FROM School s1 LEFT JOIN s1.grades g1 LEFT JOIN g1.classes c1 WHERE s1.schoolCode=s.schoolCode) " +
                " , (SELECT count(distinct sc) FROM School s2 left join s2.grades sg left join sg.classes sc left join sc.classRoom scr WHERE s2.schoolCode=s.schoolCode" +
                " and sc.classRoom is not null and scr.equipment is not null ) " +
                " , (SELECT count(distinct sc) FROM School s2 left join s2.grades sg left join sg.classes sc left join sc.classRoom scr WHERE s2.schoolCode=s.schoolCode " +
                " and sc.classRoom is not null and scr.equipment is not null and scr.equipment.workStatus=1 ) " +
                " , s.schoolCode " +
                " FROM School s  ORDER BY s.schoolCode ";

        List<Object[]> list = accessoryRepository.findListByJpql(jpql);
        return list;
    }

    /**
     * 查询单个学校详情
     *
     * @param schoolCode
     * @return
     */
    @Override
    public List<Object[]> showDetail(String schoolCode) {
        String sql = "SELECT " +
                " CONCAT(g.name,cl.class_name),    " +
                "u.real_name," +
                "sb.subject_name," +
                "eq.work_status,  " +
                "eq.pc_status," +
                "eq.display_status," +
                "eq.msgsource_status, " +
                "eq.lights_status," +
                "eq.sockets_status, " +
                "eq.aircondition_status," +
                "eq.fan_status ," +
                "eq.remark, " +
                "eq.equipment_no," +
                "eq.light_power," +
                "eq.sockets_power ," +
                "eq.aircondition_power," +
                "eq.fan_power  " +
                " FROM " +
                "tab_school s " +
                "LEFT JOIN tab_grade g on g.school_school_code=s.school_code " +
                "  LEFT JOIN tab_classes cl on cl.grade_id = g.id " +
                "        LEFT JOIN tab_classroom cr ON cl.class_room_id = cr.id " +
                "        LEFT JOIN tab_equipment eq ON cr.equipment_no = eq.equipment_no  " +
                "        LEFT JOIN tab_user u ON eq.icard_no = u.icard_no  " +
                "        LEFT JOIN tab_subject sb on u.subject_id = sb.id " +
                " WHERE s.school_code = :schoolCode AND cl.id is not null " +
                "GROUP BY cl.id ORDER BY cl.class_name desc ";
        Map<String, Object> param = new HashMap();
        param.put("schoolCode", schoolCode);
        List<Object[]> list = accessoryRepository.findListBySql(sql, param);
        return list;
    }


    private boolean canUsedOnOff(String schoolCode) {
        Role role = UserUtil.getCurrentMostHighRole();

        String roleName = role.getRoleName();
        if (Constants.HUANFANGADMIN.equals(roleName)) {
            return true;
        }
        if(Constants.SCHOOLADMIN.equals(roleName)){
            User one = userRepository.findOne(UserUtil.getCurrentUser().getId());
            School school = one.getSchool();
            if(school!=null){
                if(schoolCode.equals(school.getSchoolCode())){
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 执行开关操作
     *
     * @param controlVo
     * @return
     */
    public String turnOnOff(ControlVo controlVo) {
        if(!canUsedOnOff(controlVo.getSchoolCode())){
            return "没有权限!";
        }

        School school = schoolRepository.findOne(controlVo.getSchoolCode());
        Equipment equipmentByNo = equipmentRepository.findOne(controlVo.getEquipmentNo());
        if (school != null) {
            String ip = school.getGatewayIp();
            if (!ComUtil.stringIsNotNull(ip)) {
                return "请联系幻方管理员设置网关IP!";
            }
            DataVo dataVo = new DataVo();
            Header header = new Header();
            RunTime runTime = new RunTime();
            header.setDataType("2");
            header.setEquipmentNo(controlVo.getEquipmentNo());
            header.setEquipmentType("3");
            header.setSchoolCode(controlVo.getSchoolCode());
            if (equipmentByNo != null) {
                header.setIp(equipmentByNo.getEquipmentIp());
            }
            dataVo.setHeader(header);
            if ("air_conditioning".equalsIgnoreCase(controlVo.getControlType())) {
                runTime.setAirConditioning(String.valueOf(controlVo.getOrder()));
            }
            if ("lights".equalsIgnoreCase(controlVo.getControlType())) {
                runTime.setLights(String.valueOf(controlVo.getOrder()));
            }
            if ("sockets".equalsIgnoreCase(controlVo.getControlType())) {
                runTime.setSockets(String.valueOf(controlVo.getOrder()));
            }
            if ("fan".equalsIgnoreCase(controlVo.getControlType())) {
                runTime.setFan(String.valueOf(controlVo.getOrder()));
            }
            dataVo.setRuntime(runTime);
            HttpClientApi clientApi = new HttpClientApi("http://" + ip + ":58890/send");
            String post = clientApi.post(JSON.toJSONString(dataVo));
            log.info("电控返回的数据:" + post);

            return post;
        }

        return "操作失败!请检查学校单位代码是否正确!";
    }


    /**
     * 查询环境监测数据
     *
     * @param schoolCode
     * @return
     */
    @Override
    public List<Object[]> loadEnvironment(String schoolCode) {
        String jpql = "SELECT " +
                " CONCAT(sg.name,sc.className)," +
                " se.temperature," +
                " se.humidity," +
                " se.pm25," +
                " se.illuminate," +
                " se.noise  " +
                " FROM School s LEFT JOIN s.grades sg LEFT JOIN sg.classes sc LEFT JOIN sc.classRoom scr " +
                " LEFT JOIN scr.equipment se WHERE s.schoolCode=:schoolCode";
        Map<String, Object> map = new HashMap<>();
        map.put("schoolCode", schoolCode);
        return accessoryRepository.findListByJpql(jpql, map);
    }


    public List<Object[]> loadUsedPercentChart() {

        String sql = "SELECT " +
                " school0_.NAME AS col_0_0_, " +
                "  " +
                " IFNULL(( " +
                "SELECT " +
                " count( DISTINCT classes6_.id )  " +
                "FROM " +
                " tab_school school4_ " +
                " LEFT OUTER JOIN tab_grade grades5_ ON school4_.school_code = grades5_.school_school_code " +
                " LEFT OUTER JOIN tab_classes classes6_ ON grades5_.id = classes6_.grade_id " +
                " LEFT OUTER JOIN tab_classroom classroom7_ ON classes6_.class_room_id = classroom7_.id, " +
                " tab_equipment equipment9_  " +
                "WHERE " +
                " classroom7_.equipment_no = equipment9_.equipment_no  " +
                " AND school4_.school_code = school0_.school_code  " +
                " AND ( classes6_.class_room_id IS NOT NULL )  " +
                " AND ( classroom7_.equipment_no IS NOT NULL )  " +
                " ) / " +
                " ( " +
                "SELECT " +
                " count( DISTINCT classes12_.id )  " +
                "FROM " +
                " tab_school school10_ " +
                " LEFT OUTER JOIN tab_grade grades11_ ON school10_.school_code = grades11_.school_school_code " +
                " LEFT OUTER JOIN tab_classes classes12_ ON grades11_.id = classes12_.grade_id " +
                " LEFT OUTER JOIN tab_classroom classroom13_ ON classes12_.class_room_id = classroom13_.id, " +
                " tab_equipment equipment15_  " +
                "WHERE " +
                " classroom13_.equipment_no = equipment15_.equipment_no  " +
                " AND school10_.school_code = school0_.school_code  " +
                " AND ( classes12_.class_room_id IS NOT NULL )  " +
                " AND ( classroom13_.equipment_no IS NOT NULL )  " +
                " AND equipment15_.work_status = 1  " +
                " ),0)*100 AS col_3_0_ " +
                "FROM " +
                " tab_school school0_  " +
                "ORDER BY " +
                " school0_.school_code ";

        return accessoryRepository.findListBySql(sql);


    }

    //查询使用记录
    public List showUsedRecord() {
        String sql = " SELECT    " +
                " ts.`name` taName,   " +
                " CONCAT(tg.`name`,tcs.class_name),   " +
                " tu.real_name tuName,   " +
                " tb.subject_name,   " +
                " tc.equipment_no,   " +
                " DATE_FORMAT(zt5_start,'%Y-%m-%d %H:%i:%s'),   " +
                " DATE_FORMAT(zt5_end,'%Y-%m-%d %H:%i:%s')   " +
                " FROM tab_usedrecord tud " +
                " LEFT JOIN tab_classroom tc on tc.equipment_no = tud.equipment_equipment_no   " +
                " LEFT JOIN tab_classes tcs on tcs.class_room_id = tc.id " +
                " LEFT JOIN tab_grade tg on tg.id = tcs.grade_id  " +
                " LEFT JOIN tab_school ts on tg.school_school_code = ts.school_code    " +
                " LEFT JOIN tab_user tu on tu.icard_no = tud.icard_no    " +
                " LEFT JOIN tab_subject tb on tb.id = tu.subject_id " +
                " WHERE ts.school_code is not null ";
        return accessoryRepository.findListBySql(sql);
    }


    @Override
    public List<Object[]> loadClassesUsedDetail(Long classId) {
        String sql = "SELECT " +
                " ttt.`name`, " +
                " ttt.`start`, " +
                "  ttt.`end`, " +
                //" CONCAT(tg.`name`,tcs.class_name), " +
                //" used.equipment_equipment_no, " +
                " DATE_FORMAT(used.zt5_start,'%Y-%m-%d %H:%i:%s'), " +
                " DATE_FORMAT(used.zt5_end,'%Y-%m-%d %H:%i:%s'), " +
                " u.real_name, " +
                " tsj.subject_name " +
                "FROM " +
                " tab_time_table ttt " +
                "LEFT JOIN tab_grade tg on tg.id=ttt.grade_id " +
                "LEFT JOIN tab_classes tcs on tcs.grade_id=tg.id " +
                "LEFT JOIN tab_classroom room on room.id=tcs.class_room_id " +
                "LEFT JOIN tab_equipment eq on eq.equipment_no=room.equipment_no " +
                "LEFT JOIN tab_usedrecord used on used.equipment_equipment_no=eq.equipment_no " +
                "LEFT JOIN tab_user u on u.icard_no=used.icard_no " +
                "LEFT JOIN tab_subject tsj on tsj.id=u.subject_id " +
                "WHERE " +
                "  DATE_FORMAT(used.collect_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') " +
                " and NOT (ttt.`start` > substring(used.zt5_end,12,8) OR ttt.`end` < substring(used.zt5_start,12,8)) " +
                " and tcs.id=:classId ";
        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);
        return accessoryRepository.findListBySql(sql, map);
    }

}
