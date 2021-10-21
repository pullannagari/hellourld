package com.cloudflare.hellourld.utils;

import org.springframework.stereotype.Component;

@Component
public class Links {
    public static final String URLS = "/urls";
    public static final String URL = "/url/{id}";
    public static final String CREATE_URL = "/url";
    public static final String UPDATE_URL = "/url";
    public static final String DELETE_URL = "/url/{id}";
}
