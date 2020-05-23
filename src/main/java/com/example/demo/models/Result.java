package com.example.demo.models;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private int totalRecords;
    private List<String> data;
}
