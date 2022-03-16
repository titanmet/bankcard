package com.ratnikov.bankcard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_generator")
    @SequenceGenerator(
            name="sequence_generator",
            sequenceName = "role_sequence",
            allocationSize = 1)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role")
    private String role;
}