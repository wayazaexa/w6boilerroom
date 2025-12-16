package se.chasacademy.databaser.coursesystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Min(1)
    private int capacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    List<CourseSession> sessions;

    public Room() {}

    public Room(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
