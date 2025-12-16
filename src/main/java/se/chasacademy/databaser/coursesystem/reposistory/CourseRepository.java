package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import se.chasacademy.databaser.coursesystem.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
