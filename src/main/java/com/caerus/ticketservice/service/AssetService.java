package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.AssetDto;
import com.caerus.ticketservice.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface AssetService {
    Long createAsset(AssetDto assetDto);

    PageResponse<AssetDto> getAllAssets(String search, Pageable pageable);

    AssetDto patchAssetById(Long id, AssetDto AssetDto);

    AssetDto getAssetById(Long id);
}
