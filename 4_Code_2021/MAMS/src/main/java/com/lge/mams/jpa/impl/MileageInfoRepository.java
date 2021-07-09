package com.lge.mams.jpa.impl;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.MileageInfo;

@Repository
public interface MileageInfoRepository extends JpaRepository<MileageInfo, Long> {

	List<MileageInfo> findAllByStatDtBetween(Date start, Date end);
	// @Query(value = "SELECT a.STAT_DT a.ROBOT_ID a.LAT a.LON FROM TB_AGENT_STAT a WHERE date(STAT_DT) BETWEEN ? and ? order by STAT_DT DESC")
	// List<TbAgentStat> findByStatDtBetweenOrderByDesc(Date start, Date end);
	// List<TbAgentStat> selectMileageInfo(Date start, Date end);

}
