package com.xpr35.lunit;

/**
 * Created by xpres on 15/04/18.
 */
public class ReportEntry {
    public enum Status {
        SUCCESS, FAILED, ASSERTION_ERROR, IGNORED, ERROR
    }

    private String className;
    private String methodName;
    private Status status;
    private String cause;

    public ReportEntry(String className, String methodName, Status status, String cause) {
        this.className = className;
        this.methodName = methodName;
        this.status = status;
        this.cause = cause;
    }

    public ReportEntry(String className, String methodName, Status status) {
        this(className, methodName, status, "");
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Status getStatus() {
        return status;
    }

    public String getCause() {
        return cause;
    }
}
