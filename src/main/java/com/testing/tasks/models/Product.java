package com.testing.tasks.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Product {

    private final String name;
    private final ProductType type;
    private final boolean exotic;

}
