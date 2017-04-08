package com.xms.task.exception;


import com.xms.task.code.Code;

public abstract class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Code code;

    private Object[] args;

    public ServiceException(Code code, Throwable cause) {
        super(code.name(), cause);
        this.code = code;
    }

    public ServiceException(Code code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return this.code.name();
    }

    @Override
    public String getMessage() {
        String reason = this.code.getReason();

        if (args == null) {
            return reason;
        }

        for (int i = 0, len = args.length; i < len; i++) {
            reason = reason.replaceAll("\\{" + i + "\\}", args[i].toString());
        }

        return reason;
    }

    public String[] getNames() {
        return this.code.getArgNames();
    }

    public Object[] getValues() {
        return this.args;
    }

}
