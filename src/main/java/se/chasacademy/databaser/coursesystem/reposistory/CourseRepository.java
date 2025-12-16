package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.chasacademy.databaser.coursesystem.entities.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c join fetch c.teacher")
    List<Course> findAllWithTeacher();
}
