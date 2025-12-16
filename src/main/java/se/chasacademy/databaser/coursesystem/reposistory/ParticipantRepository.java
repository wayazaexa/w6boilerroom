package se.chasacademy.databaser.coursesystem.reposistory;


import org.springframework.data.jpa.repository.JpaRepository;
import se.chasacademy.databaser.coursesystem.entities.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}