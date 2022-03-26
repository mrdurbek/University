package uz.university.springappuniversity.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

import uz.university.springappuniversity.entity.Groups;
import uz.university.springappuniversity.entity.Student;
import uz.university.springappuniversity.entity.Subject;
import uz.university.springappuniversity.model.StudentDto;
import uz.university.springappuniversity.repository.GroupsRepository;
import uz.university.springappuniversity.repository.StudentRepository;
import uz.university.springappuniversity.repository.SubjectRepository;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	GroupsRepository groupsRepository;
	
//	@GetMapping
//	public Page<Student> getStudents(@RequestParam(value = "page") Integer page){
//		Pageable pageable = PageRequest.of(page, 10);
//		return studentRepository.findAll(pageable);
//	}
	
	@GetMapping
	public List<Student> getStudents(){
		return studentRepository.findAll();
	}
	
	@GetMapping("/university/{universityId}")
	public List<Student> getStudentsByUniversityId(@PathVariable Integer universityId){
		return studentRepository.findAllByGroup_Faculty_UniversityId(universityId);
	}
	
	@GetMapping("/faculty/{facultyId}")
	public List<Student> getStudentsByFacultyId(@PathVariable Integer facultyId){
		return studentRepository.findAllByGroup_FacultyId(facultyId);
	}
	@GetMapping("/group/{groupId}")
	public List<Student> getStudentsByGroupId(@PathVariable Integer groupId){
		return studentRepository.findAllByGroupId(groupId);
	}
	
	
	@PostMapping
	public String addStudent(@RequestBody StudentDto studentDto) {
		Student student = new Student();
		student.setFirstname(studentDto.getFirstname());
		student.setLastname(studentDto.getLastname());
		student.setPhoneNumber(studentDto.getPhoneNumber());
		
		List<Subject> subjectList = new ArrayList<Subject>();
		Iterator<Integer> it = studentDto.getSubjectId().iterator();
		while(it.hasNext()) {
			Optional<Subject> optionalSubject = subjectRepository.findById(it.next());
			if(optionalSubject.isPresent()) {
				subjectList.add(optionalSubject.get());
			}
			else return "Error";
		}
		student.setSubject(subjectList);
		Optional<Groups> optionalGroup = groupsRepository.findById(studentDto.getGroupId());
		if(optionalGroup.isPresent()) {
			Groups group = optionalGroup.get();
			student.setGroup(group);
			studentRepository.save(student);
			return "Succesfully added";
		}
		
		return "Error occured";
	}
	
	@DeleteMapping("/{id}")
	public String deleteStudent(@PathVariable Integer id) {
		studentRepository.deleteStudentById(id);
		studentRepository.deleteById(id);
		return "Succesfully deleted";
	}
	
	@PutMapping("/{id}")
	public String updateStudent(@PathVariable Integer id , @RequestBody StudentDto studentDto) {
		Optional<Student> optionalStudent = studentRepository.findById(id);
		if(optionalStudent.isPresent()) {
			Student updatedStudent = optionalStudent.get();
			updatedStudent.setFirstname(studentDto.getFirstname());
			updatedStudent.setLastname(studentDto.getLastname());
			updatedStudent.setPhoneNumber(studentDto.getPhoneNumber());
			List<Subject> subjectList = new ArrayList<Subject>();
			Iterator<Integer> it = studentDto.getSubjectId().iterator();
			while(it.hasNext()) {
				Optional<Subject> optionalSubject = subjectRepository.findById(it.next());
				if(optionalSubject.isPresent()) {
					subjectList.add(optionalSubject.get());
				}
				else return "Error occured";
			}
			updatedStudent.setSubject(subjectList);
			
			Optional<Groups> optionalGroup = groupsRepository.findById(studentDto.getGroupId());
			if(optionalGroup.isPresent()) {
				Groups group = optionalGroup.get();
				updatedStudent.setGroup(group);
				studentRepository.save(updatedStudent);
				return "Succesfully updated";
			}
		}
		return "Error occured!";
	}
}
