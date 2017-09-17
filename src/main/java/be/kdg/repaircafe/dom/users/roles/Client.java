package be.kdg.repaircafe.dom.users.roles;

import be.kdg.repaircafe.dom.repairs.Repair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Client can post repairs to the system.
 *
 * @author wouter
 */
@Entity
@DiscriminatorValue("ROLE_CLIENT")
public class Client extends Role {

    @OneToMany(targetEntity = Repair.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client")
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        return authorities;
    }

    @Override
    public RoleType getRoleType() {
        return RoleType.ROLE_CLIENT;
    }

}
