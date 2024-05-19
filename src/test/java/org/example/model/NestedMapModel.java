package org.example.model;

import lombok.Data;

import java.util.Map;

@Data
public class NestedMapModel {
    Map<String, Map<String, String>> mapProperty;
}
