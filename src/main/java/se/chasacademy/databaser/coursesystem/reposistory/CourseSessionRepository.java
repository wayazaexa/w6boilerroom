package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import se.chasacademy.databaser.coursesystem.entities.CourseSession;

public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {
}
