package com.fruktkorgservice.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruktkorgCreateDTO {

    @Null
    private Long id;
    @NotBlank
    private String name;
    private List<FruktCreateDTO> fruktList = new ArrayList<>();
    private Instant lastChanged;
}
