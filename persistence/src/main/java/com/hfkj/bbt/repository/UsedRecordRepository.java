package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.Equipment;
import com.hfkj.bbt.entity.UsedRecord;
import com.hfkj.bbt.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedRecordRepository extends BaseRepository<UsedRecord,String> {

    UsedRecord findByEquipment(Equipment equipment);

    List<UsedRecord> findByEquipmentOrderByCollectTimeDesc(Equipment equipment);
}
