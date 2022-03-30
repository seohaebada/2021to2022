package com.spring.batch.itemReader.jpaPaging;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long Id;
    private String username;
    private int age;

    @OneToOne(mappedBy = "customer")
    private Address address;

}
