package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.Asset;
import com.caerus.ticketservice.dto.AssetDto;
import com.caerus.ticketservice.dto.PageResponse;
import com.caerus.ticketservice.enums.ErrorCode;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.AssetMapper;
import com.caerus.ticketservice.repository.AssetRepository;
import com.caerus.ticketservice.repository.spec.AssetSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {

  private final AssetRepository assetRepository;
  private final AssetMapper assetMapper;
  private final AssetSpecification assetSpecification;

  @Override
  public Long createAsset(AssetDto assetDto) {
    Asset savedAsset = assetRepository.save(assetMapper.toEntity(assetDto));
    return savedAsset.getId();
  }

  @Override
  public PageResponse<AssetDto> getAllAssets(Boolean deleted, String search, Pageable pageable) {
    boolean isDeleted = Boolean.TRUE.equals(deleted);

    Specification<Asset> spec =
        Specification.allOf(
            assetSpecification.deletedEquals(isDeleted), assetSpecification.search(search));

    Page<Asset> page = assetRepository.findAll(spec, pageable);

    Page<AssetDto> dtoPage = page.map(assetMapper::toDto);
    return PageResponse.from(dtoPage);
  }

  @Override
  public AssetDto patchAssetById(Long id, AssetDto AssetDto) {
    Asset asset = getAssetByIdOrThrowError(id);
    assetMapper.patchAssetFromDto(AssetDto, asset);
    return assetMapper.toDto(assetRepository.save(asset));
  }

  @Override
  public AssetDto getAssetById(Long id) {
    Asset asset = getAssetByIdOrThrowError(id);
    return assetMapper.toDto(asset);
  }

  @Override
  public void deleteAssetById(Long id) {
    Asset asset = getAssetByIdOrThrowError(id);
    asset.setDeleted(true);
    assetRepository.save(asset);
    log.info("Asset with id {} marked deleted successfully", id);
  }

  private Asset getAssetByIdOrThrowError(Long id) {
    return assetRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorCode.ASSET_NOT_FOUND.getMessage(id)));
  }
}
