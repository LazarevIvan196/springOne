package org.example.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task", schema = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "status" ,columnDefinition = "int")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

}
