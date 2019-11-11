package com.springboot.task.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.springboot.task.model.User;
import com.springboot.task.dao.UserRepo;



@Controller
public class FileController {
	
	@Autowired
	UserRepo userRepo;
		
	@RequestMapping("/")
	public String uploadPage(Model model) {
		return "uploadview";
	}
	
	@RequestMapping("/upload")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
		try {
			for(MultipartFile file:files) {		
				try {
					InputStreamReader input = new InputStreamReader(file.getInputStream());
					BufferedReader reader = new BufferedReader(input);
					int currentLineInFile=0;
					int emptyFieldInLine=0;
					String line = reader.readLine();
					while ((line = reader.readLine())!=null) {
						if (line.isEmpty()) {
							System.out.println("Line number: "+ (currentLineInFile+1) +" was empty.");
						}else {
							System.out.println("Line number: "+ (currentLineInFile+1) +" was: " + line);
							String []data = line.split(";",-1);
							
							for (int i=0; i<3;i++) {
								if (data[i].trim().length()==0) {
									System.out.println("In line number: "+ (currentLineInFile+1) +" found empty field on position: " + i);
									emptyFieldInLine++;
								}
							}
							User newUser = new User();
							if (emptyFieldInLine==0) {
								newUser.setFirstName(data[0].trim());
								newUser.setLastName(data[1].trim());
								newUser.setBirthDate(data[2].trim());
								newUser.setAge(data[2].trim());
							} else {
								System.out.println("Something goes wrong, check input data: [First Name / Last name / Brith Date] in line: "+(currentLineInFile+1));
							}
							if (data[3].matches("[0-9]+") && data[3].length()==9){
								newUser.setPhoneNumber(Integer.parseInt(data[3].trim()));
							} else {
								System.out.println("Something goes wrong, check input data: [Phone Number] in line: "+(currentLineInFile+1));
							}
							if (emptyFieldInLine==0 && newUser.getPhoneNumber()!=0) {
								try {
									userRepo.save(newUser);
									System.out.println("Correctly added user: "+newUser.getFirstName() +" "+newUser.getLastName());
						        } catch (DataAccessException ex) {
						            System.out.println("Something goes wrong, check input data in line: "+(currentLineInFile+1));
						            return null;
						        }
							}
						}
						emptyFieldInLine=0;
						currentLineInFile++;
					}				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.addAttribute("users",userRepo.findByOrderByAgeAsc());
				model.addAttribute("msg",userRepo.findAll().toString());
				model.addAttribute("usersize",userRepo.count());
				model.addAttribute("oldest",userRepo.findByOrderByAgeAsc().get((int) (userRepo.count()-1)));
			}		
					
		} catch (Exception e) {
			System.out.println("Check your file");
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
