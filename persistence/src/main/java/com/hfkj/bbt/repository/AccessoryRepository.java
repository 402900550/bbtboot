package com.hfkj.bbt.repository;


import com.hfkj.bbt.entity.Accessory;
import com.hfkj.bbt.entity.ClassRoom;
import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface AccessoryRepository extends BaseRepository<Accessory, Long> {


    List<Accessory> findByClassRoom(ClassRoom classRoom);

}
