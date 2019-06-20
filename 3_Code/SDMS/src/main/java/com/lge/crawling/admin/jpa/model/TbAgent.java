package com.lge.crawling.admin.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="TB_AGENT", indexes = {@Index(name = "IDX_AGENT_01",  columnList="AGENT_ID,AGENT_AREA_CODE", unique = true)})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbAgent {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AGENT_SN")
	private Long agentSn;
	
	@Column(name="AGENT_ID", length=50)
	private String agentId;
	
	@Column(name="AGENT_NAME", length=100)
	private String agentName;
	
	@Column(name="AGENT_DESC", length=500)
	private String agentDesc;
	
	@Column(name="AGENT_AREA_CODE")
	private int agentAreaCode;

	public Long getAgentSn() {
		return agentSn;
	}

	public void setAgentSn(Long agentSn) {
		this.agentSn = agentSn;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentDesc() {
		return agentDesc;
	}

	public void setAgentDesc(String agentDesc) {
		this.agentDesc = agentDesc;
	}

	public int getAgentAreaCode() {
		return agentAreaCode;
	}

	public void setAgentAreaCode(int agentAreaCode) {
		this.agentAreaCode = agentAreaCode;
	}
	
	
}
