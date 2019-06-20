package com.lge.crawling.admin.management.sensorDataInfo.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Region_attributes_sd
{
    private String tagging_dic_3_depth_nm;

    private String tagging_dic_3_depth_id;

    private String tagging_dic_2_depth_nm;

    private String tagging_dic_2_depth_id;

    private String tagging_dic_1_depth_nm;

    private String tagging_dic_1_depth_id;

    public String getTagging_dic_3_depth_nm ()
    {
        return tagging_dic_3_depth_nm;
    }

    public void setTagging_dic_3_depth_nm (String tagging_dic_3_depth_nm)
    {
        this.tagging_dic_3_depth_nm = tagging_dic_3_depth_nm;
    }

    public String getTagging_dic_3_depth_id ()
    {
        return tagging_dic_3_depth_id;
    }

    public void setTagging_dic_3_depth_id (String tagging_dic_3_depth_id)
    {
        this.tagging_dic_3_depth_id = tagging_dic_3_depth_id;
    }

    public String getTagging_dic_2_depth_nm ()
    {
        return tagging_dic_2_depth_nm;
    }

    public void setTagging_dic_2_depth_nm (String tagging_dic_2_depth_nm)
    {
        this.tagging_dic_2_depth_nm = tagging_dic_2_depth_nm;
    }

    public String getTagging_dic_2_depth_id ()
    {
        return tagging_dic_2_depth_id;
    }

    public void setTagging_dic_2_depth_id (String tagging_dic_2_depth_id)
    {
        this.tagging_dic_2_depth_id = tagging_dic_2_depth_id;
    }

    public String getTagging_dic_1_depth_nm ()
    {
        return tagging_dic_1_depth_nm;
    }

    public void setTagging_dic_1_depth_nm (String tagging_dic_1_depth_nm)
    {
        this.tagging_dic_1_depth_nm = tagging_dic_1_depth_nm;
    }

    public String getTagging_dic_1_depth_id ()
    {
        return tagging_dic_1_depth_id;
    }

    public void setTagging_dic_1_depth_id (String tagging_dic_1_depth_id)
    {
        this.tagging_dic_1_depth_id = tagging_dic_1_depth_id;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}