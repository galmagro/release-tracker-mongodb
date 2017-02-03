package com.ladbrokes.web.jsonapi;

import java.util.Properties;

/**
 * Base class for DTO objects following JSON API standard (http://www.jsonapi.org).
 */
public class ResourceObject extends ResourceIdentifier{

    private Properties attributes = new Properties();


    public Properties getAttributes() {
        return attributes;
    }

    public String getAttribute(String attributeName){
        return attributes.getProperty(attributeName);
    }

    public void setAttribute(String name, String value){
        attributes.setProperty(name,value);
    }


    public static class SingleDataContainer {

        private ResourceIdentifier data;

        public SingleDataContainer() {
        }

        public SingleDataContainer(ResourceIdentifier data) {
            this.data = data;
        }

        public ResourceIdentifier getData() {
            return data;
        }

        public void setData(ResourceIdentifier data) {
            this.data = data;
        }
    }
}
