package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Building;
import com.hfkj.bbt.entity.ClassRoom;
import com.hfkj.bbt.entity.Classes;
import com.hfkj.bbt.entity.Equipment;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface ClassRoomRepository extends BaseRepository<ClassRoom, Long> {

    /**
     * 根据roomId查询教室
     * @param id
     * @return
     */
    ClassRoom findClassRoomById(Long id);

    /**
     * 根据实体类查询所有教室
     * @param building
     * @return
     */
    List<ClassRoom> findByBuildingAndRoomType(Building building,String roomType);

    /**
     * 根据实体类查询教室
     * @param building
     * @return
     */
    List<ClassRoom> findByBuilding(List<Building>  building);

    /**
     * 根据实体类查询教室
     * @param building
     * @param roomCode
     * @return
     */
    ClassRoom findByBuildingAndRoomCode(Building building,String roomCode);


    ClassRoom findByEquipment(Equipment equipment);
}
