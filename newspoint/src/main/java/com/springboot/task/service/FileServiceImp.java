package com.springboot.task.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.springboot.task.dao.UserRepo;
import com.springboot.task.model.User;
import com.springboot.task.model.UserBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImp implements FileService {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    UserRepo userRepo;
    @Autowired
    private Validator validator;

    public boolean isPhoneNumberPresent(String phoneNumber) {
		if (userRepo.findByPhoneNumber(phoneNumber).isPresent()) {
			return true;
		}
		return false;
    }
    
    public void save(User user) {
        userRepo.save(user);
    }
    
    public List<User> load(MultipartFile file) {
        BufferedReader reader = getFile(file); 
        return reader.lines()
              .skip(1)
              .filter(line -> !line.isEmpty())
              .filter(line -> {
                List<String> columns = getTokensWithCollection(line);
                return !(columns.isEmpty());
              })
              .map(mapToItem)
              .filter(user -> isValidUser(user))
              .collect(Collectors.toList());

            //   int currentLineInFile=0;
            //   List <User> validUsers = new ArrayList<>();
            // try {
            //     String line = reader.readLine();
            //     while ((line = reader.readLine()) != null) {
            //         List<String> columns = getTokensWithCollection(line);
            //         if (line.isEmpty() || (columns.isEmpty()))
            //             log.error("Line number: " + (currentLineInFile + 1) + " was empty.");
            //         else {
            //             UserBuilder userBuilder = new UserBuilder()
            //                                                     .firstName(columns.get(0))
            //                                                     .lastName(columns.get(1))
            //                                                     .birthDate(columns.get(2));                                        
            //             if (columns.size() == 3)
            //                 userBuilder.phoneNumber(null);
            //             else
            //                 userBuilder.phoneNumber(columns.get(3));
            //             User user = userBuilder.build();
            //             if (isValidUser(user))
            //                 validUsers.add(user);
            //         }
            //         currentLineInFile++;
            //     }
            // } catch (IOException e) {
            //     log.error(e.getMessage(),e);
            // }
            // return validUsers;
    }

    private Function<String, User> mapToItem = (line) -> {
        List<String> columns = getTokensWithCollection(line);
                    UserBuilder userBuilder = new UserBuilder().firstName(columns.get(0))
                                                               .lastName(columns.get(1))
                                                               .birthDate(columns.get(2));
                                                                
                        if (columns.size() != 3)
                        userBuilder.phoneNumber(columns.get(3));
                        
                        User user = userBuilder.build();
                        return user;
      };
    
    private boolean isValidUser(User user) {
        // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> validationErrors = validator.validate(user);
        if (!validationErrors.isEmpty()) {
            for (ConstraintViolation<User> validationError : validationErrors) {
                log.error(validationError.getPropertyPath().toString() + ", "
                        + validationError.getMessage() + " for: " + user);
            }
            return false;
        } else {
            log.info("User OK!: " + user);
            return true;
        }
    }

    private BufferedReader getFile(MultipartFile file) {
        InputStreamReader input;
        try {
            input = new InputStreamReader(file.getInputStream());
            return new BufferedReader(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getTokensWithCollection(String str) {
        String splitBy = ";";
        return Collections.list(new StringTokenizer(str, splitBy)).stream()
          .map(token -> (String) token)
          .map(s -> s.trim())
          .collect(Collectors.toList());
    }


   
}