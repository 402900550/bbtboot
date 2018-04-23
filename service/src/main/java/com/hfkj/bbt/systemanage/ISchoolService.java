package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.Company;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.entity.SchoolType;
import com.hfkj.bbt.vo.SchoolVo;

import java.util.List;

public interface ISchoolService {

    /**
     * 查询所有企业
     * @return
     */
    List<Company> findCompanys();

    /**
     * 根据schoolCode查询学校实体
     * @param schoolCode
     * @return
     */
    School findBySchoolCode(String schoolCode);

    /**
     * 查询所有学校
     * @return
     */
    List findBySchoolAll();


    /**
     * 根据当前用户的角色查询前端添加用户时候出现的学校
     * @return
     */
    List<School> findSchoolByRole();


    String doSaveSchool(SchoolVo schoolVo);

    String doModifySchool(SchoolVo schoolVo);
    List<SchoolType> findBySchoolTypeAll();

    /**
     * 根据学校单位代码删除学校
     * @param schoolCode
     * @return
     */
    void deleteBySchoolCode(String schoolCode);

    /**
     * 查询所有费用
     * @return
     */
    List findBySchoolAccessoryPrice();




    List findTop3SchoolEnvironment();


}
