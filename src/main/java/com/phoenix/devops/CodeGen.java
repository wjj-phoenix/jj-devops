package com.phoenix.devops;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.IDialect;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

/**
 * @author wjj-phoenix
 * @since 2025-02-17
 */
public final class CodeGen {
    public static void main(String[] args) {
        // 配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        Properties properties = CodeGen.readYaml();
        dataSource.setUrl(properties.getProperty("spring.datasource.url"));
        dataSource.setUsername(properties.getProperty("spring.datasource.username"));
        dataSource.setPassword(properties.getProperty("spring.datasource.password"));

        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置根包
        globalConfig.setAuthor("wjj-phoenix");
        globalConfig.setSince(LocalDate.now().toString());
        globalConfig.setBasePackage(CodeGen.class.getPackage().getName());

        globalConfig.setGenerateTable("resource_auth", "machine");

        // 设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setOverwriteEnable(true)
                // 设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
                // System.getProperty("java.specification.version") 在运行时获取jdk大版本号
                .setJdkVersion(Integer.parseInt(System.getProperty("java.specification.version")));

        globalConfig.enableTableDef().setOverwriteEnable(true);

        globalConfig.setMapperAnnotation(true);
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setMapperXmlGenerateEnable(true);

        globalConfig.setServiceClassPrefix("I");
        globalConfig.setServiceGenerateEnable(true);
        globalConfig.setServiceImplCacheExample(true);
        globalConfig.setServiceImplGenerateEnable(true);

        globalConfig.setControllerGenerateEnable(true);

        // 通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig, IDialect.MYSQL);

        // 生成代码
        generator.generate();
    }

    private static Properties readProperties() {
        final String CONF_NAME = "application-dev.properties";

        Properties properties = new Properties();
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(CONF_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private static Properties readYaml() {
        final String CONF_NAME = "application-dev.yaml";

        Resource resource = new ClassPathResource(CONF_NAME);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource);
        return yamlPropertiesFactoryBean.getObject();
    }
}
