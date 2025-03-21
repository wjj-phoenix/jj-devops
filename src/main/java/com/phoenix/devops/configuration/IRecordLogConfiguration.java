package com.phoenix.devops.configuration;

import com.phoenix.devops.function.CustomFunctionFactory;
import com.phoenix.devops.function.DefaultCustomFunction;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Configuration
public class IRecordLogConfiguration {
    @PostConstruct
    public void printBanner() {
        ClassPathResource classPathResource = new ClassPathResource("banner-log.txt");
        try (InputStreamReader reader = new InputStreamReader(classPathResource.getInputStream())) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Bean
    public CustomFunctionFactory CustomFunctionRegistrar(@Autowired List<DefaultCustomFunction> iCustomFunctionList) {
        return new CustomFunctionFactory(iCustomFunctionList);
    }
}
