package com.tang.es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
public class EsProperties {

    private String schema;

    private String clusterName;

    private String[] clusterNodes;

    private Integer connectionReqTimeOut;

    private Integer socketTimeOut;

    private Integer connectionTimeOut;

    private String userName;

    private String password;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String[] getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String[] clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public Integer getConnectionReqTimeOut() {
        return connectionReqTimeOut;
    }

    public void setConnectionReqTimeOut(Integer connectionReqTimeOut) {
        this.connectionReqTimeOut = connectionReqTimeOut;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public Integer getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(Integer connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
