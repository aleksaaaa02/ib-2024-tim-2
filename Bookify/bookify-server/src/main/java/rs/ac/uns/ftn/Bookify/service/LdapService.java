package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.NameClassPairCallbackHandler;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.Admin;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.service.interfaces.ILdapService;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import java.util.ArrayList;
import java.util.List;

@Service
public class LdapService implements ILdapService {


    @Autowired
    private LdapTemplate ldapTemplate;

    public List<User> getAllUsersFromLdap() {
        List<User> users = new ArrayList<>();
        String[] roles = {"ADMIN", "OWNER", "GUEST"};
        for (String role : roles) {
            List<String> roleMembers = getRoleMembers(role);
            for (String memberDN : roleMembers) {
                User user = getUser(memberDN, role);
                users.add(user);
            }
        }
        return users;
    }

    private List<String> getRoleMembers(String groupName) {
        String searchFilter = "(cn="+groupName+")";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        List<String> members = new ArrayList<>();
        ldapTemplate.search("ou=groups,ou=system", searchFilter, searchControls, (AttributesMapper<Void>) attrs -> {
            Attribute memberAttr = attrs.get("member");
            if (memberAttr != null) {
                NamingEnumeration<?> memberEnum = memberAttr.getAll();
                while (memberEnum.hasMore()) {
                    String memberDN = (String) memberEnum.next();
                    if (memberDN.equals("cn=empty-membership-placeholder")) continue;
                    members.add(memberDN);
                }
            }
            return null;
        });
        return members;
    }

    private User getUser(String userDn, String role) {
        String searchFilter = "(objectclass=person)";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        List<User> users = ldapTemplate.search(userDn, searchFilter, searchControls, (AttributesMapper<User>) attributes -> {
            String mail = attributes.get("mail").get().toString();
            String uid = attributes.get("uid").get().toString();
            String firstName = attributes.get("cn").get().toString();
            String lastName = attributes.get("sn").get().toString();

            switch (role) {
                case "GUEST" -> {
                    Guest g = new Guest();
                    g.setFirstName(firstName);
                    g.setLastName(lastName);
                    g.setEmail(mail);
                    g.setUid(uid);
                    return g;
                }
                case "OWNER" -> {
                    Owner o = new Owner();
                    o.setFirstName(firstName);
                    o.setLastName(lastName);
                    o.setEmail(mail);
                    o.setUid(uid);
                    return o;
                }
                case "ADMIN" -> {
                    Admin a = new Admin();
                    a.setFirstName(firstName);
                    a.setLastName(lastName);
                    a.setEmail(mail);
                    a.setUid(uid);
                    return a;
                }
            }
            return null;
        });
        return users.stream().findFirst().orElseThrow();
    }
}
