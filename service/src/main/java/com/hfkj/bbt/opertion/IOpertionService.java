package com.hfkj.bbt.opertion;

import com.hfkj.bbt.vo.OpertionDetailVo;
import com.hfkj.bbt.vo.SubmitAviceVo;

import java.util.List;

public interface IOpertionService {


    String addOpertionAndStartProcess(Long classId,String description,Long companyId);

    List<Object[]> loadOpertionsByRole(Integer status);

    OpertionDetailVo loadProcessDetail(Long opertionId);

    /**
     * 首页运维使用数
     * @return
     */
    List loadOpertions();

    String doCompleteTask(SubmitAviceVo vo);

    String approScrap(Long accessoryId,Long classId);
}
