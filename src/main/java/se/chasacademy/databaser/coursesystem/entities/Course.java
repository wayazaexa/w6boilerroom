package se.chasacademy.databaser.coursesystem.entities;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    private String description;

    @Min(1)
    @Max(50)
    @Column(nullable = false)
    private int maxParticipants;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // En Course har många CourseSession
    // Rimligt cascade: om man tar bort en Course → sessions bör tas bort (orphanRemoval).
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseSession> sessions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_participants",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private Set<Participant> participants = new HashSet<>();

    public Course() {}

    public Course(String title, String description, int maxParticipants, Teacher teacher) {
        this.title = title;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.teacher = teacher;
    }

    // helpers för M:N (håll relationen synkad)
    public void addParticipant(Participant p) {
        participants.add(p);
        p.getCourses().add(this);
    }

    public void removeParticipant(Participant p) {
        participants.remove(p);
        p.getCourses().remove(this);
    }

    // getters/setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getMaxParticipants() { return maxParticipants; }
    public Teacher getTeacher() { return teacher; }
    public List<CourseSession> getSessions() { return sessions; }
    public Set<Participant> getParticipants() { return participants; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}
