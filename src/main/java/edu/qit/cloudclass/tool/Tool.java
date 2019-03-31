package edu.qit.cloudclass.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author nic
 * @version 1.0
 */
public class Tool {
    private final static List<String> SUPPORT_IMAGE_TYPES = new ArrayList<>(Arrays.asList("JPG","JPEG","PNG","GIF"));

    /**
     * 获取随机UUID
     * @return 随机UUID
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 检查参数是否有效
     * @param params 待检查参数
     * @return 检查结果
     */
    public static boolean checkParamsNotNull(String... params){
        for (String param: params) {
            if (param == null || param.length() == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 通过后缀名检查图片文件类型是否支持
     * @param suffix 图片文件后缀名
     * @return 检查结果
     */
    public static boolean checkSupportImageType(String suffix){
        return SUPPORT_IMAGE_TYPES.contains(suffix.toUpperCase());
    }
}
