package com.renegz.pnccontroller.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sec01_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID code;

    private String isbn;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
