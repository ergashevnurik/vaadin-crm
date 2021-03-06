package com.vaadin.crm.backend.Users.services;

import com.vaadin.crm.backend.Users.entity.Role;
import com.vaadin.crm.backend.Users.entity.User;
import com.vaadin.crm.backend.Users.exception.UserNotFoundException;
import com.vaadin.crm.backend.Users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User with this Username: " + s + " does not exist");
        }

        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(String filterText) {
        if (filterText.isEmpty() || filterText == null) {
            return userRepository.findAll();
        } else {
            return userRepository.search(filterText);
        }
    }

    public User saveUser(User user) {
        user.setActive(true);
        //user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public ResponseEntity<User> getUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is not User with this ID: " + id));
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Map<String, Boolean>> deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("There is not User with this ID: " + id));
        userRepository.deleteById(user.getId());
        Map<String, Boolean> deletedSuccessfully = new HashMap<>();
        deletedSuccessfully.put("deleted successfully", Boolean.TRUE);
        return ResponseEntity.ok(deletedSuccessfully);
    }

    public ResponseEntity<User> updateUser(User userDetails, Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is not User with this ID: " + id));
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRoles(userDetails.getRoles());

        User updatedUserDetails = userRepository.save(user);
        return ResponseEntity.ok(updatedUserDetails);
    }

}
