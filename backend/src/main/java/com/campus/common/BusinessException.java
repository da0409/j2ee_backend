package com.campus.common;

/**
 * 业务异常（规范文档 5.2 / 6.2）。
 *
 * <p>在 Service 层抛出，由 {@link GlobalExceptionHandler} 统一转换为
 * {@code {code, message, data:null}} 结构。</p>
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        this(400, message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
