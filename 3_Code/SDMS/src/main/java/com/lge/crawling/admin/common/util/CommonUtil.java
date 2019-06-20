package com.lge.crawling.admin.common.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * Created by chokyujin on 2017. 2. 17..
 */
@Component("commonUtil")
public class CommonUtil {

    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
