package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.BottlePointsInfoResponseDto;
import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.enums.BottleSizeType;
import com.greenearn.customerservice.service.BottleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bottles")
@Slf4j
public class BottleController {

    private final BottleService bottleService;

    @GetMapping("/{bottleSizeType}")
    public ResponseEntity<BottleResponseDto> getBottleBySizeType(@PathVariable("bottleSizeType") BottleSizeType bottleSizeType) {
        return ResponseEntity.ok(bottleService.getBottleBySizeType(bottleSizeType));
    }

    @GetMapping("/points-info")
    public ResponseEntity<BottlePointsInfoResponseDto> getBottlePointsInfo() {
        return ResponseEntity.ok(bottleService.getBottlePointInfo());
    }




}
