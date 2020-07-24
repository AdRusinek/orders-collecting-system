package com.collecting.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Getter
@Setter
@Entity(name = "ORDER_TABLE")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    private String customerFullName;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp dateOfPurchase;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

}
