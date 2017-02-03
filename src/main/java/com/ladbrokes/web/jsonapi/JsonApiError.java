package com.ladbrokes.web.jsonapi;

import java.util.Properties;

/**
 * JSON api errors objects.
 */
public class JsonApiError {

    private int status;

    private String title;

    private String detail;

    private Properties source;

    public JsonApiError(int status){
        this.status = status;
    }

    public JsonApiError(int status, String source) {
        this.status = status;
        if (source!=null && !source.trim().isEmpty()) {
            this.source = new Properties();
            this.source.put("pointer","data/attributes/".concat(source));
        }
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Properties getSource() {
        return source;
    }
}
