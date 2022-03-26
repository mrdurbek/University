package uz.university.springappuniversity.controller;

import java.util.List;
import java.util.Optional;

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
import uz.university.springappuniversity.entity.Groups;
import uz.university.springappuniversity.model.GroupsDto;
import uz.university.springappuniversity.repository.FacultyRepository;
import uz.university.springappuniversity.repository.GroupsRepository;

@RestController
@RequestMapping(value = "/groups")
public class GroupController {
	
	@Autowired
	GroupsRepository groupRepository;
	
	@Autowired
	FacultyRepository facultyRepository;
	
	@GetMapping
	public List<Groups> getGroups(){
		return groupRepository.findAll();
	}
	
	@PostMapping
	public String addGroup(@RequestBody GroupsDto groupsDto) {
		
		boolean existsByNameAndFacultyId = groupRepository.existsByNameAndFacultyId(groupsDto.getName(),groupsDto.getId());
		if(existsByNameAndFacultyId) {
			return "This info is already existed";
		}
		Groups groups = new Groups();
		groups.setName(groupsDto.getName());
		
		Optional<Faculty> optionalFaculty = facultyRepository.findById(groupsDto.getId());
		if(optionalFaculty.isPresent()) {
			Faculty faculty = optionalFaculty.get();
			groups.setFaculty(faculty);
			groupRepository.save(groups);
			return "Succesfully added";
		}
		return "Error occured";
	}
	
	@DeleteMapping("/{id}")
	public String deleteGroup(@PathVariable Integer id) {
		groupRepository.deleteById(id);
		return "Group is deleted";
	}
	
	@PutMapping("/{id}")
	public String updateGroup(@PathVariable Integer id , @RequestBody GroupsDto groupsDto) {
		
		Optional<Groups> groupOptional = groupRepository.findById(id);
		if(groupOptional.isPresent()) {
			Groups group = groupOptional.get();
			group.setName(groupsDto.getName());
			Optional<Faculty> optional = facultyRepository.findById(groupsDto.getId());
			if(optional.isPresent()) {
				Faculty faculty = optional.get();
				group.setFaculty(faculty);
				
			}
			groupRepository.save(group);
			return "Updated";
		}
		return "Error";
	}
}
