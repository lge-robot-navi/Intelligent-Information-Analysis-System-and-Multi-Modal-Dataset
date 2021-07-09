package com.lge.mams.jpa.impl;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TbAgentStat;

@Repository
public interface TbAgentStatRepository extends JpaRepository<TbAgentStat, Long> {

	Page<TbAgentStat> findByStatDtBetween(Pageable page, Date start, Date end);

}
