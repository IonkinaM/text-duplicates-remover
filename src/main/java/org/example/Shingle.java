package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"minHash"})
@AllArgsConstructor
public class Shingle {
    private String content;
    private String minHash;
}
