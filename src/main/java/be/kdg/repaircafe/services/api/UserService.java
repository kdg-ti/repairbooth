/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kdg.repaircafe.services.api;


import be.kdg.repaircafe.dom.users.Person;
import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.services.exceptions.UserServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


/**
 * @author wouter
 */
public interface UserService extends UserDetailsService
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-service-layer/
    // https://programmeren3-repaircafe.rhcloud.com/spring-concepten/backend-spring-security/

    User findUserById(int id) throws UserServiceException;

    List<User> findUsers();

    User findUserByUsername(String username) throws UserServiceException;

    List<User> findUsersByRole(Class c);

    User saveUser(User user) throws UserServiceException;

    User updateUser(Integer userId, Person person) throws UserServiceException;

    User addUser(User user) throws UserServiceException;

    void deleteUser(Integer userId) throws UserServiceException;

    void checkLogin(Integer userId, String password) throws UserServiceException;

    void updatePassword(Integer userId, String oldPassword, String newPassword) throws UserServiceException;
}
