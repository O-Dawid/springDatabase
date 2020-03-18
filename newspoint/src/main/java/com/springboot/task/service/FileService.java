package com.springboot.task.service;

import java.util.List;

import com.springboot.task.model.User;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    List<User> load(MultipartFile file);
    boolean isPhoneNumberPresent(String value);
    void save(User user);
}