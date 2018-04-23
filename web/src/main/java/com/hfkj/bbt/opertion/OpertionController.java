package com.hfkj.bbt.opertion;

import com.hfkj.bbt.assetmanage.IAccessoryInputService;
import com.hfkj.bbt.vo.SubmitAviceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "opertion")
public class OpertionController {

    @Autowired
    private IOpertionService opertionService;

    @Autowired
    private IAccessoryInputService inputService;

    /**
     * 手动添加运维单
     * @param classId
     * @param exceptionDescription
     * @param companyId
     * @return
     */
    @PostMapping(value = "addOpertion")
    @ResponseBody
    public String addOpertion(Long classId,String exceptionDescription,Long companyId){
        return opertionService.addOpertionAndStartProcess(classId,exceptionDescription,companyId);
    }


    /**
     * 加载运维单
     * @param status
     * @return
     */
    @GetMapping(value = "loadOpertionTable")
    @ResponseBody
    public Map<String,Object>  loadOpertionTable(Integer status){
        Map<String,Object> map=new HashMap<>();
        map.put("data",opertionService.loadOpertionsByRole(status));
        return map;
    }

    /**
     * 跳转到流程详情
     * @param opertionId
     * @return
     */
    @PostMapping(value = "forwardToOpertionProcess")
    public ModelAndView forwardToOpertionProcess(Long opertionId){
        ModelAndView mav=new ModelAndView("operationsManagement/maintainStatisticsProcess");
        mav.addObject("opertionId",opertionId);
        mav.addObject("companys",inputService.findAllCompany());
        mav.addObject("opertionDetailVo",opertionService.loadProcessDetail(opertionId));
        return mav;
    }

    /**
     * 提交审批
     * @return
     */
    @PostMapping(value = "doSubmitAdvice")
    @ResponseBody
    public String doSubmitAdvice(SubmitAviceVo vo){
        if("no".equals(vo.getChoose())&&vo.getChooseCompany()==null){
            return "请选择维修公司!";
        }
        return opertionService.doCompleteTask(vo);
    }


    /**
     * 申请报废
     * @return
     */
    @PostMapping(value = "approScrap")
    @ResponseBody
    public String approScrap(Long accessoryId,Long classId){
        //判断权限
        return opertionService.approScrap(accessoryId,classId);
    }

}
