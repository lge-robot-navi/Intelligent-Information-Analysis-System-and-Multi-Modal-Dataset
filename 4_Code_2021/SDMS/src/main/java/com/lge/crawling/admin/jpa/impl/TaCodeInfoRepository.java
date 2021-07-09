package com.lge.crawling.admin.jpa.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.crawling.admin.jpa.model.TaCodeId;
import com.lge.crawling.admin.jpa.model.TaCodeInfo;

@Repository
public interface TaCodeInfoRepository extends JpaRepository<TaCodeInfo, TaCodeId> {

}
