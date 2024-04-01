package com.grab.job;

import org.springframework.beans.factory.annotation.Value;

public class BaseJob {

    @Value("${app.http.proxy.enable}")
    public boolean httpProxyEnable;

    @Value("${app.http.proxy.host}")
    public String httpProxyHost;

    @Value("${app.http.proxy.port}")
    public int httpProxyPort;

}
