package com.expense.management.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @PrePersist
    @PreUpdate
    public void normalizeName() {
        if (this.name != null) {
            this.name = this.name.toUpperCase();
            if (!this.name.startsWith("ROLE_")) {
                this.name = "ROLE_" + this.name;
            }
        }
    }
}
