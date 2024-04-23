package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class NestedListModel {
    List<List<String>> list;
}
