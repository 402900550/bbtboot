package com.hfkj.bbt.systemanage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hfkj.bbt.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "parameter")
public class ParameterController {

    @Autowired
    private IParamterService service;

    /**
     * 添加修改品牌
     * @param brandId
     * @param brandName
     * @return
     */
    @PostMapping(value = "addOrModifyBrand")
    @ResponseBody
    public String addOrModifyBrand(Long brandId,String brandName){
        return service.doSaveBrand(brandId,brandName);
    }


    /**
     * 添加修改规格型号
     * @param modelId
     * @param modelName
     * @return
     */
    @PostMapping(value = "addOrModifyModel")
    @ResponseBody
    public String addOrModifyModel(Long modelId,String modelName){
        return service.doSaveModel(modelId,modelName);
    }


    /**
     * 添加修改公司
     * @param companyId
     * @param companyName
     * @param address
     * @param phone
     * @return
     */
    @PostMapping(value = "addOrModifyCompany")
    @ResponseBody
    public String addOrModifyCompany(Long companyId,String companyName,String address,String phone){
        return service.doSaveCompany(companyId,companyName,address,phone);
    }

    /**
     * 添加修改设备名称
     * @return
     */
    @PostMapping(value = "addOrModifyAccessoryName")
    @ResponseBody
    public String addOrModifyAccessoryName(Long accessoryId,String accessoryName){
        return service.doSaveAccessoryName(accessoryId,accessoryName);
    }

    /**
     * 添加修改配备类型
     * @return
     */
    @PostMapping(value = "addOrModifyDevelopType")
    @ResponseBody
    public String addOrModifyDevelopType(Long developId,String developName){
        return service.doSaveDevelopType(developId,developName);
    }

    /**
     * 查询
     * @return
     */
    @GetMapping(value = "loadBrandList")
    @ResponseBody
    public Map<String,Object> loadBrandList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",service.findAllBrand());
        return map;
    }

    @GetMapping(value = "loadModelList")
    @ResponseBody
    public Map<String,Object> loadModelList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",service.findAllModel());
        return map;

    }

    @GetMapping(value = "loadCompanyList")
    @ResponseBody
    public Map<String,Object> loadCompanyList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",service.findAllCompany());
        return map;

    }

    /**
     * 查询设备名称列表
     * @return
     */
    @GetMapping(value = "loadAccessNameTableList")
    @ResponseBody
    public Map<String,Object> loadAccessNameTableList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",service.findAllAccessName());
        return map;

    }
    /**
     * 查询配备类型列表
     * @return
     */
    @GetMapping(value = "loadDevelopList")
    @ResponseBody
    public Map<String,Object> loadDevelopList(){
        Map<String,Object> map=new HashMap<>();
        map.put("data",service.findAllDevelopType());
        return map;

    }


    @PostMapping(value = "deleteBrand")
    @ResponseBody
    public String deleteBrand(Long brandId){
        return service.deleteBrand(brandId);
    }

    @PostMapping(value = "deleteModel")
    @ResponseBody
    public String deleteModel(Long modelId){
        return service.deleteModel(modelId);
    }

    @PostMapping(value = "deleteCompany")
    @ResponseBody
    public String deleteCompany(Long companyId){
        return service.deleteCompany(companyId);
    }

    @PostMapping(value = "deleteAccessoryName")
    @ResponseBody
    public String deleteAccessoryName(Long accessoryId){
        return service.deleteAccessoryName(accessoryId);
    }

    @PostMapping(value = "deleteDevelopType")
    @ResponseBody
    public String deleteDevelopType(Long developTypeId){
        return service.deleteDevelopType(developTypeId);
    }


    @PostMapping(value = "findBrandById")
    @ResponseBody
    public JSONObject findBrandById(Long brandId){
        Brand brand=service.findBrandById(brandId);
        JSONObject obj=new JSONObject();
        obj.put("brandId",brand.getId());
        obj.put("brandName",brand.getName());
        return obj;
    }

    @PostMapping(value = "findModelById")
    @ResponseBody
    public JSONObject findModelById(Long modelId){
        SpecificationModel model=service.findModelById(modelId);
        JSONObject obj=new JSONObject();
        obj.put("modelId",model.getId());
        obj.put("modelName",model.getName());

        return obj;
    }

    @PostMapping(value = "findCompanyById")
    @ResponseBody
    public JSONObject findCompanyById(Long companyId){
        JSONObject obj=new JSONObject();
        Company company=service.findCompanyById(companyId);
        obj.put("companyId",company.getId());
        obj.put("companyName",company.getName());
        obj.put("phone",company.getPhone());
        obj.put("address",company.getAddress());
        return obj;
    }


    @PostMapping(value = "findAccessoryNameById")
    @ResponseBody
    public JSONObject findAccessoryNameById(Long accNameId){
        AccessoryName accessoryName = service.findById(accNameId);
        JSONObject obj=new JSONObject();
        obj.put("accessoryName",accessoryName.getId());
        obj.put("accessoryId",accessoryName.getName());

        return obj;
    }


    @PostMapping(value = "findDevelopTypeById")
    @ResponseBody
    public JSONObject findDevelopTypeById(Long developId){
        DevelopType byDevelopId = service.findByDevelopId(developId);
        JSONObject obj=new JSONObject();
        obj.put("developName",byDevelopId.getId());
        obj.put("developId",byDevelopId.getName());

        return obj;
    }
}
