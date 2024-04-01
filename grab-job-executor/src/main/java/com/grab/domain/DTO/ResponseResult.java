package com.grab.domain.DTO;



import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.util.HashMap;
import java.util.Objects;

/**
 * 操作消息提醒
 */
public class ResponseResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标识
     */
    public static final String SUCCESS_TAG = "success";

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MESSAGE_TAG = "message";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 ResponseResult 对象，使其表示一个空消息。
     */
    public ResponseResult() {
    }

    /**
     * 初始化一个新创建的 ResponseResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public ResponseResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MESSAGE_TAG, msg);
    }

    /**
     * 初始化一个新创建的 ResponseResult 对象
     *
     * @param success 成功标识
     * @param code    状态码
     * @param msg     返回内容
     * @param data    数据对象
     */
    public ResponseResult(boolean success, int code, String msg, Object data) {
        super.put(SUCCESS_TAG, success);
        super.put(CODE_TAG, code);
        super.put(MESSAGE_TAG, msg);
        if (null != data) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static ResponseResult success() {
        return ResponseResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static ResponseResult success(Object data) {
        return ResponseResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static ResponseResult success(String msg) {
        return ResponseResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResponseResult success(String msg, Object data) {
        return new ResponseResult(true, HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ResponseResult warn(String msg) {
        return ResponseResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static ResponseResult warn(String msg, Object data) {
        return new ResponseResult(false, HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static ResponseResult error() {
        return ResponseResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static ResponseResult error(String msg) {
        return ResponseResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static ResponseResult error(String msg, Object data) {
        return new ResponseResult(false, HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 错误消息
     */
    public static ResponseResult error(int code, String msg) {
        return new ResponseResult(false, code, msg, null);
    }

    /**
     * 判定
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResponseResult decide(boolean success, String msg) {
        return decide(success, msg, null);
    }

    /**
     * 判定
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static ResponseResult decide(boolean success, String msg, Object data) {
        if (success) {
            if (null != data) {
                return success(msg, data);
            } else {
                if (StringUtils.isNotEmpty(msg)) {
                    return success(msg);
                } else {
                    return success();
                }
            }
        } else {
            return error(msg);
        }
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public boolean isSuccess() {
        return Objects.equals(HttpStatus.SUCCESS, this.get(CODE_TAG)) && Objects.equals(true, this.get(SUCCESS_TAG));
    }

    /**
     * 是否为警告消息
     *
     * @return 结果
     */
    public boolean isWarn() {
        return Objects.equals(HttpStatus.WARN, this.get(CODE_TAG)) && Objects.equals(false, this.get(SUCCESS_TAG));
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public boolean isError() {
        return Objects.equals(HttpStatus.ERROR, this.get(CODE_TAG)) && Objects.equals(false, this.get(SUCCESS_TAG));
    }

    /**
     * 消息
     *
     * @return 消息
     */
    public String getMessage() {
        return get(MESSAGE_TAG).toString();
    }

    /**
     * 方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public ResponseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
