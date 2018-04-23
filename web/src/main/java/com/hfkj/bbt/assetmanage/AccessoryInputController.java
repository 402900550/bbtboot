package com.hfkj.bbt.assetmanage;

import com.alibaba.fastjson.JSONObject;
import com.hfkj.bbt.vo.ClassRoomAccessoryVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("equipmentinput")
public class AccessoryInputController {

    @Autowired
    private IAccessoryInputService inputService;


    @PostMapping(value = "loadClassRoomByBuliding")
    public List<Object[]> loadClassRoomByBuliding(Long buildingId) {
        return inputService.findClassRoomsByBuilding(buildingId);
    }

    @PostMapping(value = "loadClassAndGradeAndEquipment")
    public JSONObject loadClassAndGrade(Long roomId) {
        return inputService.findClassAndGrade(roomId);
    }

    @PostMapping(value = "deleteAccessoryById")
    public String deleteAccessoryById(Long accessoryId){
        return inputService.deleteAccessoryById(accessoryId);
    }

    @PostMapping(value = "doSaveAccessories")
    public String doSaveAccessories(ClassRoomAccessoryVo accessoryVo){
        Long developTypeId = accessoryVo.getDevelopTypeId();
        if(developTypeId==null){
            return "请选择配备类型!";
        }
        Long roomId = accessoryVo.getRoomId();
        if(roomId==null){
            return "请选择教室!";
        }
        String manager = accessoryVo.getManager();
        if(manager==null){
            return "请输入负责人!";
        }
        Integer personCost = accessoryVo.getPersonCost();
        if(personCost==null){
            return "请选择输出人工耗材费!";
        }
        String inputDate = accessoryVo.getInputDate();
        if(inputDate==null){
            return "请输入录入日期!";
        }
        inputService.doSaveAccessories(accessoryVo);
        return "保存成功!";
    }

}
