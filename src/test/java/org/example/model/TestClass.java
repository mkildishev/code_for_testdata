package org.example.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class TestClass {
    private Integer id;
    private String name;
    private BigDecimal price;
    private List<String> stringProperties;
    private Map<String, Integer> mapProperties;
   // private NestedTestClass objectProperties;
}
