package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import se.chasacademy.databaser.coursesystem.entities.CourseSession;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseSessionRepository extends JpaRepository<CourseSession, Long> {

    // all commend courses after days date
    List<CourseSession> findByDateAfter(LocalDateTime now);

    //  all courses for a local
    List<CourseSession> findByRoomId(Long roomId);
}
