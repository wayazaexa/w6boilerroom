package se.chasacademy.databaser.coursesystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "participants")
    Set<Course> courses;

    public Participant() {}

    public Participant(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
