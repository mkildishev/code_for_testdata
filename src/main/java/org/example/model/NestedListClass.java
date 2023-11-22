package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class NestedListClass {
    List<List<String>> list;
}
