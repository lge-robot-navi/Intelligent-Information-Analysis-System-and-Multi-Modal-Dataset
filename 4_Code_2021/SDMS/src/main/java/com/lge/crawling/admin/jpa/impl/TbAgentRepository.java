package com.lge.crawling.admin.jpa.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.crawling.admin.jpa.model.TbAgent;

@Repository
public interface TbAgentRepository extends JpaRepository<TbAgent, Long> {

}
