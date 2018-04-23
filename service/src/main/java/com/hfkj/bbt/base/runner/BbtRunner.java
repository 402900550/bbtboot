package com.hfkj.bbt.base.runner;

import com.hfkj.bbt.entity.Distinct;
import com.hfkj.bbt.base.runner.service.RunnerService;
import com.hfkj.bbt.entity.SchoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目启动后执行
 */
@Component
@Order(value = 1)
public class BbtRunner implements ApplicationRunner{

    @Autowired
    private RunnerService runnerService;


    @Override
    public void run(ApplicationArguments var1) throws Exception{
        //保存区县
        runnerService.updateDistinct();
        //保存角色
        runnerService.updateRoles();
        //保存学校类型
        runnerService.updateSchoolType();
        //保存学科
        runnerService.updateSubject();
    }




}



















