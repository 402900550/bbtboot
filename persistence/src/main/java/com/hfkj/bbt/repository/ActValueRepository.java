package com.hfkj.bbt.repository;

import com.hfkj.bbt.entity.ActivityValue;
import com.hfkj.bbt.repository.base.BaseRepository;



public interface ActValueRepository extends BaseRepository<ActivityValue,String> {

    ActivityValue findByTaskIdAndType(String taskId, String type);

}
