package org.example.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimpleTestModel {
    private Integer id;
    private String name;
    private BigDecimal price;
}
