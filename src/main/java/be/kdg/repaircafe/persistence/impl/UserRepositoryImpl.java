/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.persistence.impl;

import be.kdg.repaircafe.dom.users.User;
import be.kdg.repaircafe.persistence.api.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
 *
 * @author wouter *
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepositoryCustom {

    // Inject persistence context for low-level access
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findUsersByRole(Class c) {
        TypedQuery<User> q = em.createQuery("SELECT u from User u where TYPE(u.roles) = " + c.getSimpleName(), User.class);
        return q.getResultList();
    }
}
