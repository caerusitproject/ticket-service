package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.AssetDto;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Long>>> createAsset(@Valid @RequestBody AssetDto assetDto) {
        Long id = assetService.createAsset(assetDto);
        Map<String, Long> data = Map.of("id", id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Asset created successfully", data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AssetDto>>> getAllAssets(
            @RequestParam(defaultValue = "false") Boolean deleted,
            @RequestParam(required = false) String search,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Assets retrieved successfully", assetService.getAllAssets(deleted, search, pageable)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetDto>> patchAssetById(@PathVariable Long id, @RequestBody AssetDto AssetDto) {
        AssetDto updatedSubcategory = assetService.patchAssetById(id, AssetDto);
        return ResponseEntity.ok(ApiResponse.success("Asset updated successfully", updatedSubcategory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AssetDto>> getAssetById(@PathVariable Long id) {
        AssetDto AssetDto = assetService.getAssetById(id);
        return ResponseEntity.ok(ApiResponse.success("Asset retrieved successfully", AssetDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAsset(@PathVariable Long id) {
        assetService.deleteAssetById(id);
        return ResponseEntity.noContent().build();
    }
}
