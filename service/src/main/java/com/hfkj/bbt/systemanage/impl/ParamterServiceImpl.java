package com.hfkj.bbt.systemanage.impl;

import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.systemanage.IParamterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ParamterServiceImpl implements IParamterService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccessoryNameRepository nameRepository;

    @Autowired
    private DevelopRepository developRepository;
    @Override
    public List findAllBrand() {
        String jpql="SELECT b.id,b.name FROM Brand b";
        return brandRepository.findListByJpql(jpql);
    }

    @Override
    public List findAllModel() {
        String jpql="SELECT s.id,s.name FROM SpecificationModel s";
        return modelRepository.findListByJpql(jpql);
    }

    @Override
    public List findAllCompany() {
        String jpql="SELECT c.id,c.name,c.phone,c.address FROM Company c";
        return companyRepository.findListByJpql(jpql);
    }

    @Override
    public List findAllAccessName() {
        String jpql="SELECT c.id,c.name FROM AccessoryName c";
        return nameRepository.findListByJpql(jpql);
    }

    @Override
    public List findAllDevelopType() {
        String jpql="SELECT c.id,c.name  FROM DevelopType c";
        return developRepository.findListByJpql(jpql);
    }

    /**
     * 保存品牌
     * @param brandId
     * @param brandName
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String doSaveBrand(Long brandId, String brandName) {
        Brand brand=new Brand();
        if (brandId!=null){
            brand = brandRepository.findOne(brandId);

        }
        brand.setName(brandName);
        try {
            brandRepository.save(brand);
        }catch (Exception e){
            return "名称重复!";
        }
        return "保存成功!";
    }

    /**
     * 保存规格型号
     * @param modelId
     * @param modelName
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String doSaveModel(Long modelId, String modelName) {
        SpecificationModel model=new SpecificationModel();
        if (modelId!=null){
            model = modelRepository.findOne(modelId);
        }
        model.setName(modelName);
        try {
            modelRepository.save(model);
        }catch (Exception e){
            return "名称重复!";
        }
        return "保存成功!";
    }

    /**
     * 保存公司
     * @param companyId
     * @param companyName
     * @param address
     * @param phone
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String doSaveCompany(Long companyId, String companyName, String address, String phone) {
        Company company=new Company();
        if (companyId!=null){
            company = companyRepository.findOne(companyId);
        }
        company.setAddress(address);
        company.setName(companyName);
        company.setPhone(phone);
        try {
            companyRepository.save(company);
        }catch (Exception e){
            return "名称重复!";
        }
        return "保存成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String doSaveAccessoryName(Long id, String accessName) {
        AccessoryName accessoryName=new AccessoryName();
        if (id!=null){
            accessoryName = nameRepository.findOne(id);
        }
        accessoryName.setName(accessName);
        try {
            nameRepository.save(accessoryName);
        }catch (Exception e){
            return "名称重复!";
        }
        return "保存成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String doSaveDevelopType(Long id, String developName) {
        DevelopType developType=new DevelopType();
        if (id!=null){
            developType = developRepository.findOne(id);
        }
        developType.setName(developName);
        try {
            developRepository.save(developType);
        }catch (Exception e){
            return "名称重复!";
        }
        return "保存成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteBrand(Long brandId) {
        try {
            brandRepository.delete(brandId);
        }catch (Exception e){
            return "该参数已被使用!";
        }
        return "删除成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteModel(Long modelId) {
        try {
            modelRepository.delete(modelId);
        }catch (Exception e){
            return "该参数已被使用!";
        }
        return "删除成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteCompany(Long companyId) {
        try {
            companyRepository.delete(companyId);
        }catch (Exception e){
            return "该参数已被使用!";
        }
        return "删除成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteAccessoryName(Long id) {
        try {
            nameRepository.delete(id);
        }catch (Exception e){
            return "该参数已被使用!";
        }
        return "删除成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteDevelopType(Long id) {
        try {
            developRepository.delete(id);
        }catch (Exception e){
            return "该参数已被使用!";
        }
        return "删除成功!";
    }

    @Override
    public Brand findBrandById(Long brandId) {
        return brandRepository.findOne(brandId);
    }

    @Override
    public SpecificationModel findModelById(Long modelId) {
        return modelRepository.findOne(modelId);
    }

    @Override
    public Company findCompanyById(Long companyId) {
        return companyRepository.findOne(companyId);
    }

    @Override
    public AccessoryName findById(Long id) {
        return nameRepository.findOne(id);
    }

    @Override
    public DevelopType findByDevelopId(Long id) {
        return developRepository.findOne(id);
    }
}














