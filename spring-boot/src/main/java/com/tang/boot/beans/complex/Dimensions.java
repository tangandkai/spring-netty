package com.tang.boot.beans.complex;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Dimensions {

    private List<String> client;
    private String STime;
    private String ETime;
    private List<Map<String, String>> dims;
    private List<String> field;
    private List<String> group;
    private List<String> values;

    public List<String> getClient() {
        return client;
    }

    public void setClient(List<String> client) {
        this.client = client;
    }

    public String getSTime() {
        return STime;
    }

    public void setSTime(String STime) {
        this.STime = STime;
    }

    public String getETime() {
        return ETime;
    }

    public void setETime(String ETime) {
        this.ETime = ETime;
    }

    public List<Map<String, String>> getDims() {
        return dims;
    }

    public void setDims(List<Map<String, String>> dims) {
        this.dims = dims;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    public List<String> getGroup() {
        return group;
    }

    public void setGroup(List<String> group) {
        this.group = group;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "client=" + client +
                ", STime='" + STime + '\'' +
                ", ETime='" + ETime + '\'' +
                ", dims=" + dims +
                ", field=" + field +
                ", group=" + group +
                ", values=" + values +
                '}';
    }
}
