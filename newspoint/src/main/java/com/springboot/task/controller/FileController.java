package com.springboot.task.controller;

import java.util.List;

import com.springboot.task.dao.UserRepo;
import com.springboot.task.model.User;
import com.springboot.task.service.FileService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	
	UserRepo userRepo;
	FileService fileService;

	public FileController(UserRepo userRepo, FileService fileService) {
		this.userRepo = userRepo;
		this.fileService = fileService;
	}

	@RequestMapping("/")
	public String uploadPage(Model model) {
		return "uploadview";
	}

	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public String upload(Model model, @RequestParam("files") MultipartFile[] files){
		
		try {
			for(MultipartFile file:files) {		
				List<User> users = fileService.load(file);
				for (User user : users) {
						// if (!fileService.isPhoneNumberPresent(user.getPhoneNumber()))
						fileService.save(user);
				}

				model.addAttribute("users",userRepo.findByOrderByAgeAsc());
				model.addAttribute("msg",userRepo.findAll().toString());
				model.addAttribute("usersize",userRepo.count());
				model.addAttribute("oldest",userRepo.findByOrderByAgeAsc().get((int) (userRepo.count()-1)));
			}		
					
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "uploadview";
		}		
		return "uploadedstatusview";
	}

	@RequestMapping("/getuser")
	public String getUser(Model model, @RequestParam String lastname) {		
		List<User> searchedUsers = userRepo.findByLastNameIgnoreCase(lastname);
		if (searchedUsers.isEmpty()) {
			System.out.println("Not found user with last name: "+lastname);
			return "uploadview";
		} else {
			model.addAttribute("searchedUsers",searchedUsers);
		}
		return "searchview";	
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod. POST)
	public String editCustomer(@RequestParam("checkboxName")int[] checkboxValue, Model model) 
	{
		
		try {
			for(int c:checkboxValue) {
				System.out.println("User: " + userRepo.findById(c).get().getFirstName() + " " + userRepo.findById(c).get().getLastName() + " was deleted");
				userRepo.deleteById(c);
			}
			model.addAttribute("users",userRepo.findByOrderByAgeAsc());
			model.addAttribute("msg",userRepo.findAll().toString());
			model.addAttribute("usersize",userRepo.count());
			model.addAttribute("oldest",userRepo.findByOrderByAgeAsc().get((int) (userRepo.count()-1)));
		} catch (Exception e) {
			System.out.println("Database is empty");
			return "uploadview";
		}
        return "uploadedstatusview";        	
	}
	
}
