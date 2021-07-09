package com.lge.mams.jpa.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TaCodeId;
import com.lge.mams.jpa.model.TaCodeInfo;

@Repository
public interface TaCodeInfoRepository extends JpaRepository<TaCodeInfo, TaCodeId> {

	List<TaCodeInfo> findByCdgrpCd(String cdgrpcd);

	List<TaCodeInfo> findByCdgrpCdAndUseYn(String cdgrpcd, String useYn);

	List<TaCodeInfo> findByCdgrpCdOrderByOrderNoAsc(String cdgrpcd);

	List<TaCodeInfo> findByCdgrpCdAndUseYnOrderByOrderNoAsc(String cdgrpcd, String useYn);

}
