package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.User;

import java.util.List;

@Service
public interface ILdapService {
    public List<User> getAllUsersFromLdap();

}
