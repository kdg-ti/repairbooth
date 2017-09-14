/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.persistence.impl;

import be.kdg.repaircafe.dom.repairs.Repair;
import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.persistence.api.RepairRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Repository bean that implements some custom functionality using a named query
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-backend/backend-persistence-layer/
 *
 * @author wouter
 */
@Repository
public class RepairRepositoryImpl implements RepairRepositoryCustom
{
    // Inject persistence context for low-level access
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Repair> getRepairsByClient(Client client)
    {
        final TypedQuery<Repair> q = em.createNamedQuery("Repair.findRepairByClient", Repair.class);
        q.setParameter("client", client);
        return q.getResultList();
    }
}
