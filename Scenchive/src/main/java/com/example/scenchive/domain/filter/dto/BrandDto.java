package com.example.scenchive.domain.filter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandDto {
    private String brandName;
    private String brandName_kr;
    private String brandImage;

    public BrandDto(String brandName, String brandName_kr, String brandImage) {
        this.brandName = brandName;
        this.brandName_kr = brandName_kr;
        this.brandImage = brandImage;
    }
}
