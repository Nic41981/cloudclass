package edu.qit.cloudclass.tool;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author nic
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 一般错误
     */
    ERROR(-1, "ERROR"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(-2, "PERMISSION_DENIED"),
    /**
     * 缺少参数
     */
    MISSING_ARGUMENT(-3, "MISSING_ARGUMENT"),
    /**
     * 参数非法
     */
    ILLEGAL_ARGUMENT(-4, "ILLEGAL_ARGUMENT");

    private int status;
    private String msg;
}
