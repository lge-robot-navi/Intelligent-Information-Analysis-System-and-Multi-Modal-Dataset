package com.lge.crawling.admin.management.imageInfo.entity;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Image
{
    private String base64_img_data;

    private String fileref;

    private String filename;

    private Map<String, Regions> regions;

    private String size;

    public String getBase64_img_data ()
    {
        return base64_img_data;
    }

    public void setBase64_img_data (String base64_img_data)
    {
        this.base64_img_data = base64_img_data;
    }

    public String getFileref ()
    {
        return fileref;
    }

    public void setFileref (String fileref)
    {
        this.fileref = fileref;
    }

    public String getFilename ()
    {
        return filename;
    }

    public void setFilename (String filename)
    {
        this.filename = filename;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

	public Map<String, Regions> getRegions() {
		return regions;
	}

	public void setRegions(Map<String, Regions> regions) {
		this.regions = regions;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}