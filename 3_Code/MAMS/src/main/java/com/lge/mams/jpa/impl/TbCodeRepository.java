package com.lge.mams.jpa.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TbCode;

@Repository
public interface TbCodeRepository extends JpaRepository<TbCode, Long> {

	
	
}
