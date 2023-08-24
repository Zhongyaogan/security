package com.security_03.vo;

import com.alibaba.fastjson.JSON;
import com.security_03.domain.Permission;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
public class PermissionDescriptVo {
    private String title;
    private String url;
    private Object descript;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getDescript() {
        return descript;
    }

    public void setDescript(Object descript) {
        this.descript = descript;
    }

    public PermissionDescriptVo(String title, String url, Object descript) {
        this.title = title;
        this.url = url;
        this.descript = descript;
    }

    public PermissionDescriptVo(Permission permission){
        this.title = permission.getTitle();
        this.url = permission.getUrl();
        this.descript = JSON.parse(permission.getPermissionDescription());
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", descript=" + descript +
                '}';
    }
}
