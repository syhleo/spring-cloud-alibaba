package com.alibaba.cloud.nacos.parser;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NacosDataParserHandlerTest {

    private NacosDataParserHandler nacosDataParserHandler;

    @BeforeEach
    void setUp() {
        nacosDataParserHandler = NacosDataParserHandler.getInstance();
    }

    @Test
    void parseNacosData_EmptyConfigValue_ReturnsEmptyList() throws IOException {
        String configName = "test";
        String configValue = "";
        String extension = "properties";

        List<PropertySource<?>> propertySources = nacosDataParserHandler.parseNacosData(configName, configValue, extension);

        assertTrue(propertySources.isEmpty());
    }

    @Test
    void parseNacosData_ValidPropertiesExtension_ReturnsPropertySource() throws IOException {
        String configName = "test";
        String configValue = "key=value";
        String extension = "properties";

        PropertySourceLoader mockLoader = mock(PropertySourceLoader.class);
        when(mockLoader.load(anyString(), any())).thenReturn(Collections.singletonList(mock(PropertySource.class)));

        // Mock the SpringFactoriesLoader to return our mocked loader
        try (MockedStatic< SpringFactoriesLoader > mocked = mockStatic(SpringFactoriesLoader.class)) {
            mocked.when(() -> SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader()))
                  .thenReturn(Collections.singletonList(mockLoader));

            List<PropertySource<?>> propertySources = nacosDataParserHandler.parseNacosData(configName, configValue, extension);

            assertFalse(propertySources.isEmpty());
        }
    }

    @Test
    void parseNacosData_ValidYamlExtension_ReturnsPropertySource() throws IOException {
        String configName = "test";
        String configValue = "key: value";
        String extension = "yaml";

        PropertySourceLoader mockLoader = mock(PropertySourceLoader.class);
        when(mockLoader.load(anyString(), any())).thenReturn(Collections.singletonList(mock(PropertySource.class)));

        // Mock the SpringFactoriesLoader to return our mocked loader
        try (MockedStatic< SpringFactoriesLoader > mocked = mockStatic(SpringFactoriesLoader.class)) {
            mocked.when(() -> SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader()))
                  .thenReturn(Collections.singletonList(mockLoader));

            List<PropertySource<?>> propertySources = nacosDataParserHandler.parseNacosData(configName, configValue, extension);

            assertFalse(propertySources.isEmpty());
        }
    }

    @Test
    void parseNacosData_EmptyExtension_DetectsFromConfigName() throws IOException {
        String configName = "test.properties";
        String configValue = "key=value";
        String extension = "";

        PropertySourceLoader mockLoader = mock(PropertySourceLoader.class);
        when(mockLoader.load(anyString(), any())).thenReturn(Collections.singletonList(mock(PropertySource.class)));

        // Mock the SpringFactoriesLoader to return our mocked loader
        try (MockedStatic< SpringFactoriesLoader > mocked = mockStatic(SpringFactoriesLoader.class)) {
            mocked.when(() -> SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader()))
                  .thenReturn(Collections.singletonList(mockLoader));

            List<PropertySource<?>> propertySources = nacosDataParserHandler.parseNacosData(configName, configValue, extension);

            assertFalse(propertySources.isEmpty());
        }
    }

    @Test
    void parseNacosData_InvalidExtension_NoMatchingLoader() throws IOException {
        String configName = "test";
        String configValue = "key=value";
        String extension = "invalid";

        List<PropertySource<?>> propertySources = nacosDataParserHandler.parseNacosData(configName, configValue, extension);

        assertTrue(propertySources.isEmpty());
    }
}
