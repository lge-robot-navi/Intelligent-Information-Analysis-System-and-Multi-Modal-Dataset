package com.lge.crawling.admin.management.imageInfo.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Regions
{
    private Shape_attributes shape_attributes;

    private Region_attributes region_attributes;

    public Shape_attributes getShape_attributes ()
    {
        return shape_attributes;
    }

    public void setShape_attributes (Shape_attributes shape_attributes)
    {
        this.shape_attributes = shape_attributes;
    }

    public Region_attributes getRegion_attributes ()
    {
        return region_attributes;
    }

    public void setRegion_attributes (Region_attributes region_attributes)
    {
        this.region_attributes = region_attributes;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}