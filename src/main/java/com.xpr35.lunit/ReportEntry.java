package com.xpr35.lunit;

/**
 * Created by xpres on 15/04/18.
 */
public class ReportEntry {
    private String className;
    private String methodName;
    private String status;
    private String cause;

    public ReportEntry(String className, String methodName, String status, String cause) {
        this.className = className;
        this.methodName = methodName;
        this.status = status;
        this.cause = cause;
    }

    public ReportEntry(String className, String methodName, String status) {
        this(className, methodName, status, "");
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getStatus() {
        return status;
    }

    public String getCause() {
        return cause;
    }
}
