package com.hfkj.bbt.assetmanage.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hfkj.bbt.assetmanage.IAccessoryInputService;
import com.hfkj.bbt.entity.*;
import com.hfkj.bbt.repository.*;
import com.hfkj.bbt.util.DateUtil;
import com.hfkj.bbt.vo.AccessoryVo;
import com.hfkj.bbt.vo.ClassRoomAccessoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccessoryInputServiceImpl implements IAccessoryInputService {

    @Autowired
    private DevelopRepository developRepository;

    @Autowired
    private ClassRoomRepository roomRepository;

    @Autowired
    private AccessoryNameRepository nameRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 查询所有配备类型
     *
     * @return
     */
    public List<DevelopType> findAllDevelop() {
        return developRepository.findAll();
    }

    public List<Object[]> findClassRoomsByBuilding(Long buildingId) {
        Map<String, Object> map = new HashMap<>();
        map.put("buildingId", buildingId);
        String jpql = "SELECT " +
                " cr.id, " +
                " cr.roomCode  " +
                " FROM ClassRoom cr " +
                " LEFT JOIN cr.building b " +
                " where  b.id=:buildingId";
        List list = developRepository.findListByJpql(jpql, map);
        return list;
    }

    @Override
    public List<AccessoryName> findAllAccessName() {
        return nameRepository.findAll();
    }

    @Override
    public List<SpecificationModel> findAllModel() {
        return modelRepository.findAll();
    }

    @Override
    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }

    @Override
    public JSONObject findClassAndGrade(Long roomId) {
        JSONObject json = new JSONObject();
        if (roomId != null) {
            ClassRoom classRoom = roomRepository.findOne(roomId);
            Integer personCost = classRoom.getPersonCost();
            json.put("personCost", personCost);
            json.put("manager", classRoom.getManager());
            DevelopType developType = classRoom.getDevelopType();
            if (developType != null) {
                json.put("developTypeId", developType.getId());
            }
            Classes classes = classRoom.getClasses();
            Set<Accessory> accessories = classRoom.getAccessories();
            Iterator<Accessory> iterator = accessories.iterator();
            List list = new ArrayList();
            Long[] ids;
            while (iterator.hasNext()) {
                ids = new Long[5];
                Accessory a = iterator.next();
                AccessoryName accessoryName = a.getAccessoryName();
                Brand brand = a.getBrand();
                SpecificationModel specificationModel = a.getSpecificationModel();
                ids[0] = accessoryName.getId();
                ids[1] = brand.getId();
                ids[2] = specificationModel.getId();
                ids[3] = Long.valueOf(a.getPrice());
                ids[4] = a.getId();
                json.put("inputDate", DateUtil.tranDateToString("yyyy-MM-dd", a.getPurchaseDate()));
                list.add(ids);
            }
            json.put("accessories", list);
            if (classes != null) {
                Grade grade = classes.getGrade();
                json.put("gradeName", grade.getName());
                json.put("className", classes.getClassName());

            }
        }
        return json;
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteAccessoryById(Long accessoryId) {
        accessoryRepository.delete(accessoryId);
        return "删除成功!";
    }

    @Override
    @Transactional(readOnly = false)
    public void doSaveAccessories(ClassRoomAccessoryVo accessoryVo) {
        Long roomId = accessoryVo.getRoomId();

        ClassRoom room = roomRepository.findOne(roomId);
        room.setDevelopType(developRepository.findOne(accessoryVo.getDevelopTypeId()));
        room.setManager(accessoryVo.getManager());
        room.setPersonCost(accessoryVo.getPersonCost());
        roomRepository.save(room);
        String equipments = accessoryVo.getEquipments();
        List<AccessoryVo> accessoryVos = JSONArray.parseArray(equipments, AccessoryVo.class);
        Iterator<AccessoryVo> iterator = accessoryVos.iterator();
        Accessory accessory;
        while (iterator.hasNext()) {
            AccessoryVo next = iterator.next();
            Long accessoryId = next.getAccessoryId();
            if(accessoryId==null){
                accessory=new Accessory();
                accessory.setFacilities(1);
                accessory.setClassRoom(room);
            }else {
                accessory  = accessoryRepository.findOne(accessoryId);
            }

            accessory.setAccessoryName(nameRepository.findOne(next.getNameId()));
            accessory.setBrand(brandRepository.findOne(next.getBrandId()));
            accessory.setSpecificationModel(modelRepository.findOne(next.getModelId()));
            accessory.setPrice(next.getPrice());
            accessory.setPurchaseDate(DateUtil.tranStringToDate("yyyy-MM-dd", accessoryVo.getInputDate()));

            accessoryRepository.save(accessory);
        }


    }

    @Override
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }
}
