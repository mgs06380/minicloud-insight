package com.minicloud.insight.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Monitoring API", description = "모니터링 및 부하 테스트 API")
public class MonitoringController {

    // 메모리 부하를 위한 리스트 (클래스 레벨)
    private List<byte[]> memoryList = new ArrayList<>();

    /**
     CPU 부하 테스트 - 피보나치 수열 계산
     **/
    @Operation(
            summary = "CPU 부하 테스트",
            description = "피보나치 수열을 계산하여 CPU 부하를 발생시킵니다. n 값이 클수록 부하가 증가합니다."
    )
    @GetMapping("/heavy")
    public ResponseEntity<Map<String, Object>> cpuLoad(
            @RequestParam(defaultValue = "35") int n
    ){
        sk
    }
}