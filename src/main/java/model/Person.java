package model;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Person {
    private String name;
    private int id;
    private double salary;
}
