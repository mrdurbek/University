package uz.university.springappuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.university.springappuniversity.entity.Groups;

public interface GroupsRepository extends JpaRepository<Groups , Integer> {
	boolean existsByNameAndFacultyId(String name , Integer faculty_id);
}
