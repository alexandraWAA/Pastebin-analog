package com.example.demo.model;

import com.example.demo.enums.Status;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
public class Paste {
    @Id
    private String id;
    private Instant dateExpired;
    private Instant dateCreated;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private Status status;
}