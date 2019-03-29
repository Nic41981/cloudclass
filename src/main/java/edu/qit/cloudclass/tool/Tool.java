package edu.qit.cloudclass.tool;

import java.util.UUID;

/**
 * @author nic
 * @version 1.0
 */
public class Tool {
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
}
