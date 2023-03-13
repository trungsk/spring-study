package com.demo.unittesting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author itsol.trung.nt
 * all
 * Friday. 10 Mar 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fighter")
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "age")
    private int age;
    @Column(name = "division")
    private String division;
    @Column(name = "name")
    private String name;

    public Fighter(int age, String division, String name){
        this.age = age;
        this.division = division;
        this.name = name;
    }
}
