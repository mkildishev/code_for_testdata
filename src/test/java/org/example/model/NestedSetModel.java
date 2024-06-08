package org.example.model;

import lombok.Data;

import java.util.Set;

@Data
public class NestedSetModel {
    Set<Set<String>> set;
}
