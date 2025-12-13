package org.JakovSlafhauzer.praksa.zad3;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotBlank String name,
        @Positive double price,
        @NotBlank String category
) {}
