package com.practice.pattern.chainofresponsibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

/**
 * 过滤器模式
 * <p>
 * 需要手动调用下一个链路过滤器，不调用就直接返回
 */
public class FilterDemo {

    public static void main(String[] args) {

        FilterBuilder builder = new FilterBuilder();
        builder.doFilter(new HttpRequest("1111111"));
    }
}


class FilterBuilder implements FilterChain {

    List<Filter> filters = new ArrayList<>();
    Iterator<Filter> filterIterator;

    @Override
    public void nextFilter(HttpRequest request, FilterChain chain) {
        boolean b = filterIterator.hasNext();
        if (b) {
            filterIterator.next().process(request, this);
        }
    }

    public FilterBuilder() {
        filters.add(new CodeFilter());
        filters.add(new UrlFilter());
        filters.add(new I18nFilter());
    }

    public void doFilter(HttpRequest request) {
        filterIterator = filters.iterator();
        boolean b = filterIterator.hasNext();
        if (b) {
            filterIterator.next().process(request, this);
        }
        System.out.println("处理完成");
    }
}


class HttpRequest {

    String name;

    public HttpRequest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HttpRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}

interface FilterChain {
    void nextFilter(HttpRequest request, FilterChain chain);
}


interface Filter {
    void process(HttpRequest request, FilterChain chain);
}


class UrlFilter implements Filter {
    @Override
    public void process(HttpRequest request, FilterChain chain) {

        System.out.println("UrlFilter  我处理过了，交给下一个处理。。");
        chain.nextFilter(request, chain);
    }
}

class CodeFilter implements Filter {
    @Override
    public void process(HttpRequest request, FilterChain chain) {

        System.out.println("CodeFilter  我处理过了，交给下一个处理。。");
        chain.nextFilter(request, chain);
    }
}

class I18nFilter implements Filter {
    @Override
    public void process(HttpRequest request, FilterChain chain) {

        System.out.println("I18nFilter  我处理过了，交给下一个处理。。");
        chain.nextFilter(request, chain);
    }
}