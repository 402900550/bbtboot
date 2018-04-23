package com.hfkj.bbt.systemanage;

import com.hfkj.bbt.entity.*;

import java.util.List;

public interface IParamterService {
    List findAllBrand();

    List findAllModel();

    List findAllCompany();

    List findAllAccessName();

    List findAllDevelopType();

    String doSaveBrand(Long brandId, String brandName);

    String doSaveModel(Long modelId, String modelName);

    String doSaveCompany(Long companyId, String companyName, String address, String phone);

    String doSaveAccessoryName(Long id,String accessName);

    String doSaveDevelopType(Long id,String developName);


    String deleteBrand(Long brandId);

    String deleteModel(Long modelId);

    String deleteCompany(Long companyId);

    String deleteAccessoryName(Long id);

    String deleteDevelopType(Long id);

    Brand findBrandById(Long brandId);

    SpecificationModel findModelById(Long modelId);

    Company findCompanyById(Long companyId);

    AccessoryName findById(Long id);

    DevelopType findByDevelopId(Long id);
}
