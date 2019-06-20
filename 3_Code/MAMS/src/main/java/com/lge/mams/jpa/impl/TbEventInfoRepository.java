package com.lge.mams.jpa.impl;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TbEventInfo;

@Repository
public interface TbEventInfoRepository extends JpaRepository<TbEventInfo, Long> {

	
	Page<TbEventInfo> findByConfirmYn(Pageable page, String confirmYn);
	
	Page<TbEventInfo> findByEventDtBetween(Pageable page, Date start, Date end);
}
