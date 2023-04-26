package io.petra.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Value("${attachFileLocation}")
    String attachFileUploadPath;

    @Value("${attachFileUri}")
    String attachFileUri;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler(attachFileUri)
                .addResourceLocations(attachFileUploadPath + "/");
        log.info("Image service alarm ---- \n" +
                         "- images will service URL AT : {} \n" +
                         "- images directory saved  AT : {} \n",
                 attachFileUri, attachFileUploadPath);
    }
}
