package uz.university.springappuniversity.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uz.university.springappuniversity.entity.Subject;
import uz.university.springappuniversity.repository.SubjectRepository;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@GetMapping()
	public Page<Subject> getSubjects(@RequestParam(value = "page" , required = false) int page){
		
		Pageable pageable = PageRequest.of(page, 10);		
		Page<Subject> pageSubject = subjectRepository.findAll(pageable);
		return pageSubject;
	}
	
	@PostMapping
	public String addSubject(@RequestBody Subject subject) {
		
		boolean existsByName = subjectRepository.existsByName(subject.getName());
		if(existsByName)
			return "This subject already existed";
		
		subjectRepository.save(subject);
		
		return "Subject added";
		
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteSubject(@PathVariable Integer id) {
		
		subjectRepository.deleteById(id);
		
		return "Subject deleted";
	}
	
	@PutMapping("/update/{id}")
	public String updateSubject(@PathVariable Integer id , @RequestBody Subject subject) {
		Optional<Subject> optionalSubject = subjectRepository.findById(id);
		if(optionalSubject.isPresent()) {
			Subject updatedSubject = optionalSubject.get();
			updatedSubject.setName(subject.getName());
			subjectRepository.save(updatedSubject);
			return "Subject is updated";
		}
		return "Error occured";
	}
	
}
