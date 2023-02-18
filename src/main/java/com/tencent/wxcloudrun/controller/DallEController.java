package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.DallEApiWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dalle")
@Slf4j
public class DallEController {

    @Resource
    private DallEApiWrapper dallEApiWrapper;

    @GetMapping("/generations")
    public ApiResponse generations(@RequestParam(name = "prompts") String prompts){
        String url = dallEApiWrapper.generations(prompts);
        log.info("prompts={}, url={}", prompts, url);
        return ApiResponse.ok(url);
    }


}
