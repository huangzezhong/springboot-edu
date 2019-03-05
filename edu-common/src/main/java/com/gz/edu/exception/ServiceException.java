package com.gz.edu.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 自定义异常
 *
 * @author LinkinStar
 */
public class ServiceException extends RuntimeException {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -8404029869798299145L;

    /**
     * 异常编码
     */
    private String errCode;

    public ServiceException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param errCode 异常编码
     * @param message 异常消息
     */
    public ServiceException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * 构造函数
     *
     * @param ex 异常
     */
    public ServiceException(Throwable ex) {
        super(ex);
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     * @param ex      异常
     */
    public ServiceException(String message, Throwable ex) {
        super(message, ex);
    }

    /**
     * 构造函数
     *
     * @param code    异常编码
     * @param message 异常消息
     * @param ex      异常
     */
    public ServiceException(String errCode, String message, Throwable ex) {
        super(message, ex);
        this.errCode = errCode;
    }

    /**
     * @return 返回 errCode
     */
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}