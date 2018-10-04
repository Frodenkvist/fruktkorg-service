package com.fruktkorgservice.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruktkorgUpdateDTO {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private List<FruktUpdateDTO> fruktList = new ArrayList<>();
    @NotNull
    private Instant lastChanged;

}
