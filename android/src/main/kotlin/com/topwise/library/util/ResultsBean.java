//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

public class ResultsBean {
    private String rescode = null;
    private String meaning = null;
    private String type = null;
    private String cause = null;
    private String show = null;

    public ResultsBean(String meaning, String type, String cause, String show) {
        this.meaning = meaning;
        this.type = type;
        this.cause = cause;
        this.show = show;
    }

    public String getMeaning() {
        return this.meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getShow() {
        return this.show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getRescode() {
        return this.rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }
}
