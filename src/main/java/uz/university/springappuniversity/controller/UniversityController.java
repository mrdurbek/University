package uz.university.springappuniversity.controller;

import uz.university.springappuniversity.entity.Adress;
import uz.university.springappuniversity.entity.University;
import uz.university.springappuniversity.model.UniversityDto;
import uz.university.springappuniversity.repository.AdressRepository;
import uz.university.springappuniversity.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityController {
	
	@Autowired
	UniversityRepository universityRepository;
	
	@Autowired
	AdressRepository adressRepository;
	
	@RequestMapping(value = "/university" , method = RequestMethod.GET)
	public List<University> getUniversities() {
		
		List<University> universities = universityRepository.findAll();
		
		return universities;
	}
	
	@RequestMapping(value = "/university" , method = RequestMethod.POST)
	public String addStudent(@RequestBody UniversityDto universityDto) {
		
		Adress adress  = new Adress();
		adress.setCity(universityDto.getCity());
		adress.setDistrict(universityDto.getDistrict());
		adress.setStreet(universityDto.getStreet());
		Adress savedAdress = adressRepository.save(adress);
		
		University university = new University();
		university.setName(universityDto.getName());
		university.setAdress(savedAdress);
		universityRepository.save(university);
		
		return "Succesfully added!";
	}
	
	@RequestMapping(value = "/university/{id}" , method = RequestMethod.DELETE)
	public String deleteUniversity(@PathVariable Integer id) {
		
		universityRepository.deleteById(id);
		
		return "University deleted";
	}
	
	@RequestMapping(value = "/university/{id}" , method = RequestMethod.PUT)
	public String updateUniversity(@RequestBody UniversityDto universityDto , @PathVariable Integer id) {
		
		Optional<University> optionalUniversity = universityRepository.findById(id);
		if(optionalUniversity.isPresent()) {
			
			University updatedUniversity = optionalUniversity.get();
			Adress updatedAdress = updatedUniversity.getAdress();
			
			updatedAdress.setCity(universityDto.getCity());
			updatedAdress.setDistrict(universityDto.getDistrict());
			updatedAdress.setStreet(universityDto.getStreet());
			
			updatedUniversity.setAdress(updatedAdress);
			updatedUniversity.setName(universityDto.getName());
			universityRepository.save(updatedUniversity);
			
			return "University updated";
		}
		
		return "Error occured";
	}
}

