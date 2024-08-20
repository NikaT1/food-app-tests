package com.testing.tasks.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class Product {
    private final String name;
    private final ProductType type;
    private final boolean exotic;
}
