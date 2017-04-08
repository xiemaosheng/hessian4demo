package com.xms.task.exception;


import com.xms.task.code.Code;

public class ServiceCheckException extends ServiceException {

    public ServiceCheckException(Code code, Object... args) {
        super(code, args);
    }

}
