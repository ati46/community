package com.learn.community.infrastructure.config;

import java.util.Map;

public interface IApplicationConfig {
    public String getOrigins();
    public Map<Integer,String> getUserlevel();
}
