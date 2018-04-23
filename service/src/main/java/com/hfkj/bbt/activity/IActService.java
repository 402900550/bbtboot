package com.hfkj.bbt.activity;

import com.hfkj.bbt.vo.HistoryActVo;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IActService {

    String startProcessByKey(String key);

    String startProcessByKey(String key, Map<String,Object> map);

    List<HistoryActVo> loadProcessHisByInstanceId(String processId);

    Task findTaskByProcessId(String processId);

    /**
     * 完成任务带参数
     * @param taskId
     * @param param
     */
    void doCompleteTask(String taskId,Map<String,Object> param);

    /**
     * 完成任务不带参数
     * @param taskId
     */
    void doCompleteTask(String taskId);

    /**
     * 流程是否结束
     * @param processId
     * @return
     */
    Date isEnded(String processId);
}
