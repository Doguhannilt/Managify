package com.econ.managify.model;

import jakarta.persistence.*;

@Entity
public class Issues {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User assigned;
}
