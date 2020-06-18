package util;

import java.util.UUID;

/*
* 产生UUID随机字符串工具类
* */
public class UuidUtils {
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
