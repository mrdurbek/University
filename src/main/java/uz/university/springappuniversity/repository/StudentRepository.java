package uz.university.springappuniversity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uz.university.springappuniversity.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	List<Student> findAllByGroup_Faculty_UniversityId(Integer university_id);
	List<Student> findAllByGroup_FacultyId(Integer faculty_id);
	List<Student> findAllByGroupId(Integer group_id);
	
	@Query(value = "delete from student_subject where student_id=:student_id" , nativeQuery = true)
	public void deleteStudentById(Integer student_id);
}
