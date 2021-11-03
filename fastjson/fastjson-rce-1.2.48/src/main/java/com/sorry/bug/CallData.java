package com.sorry.bug;

/**
 * created by 0x22cb7139 on 2021/8/30
 */
public class CallData {
    private String sessionId;

    private String called;

    private String caller;

    private String customField;

    private String recycleTime;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public void setRecycleTime(String recycleTime) {
        this.recycleTime = recycleTime;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getCalled() {
        return this.called;
    }

    public String getCaller() {
        return this.caller;
    }

    public String getCustomField() {
        return this.customField;
    }

    public String getRecycleTime() {
        return this.recycleTime;
    }
}

