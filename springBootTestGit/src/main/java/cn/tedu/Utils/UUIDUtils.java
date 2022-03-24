package cn.tedu.Utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUUID16() {
        return getUUID().substring(0, 16);
    }

}
