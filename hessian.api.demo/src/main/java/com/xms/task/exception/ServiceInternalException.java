package com.xms.task.exception;


import com.xms.task.code.Code;

public class ServiceInternalException extends ServiceException {

    public ServiceInternalException(Code code, Object... args) {
        super(code, args);
    }

    public ServiceInternalException(Code code, Throwable cause) {
        super(code, cause);
    }

}
