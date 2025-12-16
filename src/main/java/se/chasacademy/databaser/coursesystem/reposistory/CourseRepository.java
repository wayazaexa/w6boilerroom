package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.chasacademy.databaser.coursesystem.entities.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Find courses with teacher
    @Query("select c from Course c join fetch c.teacher")
    List<Course> findAllWithTeacher();

    // Find courses with participants
    @Query("select c from Course c join fetch c.participants")
    List<Course> findAllWithParticipants();
}
