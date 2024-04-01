//package com.grab.config;
//
////import com.zywl.interceptor.JWTInterceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class MyWebMvcConfigurer extends WebMvcConfigurationSupport {
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(new JWTInterceptor())
////                //拦截的路径
////                .addPathPatterns("/**")
////                //排除登录接口
////                .excludePathPatterns("/user/login", "/user/register", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs", "/swagger-ui.html");
////    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // swagger配置
//        registry.
//                addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
//                .resourceChain(false);
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 允许跨域访问的路径
//        registry.addMapping("/**")
//                // 允许跨域访问的源
//                .allowedOriginPatterns("*")
//                // 允许请求的方法
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                // 允许跨域访问的头
//                .allowedHeaders("*")
//                // 是否允许发送Cookie
//                .allowCredentials(true)
//                // 预检请求的有效期，单位为秒
//                .maxAge(3600);
//    }
//}
