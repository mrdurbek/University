package uz.university.springappuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.university.springappuniversity.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer>{
	boolean existsByNameAndUniversityId(String name , Integer university_id);
}
