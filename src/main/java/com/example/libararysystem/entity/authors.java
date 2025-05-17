package com.example.libararysystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authors")
public class authors {
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
@Column(name = "author_id" , columnDefinition = "INTEGER")
private Long id;

@Column(name = "first_name")
private String firstName;
@Column(name = "last_name")
private String lastName;
    @Column(name = "birth_year")
private int birthYear;
    @Column(name = "nationality")
private String nationality;
public authors() {}

}
