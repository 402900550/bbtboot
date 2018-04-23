package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.ClassRoom;
import com.hfkj.bbt.entity.Classes;
import com.hfkj.bbt.entity.Grade;
import com.hfkj.bbt.repository.base.BaseRepository;

import java.util.List;

public interface ClassRepository extends BaseRepository<Classes, Long> {



    Classes findByClassRoom(ClassRoom classRoom);

    List<Classes> findByGrade(Grade grade);


}
