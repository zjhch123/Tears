package com.zjh.tears.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjiahao on 2017/2/17.
 */
public class ReverseProxy implements Comparable {
    private String rule;
    private String target;
    private Map<String, String> cookiesMapping;

    public ReverseProxy(String rule, String target) {
        this(rule, target, new HashMap<>());
    }

    public ReverseProxy(String rule, String target, Map<String, String> cookiesMapping) {
        this.rule = rule;
        this.target = target;
        this.cookiesMapping = cookiesMapping;
    }

    public String getCookieMappingOrDefault(String from, String defaultVal) {
        return this.cookiesMapping.getOrDefault(from, defaultVal);
    }

    public void addCookieMapping(String from, String to) {
        this.cookiesMapping.put(from, to);
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getCookiesMapping() {
        return cookiesMapping;
    }

    public void setCookiesMapping(Map<String, String> cookiesMapping) {
        this.cookiesMapping = cookiesMapping;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof ReverseProxy) {
            ReverseProxy obj = (ReverseProxy) o;
            return this.rule != null ? this.rule.compareTo(obj.getRule()) : (obj.rule == null ? 0 : -1);
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReverseProxy that = (ReverseProxy) o;
        return this.rule != null ? this.rule.equals(that.rule) : this.rule == null;
    }

    @Override
    public int hashCode() {
        return rule.hashCode();
    }

    @Override
    public String toString() {
        return "ReverseProxy{" +
                "rule='" + rule + '\'' +
                ", target='" + target + '\'' +
                ", cookiesMapping=" + cookiesMapping +
                '}';
    }
}
