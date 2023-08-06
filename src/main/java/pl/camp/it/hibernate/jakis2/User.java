package pl.camp.it.hibernate.jakis2;

import jakarta.persistence.Entity;

@Entity(name = "tuser2")
public class User {
    private int id;
    private int login;
}