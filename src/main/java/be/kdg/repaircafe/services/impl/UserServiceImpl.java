/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.services.impl;

import be.kdg.repaircafe.dom.users.Person;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.persistence.api.UserRepository;
import be.kdg.repaircafe.services.api.UserService;
import be.kdg.repaircafe.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service bean that demarcates the transactional boundary and that acts as a 'controller' for backend
 * related operations.
 * <p>
 * A user can be Client, a Repairer or an Adminstrator
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/
 *
 * @author wouter
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves User by its unique userId
     *
     * @param userId Id of a specific user
     * @return User with this userId
     * @throws UserServiceException if User is not found
     */
    public User findUserById(int userId) throws UserServiceException {
        User u = userRepository.findOne(userId);

        if (u == null)
            throw new UserServiceException("User not found");

        return u;
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns user having username
     *
     * @param username
     * @return
     * @throws UserServiceException
     */
    @Override
    public User findUserByUsername(String username) throws UserServiceException {
        User user = userRepository.findUserByUsername(username);

        if (user == null)
            throw new UserServiceException("User not found");

        return user;
    }

    /**
     * Utility method to search users by Role type.
     * Role types are equal to classes, i.e. Client.class, Repairer.class, ...
     *
     * @param clazz
     * @return
     */
    @Override
    public List<User> findUsersByRole(Class clazz) {
        return userRepository.findUsersByRole(clazz);
    }

    public User saveUser(User user) throws UserServiceException {
        User u = userRepository.save(user);
        if (u == null)
            throw new UserServiceException("User not saved");
        return u;
    }

    /**
     * Update an existing user with new Person details
     *
     * @param userId Id of user
     * @param person
     */
    @Override
    public User updateUser(Integer userId, Person person) throws UserServiceException {
        User u = userRepository.findOne(userId);
        u.setPerson(person);
        return userRepository.save(u);
    }

    /**
     * Adds new user to the system but first encrypts his/her password.
     * This approach makes User decoupled from PasswordEncoder
     *
     * @param user
     * @throws UserServiceException
     */
    @Override
    public User addUser(User user) throws UserServiceException {
        // the first call to getEncryptedPassword returns a non-encrypted password
        user.setEncryptedPassword(passwordEncoder.encode(user.getEncryptedPassword()));
        return this.saveUser(user);
    }

    /**
     * Removes with userId
     *
     * @param userId Id of user object to delete
     */
    @Override
    public void deleteUser(Integer userId) throws UserServiceException {
        User user = userRepository.findOne(userId);
        if (user == null)
            throw new UserServiceException("User not found");

        userRepository.delete(user);
    }

    /**
     * Hier wordt gecontroleerd of de user bestaat en of zijn currentPassword correct
     * is. Indien één van deze controles faalt, wordt een UserServiceException gegooid.
     *
     * @param userId          Id of user
     * @param currentPassword current currentPassword
     * @throws UserServiceException when wrong credentials are given
     */
    @Override
    public void checkLogin(Integer userId, String currentPassword) throws UserServiceException {
        User u = userRepository.findOne(userId);

        if (u == null || !passwordEncoder.matches(currentPassword, u.getEncryptedPassword())) {
            throw new UserServiceException(("Gebruikersnaam of password foutief voor gebruiker " + userId));
        }
    }

    /**
     * Update currentPassword for an existing user
     *
     * @param userId      Id of user
     * @param oldPassword old password
     * @param newPassword new password
     * @throws UserServiceException
     */
    @Override
    public void updatePassword(Integer userId, String oldPassword, String newPassword) throws UserServiceException {
        User u = userRepository.findOne(userId);
        checkLogin(userId, oldPassword);
        u.setEncryptedPassword(passwordEncoder.encode(newPassword));
        userRepository.save(u);
    }

    /**
     * Implementation of Spring Security UserDetailsService method
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findUserByUsername(username);

        if (u == null) throw new UsernameNotFoundException("No such user: " + username);

        return u;
    }
}