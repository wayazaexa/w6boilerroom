package se.chasacademy.databaser.coursesystem.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CourseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public CourseSession() {}

    public CourseSession(LocalDateTime date, Course course, Room room) {
        this.date = date;
        this.course = course;
        this.room = room;
    }

    public Long getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public Course getCourse() { return course; }
    public Room getRoom() { return room; }

    public void setDate(LocalDateTime date) { this.date = date; }
    public void setCourse(Course course) { this.course = course; }
    public void setRoom(Room room) { this.room = room; }
}
