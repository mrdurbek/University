package uz.university.springappuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.university.springappuniversity.entity.University;

public interface UniversityRepository extends JpaRepository<University , Integer> {}
