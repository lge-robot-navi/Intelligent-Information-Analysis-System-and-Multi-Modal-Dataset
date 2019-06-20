package com.lge.crawling.admin.management.sensorDataInfo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SensorDataJsonFileDescEntity {

    private SensorData image;

    public SensorData getSensorData ()
    {
        return image;
    }

    public void setSensorData (SensorData image)
    {
        this.image = image;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}