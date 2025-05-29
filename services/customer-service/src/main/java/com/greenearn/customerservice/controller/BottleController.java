package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.BottlePointsInfoResponseDto;
import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.entity.BottleEntity;
import com.greenearn.customerservice.enums.BottleSizeType;
import com.greenearn.customerservice.service.BottleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bottles")
@Slf4j
public class BottleController {

    private final BottleService bottleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{bottleSizeType}")
    public ResponseEntity<BottleResponseDto> getBottleBySizeType(@PathVariable("bottleSizeType") BottleSizeType bottleSizeType) {
        return ResponseEntity.ok(bottleService.getBottleBySizeType(bottleSizeType));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/points-info")
    public ResponseEntity<BottlePointsInfoResponseDto> getBottlePointsInfo() {
        return ResponseEntity.ok(bottleService.getBottlePointInfo());
    }




}
