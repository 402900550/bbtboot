package com.hfkj.bbt.repository;


import com.hfkj.bbt.entity.School;
import com.hfkj.bbt.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends BaseRepository<School,String> {

}
