package com.info.configdemo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@AllArgsConstructor
@RefreshScope
public class BuildInfoController {

    @Value("${build.id:default id}")
    private String buildId;

    @Value("${build.version:default version}")
    private String buildVersion;

    @Value("${build.name:default name}")
    private String buildName;

    @Value("${build.description:default description}")
    private String buildDescription;

//    private BuildInfo buildInfo;

//    @GetMapping("/build-info")
//    public String getBuildInfo() {
//        return "Build ID: " + buildInfo.getId() + ",\n" +
//               "Build Version: " + buildInfo.getVersion() + ",\n" +
//               "Build Name: " + buildInfo.getName() + ",\n" +
//               "Build Description: " + buildInfo.getDescription();
//    }

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return "Build ID: " + buildId + ",\n" +
               "Build Version: " + buildVersion + ",\n" +
               "Build Name: " + buildName + ",\n" +
               "Build Description: " + buildDescription;
    }
}
