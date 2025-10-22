package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Asset;
import com.caerus.ticketservice.dto.AssetDto;
import com.caerus.ticketservice.mapper.AssetMapper;
import com.caerus.ticketservice.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    @Override
    public Long createAsset(AssetDto assetDto) {
        assetMapper.toEntity(assetDto);
        Asset savedAsset = assetRepository.save(assetMapper.toEntity(assetDto));
        return savedAsset.getId();
    }
}
