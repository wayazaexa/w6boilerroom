package se.chasacademy.databaser.coursesystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.chasacademy.databaser.coursesystem.entities.*;
import se.chasacademy.databaser.coursesystem.reposistory.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class CourseSystemApplication implements CommandLineRunner {
    private final CourseRepository courseRepository;
    private final CourseSessionRepository courseSessionRepository;
    private final ParticipantRepository participantRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;

    public CourseSystemApplication(CourseRepository courseRepository,
                                   CourseSessionRepository courseSessionRepository,
                                   ParticipantRepository participantRepository,
                                   RoomRepository roomRepository,
                                   TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.courseSessionRepository = courseSessionRepository;
        this.participantRepository = participantRepository;
        this.roomRepository = roomRepository;
        this.teacherRepository = teacherRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourseSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //1. Skapar grunddata
        //- 1–2 lärare
        Teacher teacher1 = new Teacher("Kalle", "Karlsson", "kalle.karlsson@skolmail.se");
        Teacher teacher2 = new Teacher("Pelle", "Persson", "pelle.persson@skolmail.se");
        teacherRepository.saveAll(List.of(teacher1, teacher2));

        //- 2–3 kurser kopplade till en lärare
        Course course1 = new Course("Java", "Grundläggande programmering i java", 30, teacher1);
        Course course2 = new Course("Databaser", "Databasmodellering och integration till Java", 25, teacher2);
        Course course3 = new Course("Frontend", "Fronend och tillgänglighet", 27, teacher1);
        courseRepository.saveAll(List.of(course1, course2, course3));

        //- 2 lokaler (t.ex. “Sal A” cap=10, “Stora salen” cap=50)
        Room room1 = new Room("Limhamnstorg", "Scandic Malmö", 40);
        Room room2 = new Room("Möllevågen", "Scandic Malmö", 30);
        roomRepository.saveAll(List.of(room1, room2));

        //- 3–5 deltagare
        Participant participant1 = new Participant("Stina Stinasdotter", "stina.stinasson@student.skolmail.se");
        Participant participant2 = new Participant("Lisa Lisasdotter", "lisa.lisasdotter@student.skolmail.se");
        Participant participant3 = new Participant("Olle Olsson", "olle.olsson@student.skolmail.se");
        Participant participant4 = new Participant("Martin Martinsson", "martin.martinsson@student.skolmail.se");
        Participant participant5 = new Participant("Sven Svensson", "sven.svensson@student.skolmail.se");
        participantRepository.saveAll(List.of(participant1, participant2, participant3, participant4, participant5));


        //2. Kopplar relationer
        //- Lägg till deltagare på minst en kurs (M:N)
        course1.addParticipant(participant1);
        course1.addParticipant(participant2);

        course2.addParticipant(participant3);
        course2.addParticipant(participant4);
        course2.addParticipant(participant5);

        course3.addParticipant(participant1);
        course3.addParticipant(participant3);
        course3.addParticipant(participant5);
        courseRepository.saveAll(List.of(course1, course2, course3));


        //- Skapa 2–3 `CourseSession`:
        //- Koppla varje session till en `Course` och en `Room`
        //- Ange datum i framtiden / dåtid
        CourseSession courseSession1 = new CourseSession(LocalDateTime.of(2025, 12, 16, 11, 0), course1, room1);
        CourseSession courseSession2 = new CourseSession(LocalDateTime.of(2025, 12, 16, 13, 0), course2, room1);
        CourseSession courseSession3 = new CourseSession(LocalDateTime.of(2025, 12, 16, 15, 0), course3, room2);
        courseSessionRepository.saveAll(List.of(courseSession1, courseSession2, courseSession3));

        //3. Testar constraints
        //- Försök skapa:
        //- En `Teacher` utan email → ska ge fel (null/constraint)
        Teacher teacherFel = new Teacher("Nope", "Nopesson", null);
        //teacherRepository.saveAll(List.of(teacherFel));

        //- En `Room` med `capacity = 0` → ska ge fel
        Room roomFel = new Room("Felrum", "Ingenstans", 0);
        //roomRepository.saveAll(List.of(roomFel));

        //- En `CourseSession` utan lokal → ska ge fel
        CourseSession courseSessionFel = new CourseSession(LocalDateTime.of(2025, 12, 17, 11, 0), course1, null);
        //courseSessionRepository.saveAll(List.of(courseSessionFel));


        //4. Kör queries
        //- Skriv ut:
        //- Kurser med lärare

        //- Kurser med antal deltagare

        //- Kommande kurstillfällen per lokal

        //- Lokaler med capacity > X

    }
}
