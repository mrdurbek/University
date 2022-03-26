package uz.university.springappuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.university.springappuniversity.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	
	boolean existsByName(String name);
}
