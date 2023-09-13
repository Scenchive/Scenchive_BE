package com.example.scenchive.domain.filter.dto;

import lombok.Getter;

@Getter
public class PerfumeFullInfoDto {
    private Long id; // 향수 id
    private String perfumeName; // 향수 이름
    private String perfumeImage; // 향수 이미지
    private String brandName; // 브랜드 이름
    private String brandName_kr; // 브랜드 이름 (한글)

    public PerfumeFullInfoDto(Long id, String perfumeName, String perfumeImage, String brandName, String brandName_kr) {
        this.id = id;
        this.perfumeName = perfumeName;
        this.perfumeImage = perfumeImage;
        this.brandName = brandName;
        this.brandName_kr = brandName_kr;
    }
}