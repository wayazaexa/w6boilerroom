package se.chasacademy.databaser.coursesystem;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.chasacademy.databaser.coursesystem.entities.*;
import se.chasacademy.databaser.coursesystem.reposistory.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Transactional
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
        CourseSession courseSession2 = new CourseSession(LocalDateTime.of(2025, 12, 17, 13, 0), course2, room1);
        CourseSession courseSession3 = new CourseSession(LocalDateTime.of(2025, 12, 17, 15, 0), course3, room2);
        CourseSession courseSession4 = new CourseSession(LocalDateTime.of(2025, 12, 17, 15, 0), course1, room1);
        courseSessionRepository.saveAll(List.of(courseSession1, courseSession2, courseSession3, courseSession4));

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
        System.out.println();
        System.out.println("Kurser med lärare:");
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            System.out.println("Inga kurser hittades.");
        }
        else {
            for (Course course : courses) {
                System.out.println("Kurs - Id: " + course.getId() + ", namn: " + course.getTitle()
                        + ", lärare: " + course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName());
            }
        }

        //- Kurser med antal deltagare
        System.out.println();
        System.out.println("Kurser med antal deltagare (och deltagare):");
        List<Course> coursesParticipants = courseRepository.findAll();
        if (courses.isEmpty()) {
            System.out.println("Inga kurser hittades.");
        }
        else {
            for (Course course : coursesParticipants) {
                System.out.println("Kurs - Id: " + course.getId() + ", namn: " + course.getTitle()
                        + ", antal deltagare: " + course.getParticipants().size() + ", deltagare: ");
                for (Participant participant : course.getParticipants()) {
                    System.out.println("  Name: " + participant.getFullName() + ", email: " + participant.getEmail());
                }
            }
        }

        //- Kommande kurstillfällen per lokal
        System.out.println();
        System.out.println("Kommande kurstillfällen per lokal:");
        List<CourseSession> courseSessions = courseSessionRepository.findByDateAfter(LocalDateTime.now());
        if (courseSessions.isEmpty()) {
            System.out.println("Inga kurstillfällen hittades");
        }
        else {
            for (Map.Entry<Room, List<CourseSession>> csEntry : courseSessions.stream()
                    .collect(Collectors.groupingBy(CourseSession::getRoom))
                    .entrySet()
                    .stream().toList()) {
                System.out.println("Rum: " + csEntry.getKey().getName());
                for (CourseSession cs : csEntry.getValue()) {
                    System.out.println("Kurstillfälle - Kurs: " + cs.getCourse().getTitle() + ", tid: " + cs.getDate());
                }
            }
        }

        //- Lokaler med capacity > X
        System.out.println();
        int capacity = 25;
        System.out.println("Lokaler med kapacitet > " + capacity);
        List<Room> rooms = roomRepository.findByCapacityGreaterThan(capacity);
        if (rooms.isEmpty()) {
            System.out.println("Inga rum hittades.");
        }
        else {
            for (Room room : rooms) {
                System.out.println("Rum - Id: " + room.getId() + ", namn: " + room.getName()
                        + ", adress: " + room.getAddress() + ", kapacitet: " + room.getCapacity());
            }
        }
    }
}
