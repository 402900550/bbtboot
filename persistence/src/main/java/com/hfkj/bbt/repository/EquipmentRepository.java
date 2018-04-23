package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.ClassRoom;
import com.hfkj.bbt.entity.Equipment;
import com.hfkj.bbt.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends BaseRepository<Equipment,String> {

    Equipment findByEquipmentNoAndEquipmentType(String equipmentNo,int equipmentType);

    /**
     * 根据教室表查询设备表
     * @param classRoom
     * @return
     */
    Equipment findByClassRoom(ClassRoom classRoom);

}
