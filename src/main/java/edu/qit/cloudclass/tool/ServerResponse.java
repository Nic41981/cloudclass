package edu.qit.cloudclass.tool;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author nic
 * @version 1.0
 */

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg = "";
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this(status);
        this.msg = msg;
    }

    private ServerResponse(int status, T data) {
        this(status);
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this(status, msg);
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getStatus();
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getStatus());
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getStatus(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getStatus(), msg, data);
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getStatus(), msg);
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<>(ResponseCode.ERROR.getStatus());
    }

    public static <T> ServerResponse<T> createByError(String msg) {
        return new ServerResponse<>(ResponseCode.ERROR.getStatus(), msg);
    }

    public static <T> ServerResponse<T> createByError(int status, String msg) {
        return new ServerResponse<>(status, msg);
    }
}
