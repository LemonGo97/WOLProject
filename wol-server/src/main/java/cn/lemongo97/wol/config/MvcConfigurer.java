package cn.lemongo97.wol.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author LemonGo97
 */
@EnableWebMvc
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Autowired
    ResponseResultInterceptor responseResultInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseResultInterceptor);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}
