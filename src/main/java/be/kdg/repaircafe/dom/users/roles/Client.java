package be.kdg.repaircafe.dom.users.roles;

import be.kdg.repaircafe.dom.repairs.Repair;

import java.util.ArrayList;
import java.util.List;

/**
 * A Client can post repairs to the system.
 *
 * @author wouter
 */
public class Client extends Role {
    protected List<Repair> submittedRepairs;

    public Client() {
        this.submittedRepairs = new ArrayList<>();
    }

    /**
     * Add repair to this user's list
     *
     * @param repair
     */
    public synchronized void submitRepair(Repair repair) {
        this.submittedRepairs.add(repair);
    }

    /**
     * Remove a repair from the user's submitted repairs list
     *
     * @param repair
     */
    public synchronized void removeRepair(Repair repair) {
        this.submittedRepairs.remove(repair);
    }

    /**
     * Return list of submitted repairs.
     * <p/>
     * If User is a Client then this list contains his submitted repairs
     * If User us a Repaier then this list contains assigned repairs.
     *
     * @return List of repairs
     */
    public List<Repair> getRepairs() {
        return submittedRepairs;
    }

    @Override
    public RoleType getRoleType() {
        return RoleType.ROLE_CLIENT;
    }

}
