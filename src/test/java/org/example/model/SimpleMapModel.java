package org.example.model;

import lombok.Data;

import java.util.Map;

@Data
public class SimpleMapModel {
    Map<String, Integer> mapProperty;
}
