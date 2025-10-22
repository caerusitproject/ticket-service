package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.AssetDto;
import com.caerus.ticketservice.payload.SuccessResponse;
import com.caerus.ticketservice.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<SuccessResponse<Map<String, Long>>> createAsset(@Valid @RequestBody AssetDto assetDto) {
        Long id = assetService.createAsset(assetDto);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse<>("Asset created successfully", data));
    }

}
