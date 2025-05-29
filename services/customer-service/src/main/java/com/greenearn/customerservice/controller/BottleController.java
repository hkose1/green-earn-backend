package com.greenearn.customerservice.controller;


import com.greenearn.customerservice.dto.BottlePointsInfoResponseDto;
import com.greenearn.customerservice.dto.BottleResponseDto;
import com.greenearn.customerservice.enums.BottleSizeType;
import com.greenearn.customerservice.service.BottleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bottles")
@Slf4j
public class BottleController {

    private final BottleService bottleService;

    @GetMapping("/public/{bottleSizeType}")
    public ResponseEntity<BottleResponseDto> getBottleBySizeType(@PathVariable("bottleSizeType") BottleSizeType bottleSizeType) {
        return ResponseEntity.ok(bottleService.getBottleBySizeType(bottleSizeType));
    }

    @GetMapping("/public/points-info")
    public ResponseEntity<BottlePointsInfoResponseDto> getBottlePointsInfo() {
        return ResponseEntity.ok(bottleService.getBottlePointInfo());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BottleResponseDto>> getBottles() {
        return ResponseEntity.ok(bottleService.getBottles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBottlePointById(@PathVariable("id") UUID bottleId, @RequestBody Integer newBottlePoint) {
        bottleService.updateBottlePointById(bottleId, newBottlePoint);
        return ResponseEntity.ok().build();
    }




}
