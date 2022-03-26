package uz.university.springappuniversity.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uz.university.springappuniversity.entity.Faculty;
import uz.university.springappuniversity.entity.University;
import uz.university.springappuniversity.model.FacultyDto;
import uz.university.springappuniversity.repository.FacultyRepository;
import uz.university.springappuniversity.repository.UniversityRepository;

@RestController
@RequestMapping(value = "/faculty")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name , university_id"}))
public class FacultyController {
	
	@Autowired
	FacultyRepository facultyRepository;
	@Autowired
	UniversityRepository universityRepository;
	
	@GetMapping
	public List<Faculty> getFaculty(){
		
		List<Faculty> listFaculty = facultyRepository.findAll();
		
		return listFaculty;
	}
	
	@PostMapping
	public String addFaculty(@RequestBody FacultyDto facultyDto) {
		
		boolean existsByNameAndUniversityId = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getId());
		
		if(existsByNameAndUniversityId)
			return "This info is already existed";
		
		Faculty faculty = new Faculty();
		faculty.setName(facultyDto.getName());
		
		Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getId());
		
		if(optionalUniversity.isPresent()) {
			
			University university = optionalUniversity.get();
			
			faculty.setUniversity(university);
			facultyRepository.save(faculty);
			return "Succesfully added!";
		}
		
		return "Error occured";
	}
	
	@DeleteMapping("/{id}")
	public String deleteFaculty(@PathVariable Integer id) {
		
		facultyRepository.deleteById(id);
		return "Succesfully deleted";
	}
	
	@PutMapping("/id")
	public String updateFaculty(@PathVariable Integer id , @RequestBody FacultyDto facultyDto) {
		Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
		if(optionalFaculty.isPresent()) {
			Faculty updatedFaculty = optionalFaculty.get();
			updatedFaculty.setName(facultyDto.getName());
			Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getId());
			if(optionalUniversity.isPresent()) {
				University university = optionalUniversity.get();
				updatedFaculty.setUniversity(university);
			}
			facultyRepository.save(updatedFaculty);
			return "Faculty updated";
		}
		return "ERROR";
	}
	
}
