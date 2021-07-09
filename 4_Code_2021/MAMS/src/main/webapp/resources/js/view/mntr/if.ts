export interface IfTbEvt {
  eventSn?: number;
  status?: number;
  abnormalType?: number;
  abnormalDetail?: number;
  timeT?: number;
  fbNeed?: string;
  eventDt?: string;
  robotId?: number;
  areaCode?: string;
  deviceId?: number;
  abnormalId?: string;
}

export interface IfMqttStat {
  agentId?: number;
  agentType?: string;
  location?: string;
  isHealthy?: boolean;
}

export interface IfAppBodyData {
  ledkey: number;
  alerts: [];
  alertid: number;
  schedulingShow: boolean;
  logShow: boolean;
  latlonShow: boolean;
  logs: [];
}

export interface IfAppBodyMethods {
  setHealthy: (robotId, isHealthy) => void;
}

export interface IfTaCodeInfo {
  cdgrpCd: string;
  codeCd: string;
  codeNm: string;
  codeDs: string;
}
export interface IfAgent extends IfTaCodeInfo {
  info: {
    lat: number;
    lon: number;
    fixed: string;
    markerImage: string;
    agentId: number;
    areaCode: string; // P or G
    isHealthy: boolean;
  };
}
