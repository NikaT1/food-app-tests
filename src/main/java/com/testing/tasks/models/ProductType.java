package com.testing.tasks.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductType {
    FRUIT("Фрукт"),
    VEGETABLE("Овощ");

    @Getter
    private final String name;

    public static ProductType fromString(String value) {
        if (value != null) {
            for (ProductType productType : ProductType.values()) {
                if (productType.name.equalsIgnoreCase(value)) {
                    return productType;
                }
            }
        }
        throw new IllegalArgumentException("No such enum value");
    }
}
