package com.dyq.springboot.mbsass.entity;

import java.io.Serializable;

/**
 * sass租户数据库信息(Tenant)实体类
 *
 * @author makejava
 * @since 2023-04-08 12:59:27
 */
public class Tenant implements Serializable {
    private static final long serialVersionUID = 902354860490148578L;

    private Integer id;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 数据库类型
     */
    private String type;
    /**
     * 数据库驱动
     */
    private String driverClassName;
    /**
     * 数据库连接地址
     */
    private String jdbcUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库
     */
    private String dataBase;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tenant tenant = (Tenant) o;

        if (id != null ? !id.equals(tenant.id) : tenant.id != null) return false;
        if (tenantName != null ? !tenantName.equals(tenant.tenantName) : tenant.tenantName != null) return false;
        if (type != null ? !type.equals(tenant.type) : tenant.type != null) return false;
        if (driverClassName != null ? !driverClassName.equals(tenant.driverClassName) : tenant.driverClassName != null)
            return false;
        if (jdbcUrl != null ? !jdbcUrl.equals(tenant.jdbcUrl) : tenant.jdbcUrl != null) return false;
        if (username != null ? !username.equals(tenant.username) : tenant.username != null) return false;
        if (password != null ? !password.equals(tenant.password) : tenant.password != null) return false;
        return dataBase != null ? dataBase.equals(tenant.dataBase) : tenant.dataBase == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenantName != null ? tenantName.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (driverClassName != null ? driverClassName.hashCode() : 0);
        result = 31 * result + (jdbcUrl != null ? jdbcUrl.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dataBase != null ? dataBase.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", tenantName='" + tenantName + '\'' +
                ", type='" + type + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dataBase='" + dataBase + '\'' +
                '}';
    }
}

