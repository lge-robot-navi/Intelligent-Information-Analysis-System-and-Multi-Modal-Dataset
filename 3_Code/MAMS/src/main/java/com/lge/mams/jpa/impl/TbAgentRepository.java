package com.lge.mams.jpa.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lge.mams.jpa.model.TbAgent;

@Repository
public interface TbAgentRepository extends JpaRepository<TbAgent, Long> {

}
