package com.threepounds.caseproject.service;


import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.data.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> getByUserId(String userId){
        return userRepository.findById(userId);
    }
    public List<User> list(){
        return userRepository.findAll();
    }
    public Page<User> listByPage(int pageNumber, int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageable);
    }

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
