package com.hfkj.bbt.assetmanage.impl;

import com.hfkj.bbt.assetmanage.IAssetStatisticService;
import com.hfkj.bbt.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetStatisticServiceImpl implements IAssetStatisticService{

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public List<Object[]> loadAssetStatis() {

        String sql="SELECT   " +
                "ts.name tsname,   " +
                "(SELECT COUNT(tc1.id) FROM tab_school ts1 LEFT JOIN tab_grade tg1 ON tg1.school_school_code=ts1.school_code LEFT JOIN tab_classes tc1 ON tc1.grade_id=tg1.id WHERE ts1.school_code=ts.school_code) classcount,   " +
                "(SELECT COUNT(tc1.id) FROM tab_school ts2  LEFT JOIN tab_grade tg1 ON tg1.school_school_code=ts2.school_code   LEFT JOIN tab_classes tc1 ON tc1.grade_id=tg1.id   LEFT JOIN tab_classroom tcr1 ON tcr1.id=tc1.class_room_id  WHERE tc1.class_room_id IS NOT NULL AND tcr1.equipment_no IS NOT NULL AND ts2.school_code=ts.school_code) jianceshu,   " +
                "(SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id WHERE tcr2.equipment_no IS NOT NULL AND ts3.school_code=ts.school_code ) zongtaoshu,   " +
                "(SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id  WHERE tcr2.equipment_no IS NOT NULL AND tcr2.id  NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities!=1) AND ts3.school_code=ts.school_code ) wanhaoshu,   " +
                "ROUND((SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id  WHERE tcr2.equipment_no IS NOT NULL AND tcr2.id  NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities!=1) AND ts3.school_code=ts.school_code ) /    " +
                "(SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id WHERE tcr2.equipment_no IS NOT NULL AND ts3.school_code=ts.school_code ) ,2) wanhaolv,   " +
                "(SELECT SUM(ta1.price) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code=ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id=tb2.id  LEFT JOIN tab_accessory ta1 ON ta1.class_room_id=tcr2.id WHERE ts3.school_code=ts.school_code  GROUP BY ts3.school_code )  + " +
                "(SELECT SUM(tcr2.person_cost) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code=ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id=tb2.id WHERE ts3.school_code=ts.school_code ) zicanzonge,   " +
                "(SELECT CONCAT( YEAR (MIN(ta1.purchase_date)),'-',YEAR (MAX(ta1.purchase_date))) FROM tab_accessory ta1 LEFT JOIN tab_classroom tc ON ta1.class_room_id = tc.id LEFT JOIN tab_building tb ON tb.id = tc.building_id LEFT JOIN tab_school ts1 ON ts1.school_code = tb.school_school_code WHERE ts1.school_code=ts.school_code) nianfen ,  " +
                "ts.school_code    " +
                "FROM   " +
                "tab_school ts ";

        return schoolRepository.findListBySql(sql);
    }

    @Override
    public List<Object[]> loadSchoolAssetStatis(String schoolCode) {
        String sql="SELECT " +
                " CONCAT(tg.`name`,tcs.class_name) , " +
                " td.`name` tdname, " +
                " room.equipment_no, " +
                " SUM(ta.price)+room.person_cost, " +
                " CONCAT(MIN(YEAR(ta.purchase_date)),'-',MAX(YEAR(ta.purchase_date))) ," +
                " tcs.id " +
                "FROM " +
                " tab_classes tcs " +
                " LEFT JOIN tab_grade tg on tg.id=tcs.grade_id " +
                " LEFT JOIN tab_classroom room ON room.id = tcs.class_room_id " +
                " LEFT JOIN tab_accessory ta on ta.class_room_id=room.id " +
                " LEFT JOIN tab_develop td on td.id=room.develop_type_id " +
                " WHERE tg.school_school_code=:schoolCode " +
                " GROUP BY tcs.id";

        Map<String,Object> map=new HashMap<>();
        map.put("schoolCode",schoolCode);
        return schoolRepository.findListBySql(sql,map);
    }

    @Override
    public List<Object[]> loadClassAssetStatis(Long classId) {
        String sql="SELECT " +
                " tan.name aname, " +
                " br.name bname, " +
                " mo.name mname, " +
                " ta.price, " +
                " DATE_FORMAT(ta.purchase_date,'%Y-%m-%d'), " +
                " IFNULL(ta.repair_times,0), " +
                " ta.facilities, " +
                " 8*365-ROUND((UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(ta.purchase_date))/(60*60*24))," +
                " ta.id tid " +
                "FROM " +
                " tab_classes tcs " +
                "LEFT JOIN tab_classroom tcr on tcr.id=tcs.class_room_id " +
                "LEFT JOIN tab_accessory ta on ta.class_room_id=tcr.id " +
                "LEFT JOIN tab_accessoryname tan on tan.id=ta.accessory_name_id " +
                "LEFT JOIN tab_model mo on mo.id=ta.specification_model_id " +
                "LEFT JOIN tab_brand br on br.id=ta.brand_id " +
                "WHERE tcs.id=:classId and ta.id is not null";

        Map<String,Object> map=new HashMap<>();
        map.put("classId",classId);
        return schoolRepository.findListBySql(sql,map);
    }


    /**
     * 设备完好率统计图形
     * @return
     */
    public List loadSchoolEqAccrssery(){
        String sql="SELECT   " +
                " ts.name tsname,   " +
                " ROUND((SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id  WHERE tcr2.equipment_no IS NOT NULL AND tcr2.id  NOT IN (SELECT ta1.class_room_id FROM tab_accessory ta1 WHERE ta1.facilities!=1) AND ts3.school_code=ts.school_code ) /    " +
                " (SELECT COUNT(tcr2.id) FROM tab_school ts3 LEFT JOIN tab_building tb2 ON tb2.school_school_code = ts3.school_code LEFT JOIN tab_classroom tcr2 ON tcr2.building_id = tb2.id WHERE tcr2.equipment_no IS NOT NULL AND ts3.school_code=ts.school_code ) ,2)*100 wanhaolv   "+
                " FROM   " +
                " tab_school ts ";
        return schoolRepository.findListBySql(sql);
    }

}
