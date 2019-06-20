package com.lge.mams.jpa.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TaCodeId;
import com.lge.mams.jpa.model.TaCodeInfo;
import java.lang.String;
import java.util.List;

@Repository
public interface TaCodeInfoRepository extends JpaRepository<TaCodeInfo, TaCodeId> {
	
	List<TaCodeInfo> findByCdgrpCd(String cdgrpcd);
	List<TaCodeInfo> findByCdgrpCdOrderByOrderNoAsc(String cdgrpcd);
	
}
