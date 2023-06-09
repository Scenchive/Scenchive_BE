package com.example.scenchive.domain.info.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NotesInfoResponse {
    private Long perfumeId;
    private String perfumeName;
    private String brandName;
    private List<String> top;
    private List<String> middle;
    private List<String> base;

    public NotesInfoResponse(Long perfumeId, String perfumeName, String brandName,
                             List<String> top, List<String> middle, List<String> base) {
        this.perfumeId = perfumeId;
        this.perfumeName = perfumeName;
        this.brandName = brandName;
        this.top = new ArrayList<>(top);
        this.middle = new ArrayList<>(middle);
        this.base = new ArrayList<>(base);
    }
}
