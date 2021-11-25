package com.practice.pattern.builder;

import java.util.HashMap;
import java.util.Map;

public class URLBuilder {

    public static void main(String[] args) {
        String url = URLBuilder.builder() // 创建Builder
//                         .setDomain("www.liaoxuefeng.com") // 设置domain
                .setScheme("https") // 设置scheme
                .setPath("/") // 设置路径
//                         .setQuery(Map.of("a", "123", "q", "K&R")) // 设置query
                .build(); // 完成build

        System.out.println(url);

    }

    /**
     * String url = URLBuilder.builder() // 创建Builder
     * .setDomain("www.liaoxuefeng.com") // 设置domain
     * .setScheme("https") // 设置scheme
     * .setPath("/") // 设置路径
     * .setQuery(Map.of("a", "123", "q", "K&R")) // 设置query
     * .build(); // 完成build
     */


    public static Builder builder() {
        Builder builder = new Builder();
        return builder;
    }

    public static class Builder {
        String domain;
        String scheme;
        String path;
        //不想实现query
        Map<String, String> query = new HashMap<>();

        public Builder setDomain(String _domain) {
            this.domain = _domain;
            return this;
        }

        public Builder setScheme(String _scheme) {
            this.scheme = _scheme;
            return this;
        }

        public Builder setPath(String _path) {
            this.path = _path;
            return this;
        }

        public Builder setQuery(Map<String, String> param) {
            query.putAll(param);
            return this;
        }

        public String build() {
            String url = "";
            if (null != scheme) {
                url = scheme + "://";
            }
            if (null == domain) {
                throw new RuntimeException("domain 不能为空");
            }
            url += domain;
            if (null != path) {
                url += "/" + path;
            }

            return url;
        }


    }


}
