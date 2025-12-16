package se.chasacademy.databaser.coursesystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import se.chasacademy.databaser.coursesystem.entities.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

