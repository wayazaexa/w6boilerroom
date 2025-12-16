package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.chasacademy.databaser.coursesystem.entities.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("""
        select t
        from Teacher t
        join t.courses c
        group by t
        having count(c) > :minCourses
    """)
    List<Teacher> findTeachersWithMoreThanNCourses(long minCourses);
}
