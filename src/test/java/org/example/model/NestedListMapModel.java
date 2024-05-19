package org.example.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NestedListMapModel {
    Map<String, List<Integer>> mapProperty;
}
