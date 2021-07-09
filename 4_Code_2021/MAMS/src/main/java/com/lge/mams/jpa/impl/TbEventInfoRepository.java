package com.lge.mams.jpa.impl;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lge.mams.jpa.model.TbEventInfo;

@Repository
public interface TbEventInfoRepository extends JpaRepository<TbEventInfo, Long> {

	Page<TbEventInfo> findByConfirmYn(Pageable page, String confirmYn);

	Page<TbEventInfo> findByEventDtBetween(Pageable page, Date start, Date end);

	@Modifying
	@Transactional
	@Query("UPDATE TbEventInfo a set a.confirmYn = 'Y' where a.confirmYn = 'N' ")
	void clearAlarmAll();

}
