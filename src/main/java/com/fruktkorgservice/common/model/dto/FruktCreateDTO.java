package com.fruktkorgservice.common.model.dto;

import com.fruktkorgservice.common.model.Fruktkorg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruktCreateDTO {

    @Null
    private Long id;
    @NotBlank
    private String type;
    @NotNull
    private Integer amount;
    @Null
    private Fruktkorg fruktkorg;


}
