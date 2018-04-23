package com.hfkj.bbt.base.runner.service;

import com.hfkj.bbt.entity.Distinct;
import com.hfkj.bbt.entity.Role;
import com.hfkj.bbt.entity.SchoolType;
import com.hfkj.bbt.entity.Subject;

import java.util.List;

/**
 * 启动时执行的service
 */
public interface RunnerService {

    /**
     * 更新区县
     */
    void updateDistinct ();

    /**
     * 更新角色表
     */
    void updateRoles();

    /**
     * 更新学校类型表
     */
    void updateSchoolType();

    /**
     * 更新科目表
     */
    void updateSubject();

}
