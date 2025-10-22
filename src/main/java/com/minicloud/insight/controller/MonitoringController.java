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
        log.info("CPU 부하 테스트 시작: n={}", n);

        long startTime = System.currentTimeMillis();

        // 피보나치 계산(CPU 부하)
        long result = fibonacci(n);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("CPU 부하 테스트 완료: 소요시간={}ms, 결과={}", duration, result);

        Map<String, Object> response = new HashMap<>();
        response.put("n", n);
        response.put("result", result);
        response.put("duration_ms", duration);
        response.put("message", "피보나치 계산 완료");

        return ResponseEntity.ok(response);
    }

    private long fibonacci(int n){
        if(n <= 1){
            return n;
        }
        return fibonacci(n-1) + fibonacci(n-2);
    }

    /**
     메모리 부하 테스트
     */
    @Operation(
            summary = "메모리 부하 테스트",
            description = "대용량 데이터를 메모리에 할당하여 메모리 부하를 발생시킵니다."
    )
    @GetMapping("/memory")
    public ResponseEntity<Map<String, Object>> memoryLoad(
            @RequestParam(defaultValue = "100") int sizeMB
    ){
        log.info("메모리 부하 테스트 시작: sizeMB={}", sizeMB);

        try{
            // sizeMB만큼 메모리 할당(1MB = 1024 * 1024 bytes)
            for(int i =0; i<sizeMB; i++){
                byte[] bytes = new byte[1024 * 1024];
                memoryList.add(bytes);
            }

            // 현재 메모리 사용량 확인
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
            long maxMemory = runtime.maxMemory() / (1024 * 1024);

            log.info("메모리 할당 완료: {}MB 할당됨", sizeMB);

            Map<String, Object> response = new HashMap<>();
            response.put("allocated_mb", sizeMB);
            response.put("total_allocated_mb", memoryList.size());
            response.put("used_memory_mb", usedMemory);
            response.put("max_memory_mb", maxMemory);
            response.put("message", "메모리 할당 완료");

            return ResponseEntity.ok(response);
        } catch(OutOfMemoryError e){
            log.error("메모리 부족 에러 발생", e);

            Map<String, Object> response = new HashMap<>();
            response.put("error", "OutOfMemoryError");
            response.put("message", "메모리 부족");

            return ResponseEntity.status(500).body(response);
        }
    }
    /**
     메모리 해제
     */
    @Operation(
            summary = "메모리 해제",
            description = "할당된 메모리를 해제하고 GC를 실행합니다."
    )
    @GetMapping("/memory/clear")
    public ResponseEntity<Map<String, Object>> clearMemory() {
        log.info("메모리 해제 시작");

        int clearedSize = memoryList.size();
        memoryList.clear();
        System.gc(); // GC 강제 실행 요청

        log.info("메모리 해제 완료: {}MB 해제됨", clearedSize);

        Map<String, Object> response = new HashMap<>();
        response.put("cleared_mb", clearedSize);
        response.put("message", "메모리 해제 완료");

        return ResponseEntity.ok(response);
    }
    /**
     에러 발생 테스트
     */
    @Operation(
            summary = "에러 발생 테스트",
            description = "의도적으로 에러를 발생시켜 로그 및 모니터링을 테스트합니다."
    )
    @GetMapping("/error")
    public ResponseEntity<Map<String, Object>> errorTest(
            @RequestParam(defaultValue = "runtime") String type
    ) {
        log.info("에러 테스트 시작: type={}", type);

        switch (type.toLowerCase()) {
            case "runtime":
                log.error("RuntimeException 발생!");
                throw new RuntimeException("의도적인 RuntimeException 발생");

            case "null":
                log.error("NullPointerException 발생!");
                throw new NullPointerException("의도적인 NullPointerException 발생");

            case "arithmetic":
                log.error("ArithmeticException 발생!");
                int result = 10 / 0; // Division by zero
                break;

            case "custom":
                log.error("Custom Exception 발생!");
                throw new IllegalStateException("의도적인 Custom Exception 발생");

            default:
                log.warn("알 수 없는 에러 타입: {}", type);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "알 수 없는 에러 타입: " + type);
                response.put("available_types", List.of("runtime", "null", "arithmetic", "custom"));
                return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(Map.of("message", "에러 발생 없음"));
    }
    /**
     시스템 상태 확인
     */
    @Operation(
            summary = "시스템 상태 확인",
            description = "현재 시스템의 CPU, 메모리 상태를 확인합니다."
    )
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> systemStatus() {
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory() / (1024 * 1024);

        Map<String, Object> response = new HashMap<>();
        response.put("total_memory_mb", totalMemory);
        response.put("free_memory_mb", freeMemory);
        response.put("used_memory_mb", usedMemory);
        response.put("max_memory_mb", maxMemory);
        response.put("allocated_memory_list_size", memoryList.size());
        response.put("available_processors", runtime.availableProcessors());

        return ResponseEntity.ok(response);
    }

}