package com.expense.management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3, message="Title must have at least 3 characters")
    private String title;

    private String description;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean paid = false;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "company_id", nullable = false)
    private Company company;

    public Expense(long id, String testTitle, String testDescription, BigDecimal testAmount, LocalDateTime testDate, LocalDateTime testDueDate, boolean testPaid) {
        this.id = id;
        this.title = testTitle;
        this.description = testDescription;
        this.amount = testAmount;
        this.date = testDate;
        this.dueDate = testDueDate;
        this.paid = testPaid;
    }

    @PrePersist
    protected void onCreate(){
        if(date==null){
            date= LocalDateTime.now();
        }
        if (dueDate == null) {
            dueDate = date.plusDays(7);  // Default due date = 7 days after creation
        }
    }
}
