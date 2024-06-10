package rs.ac.uns.ftn.Bookify.repository;

import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

public class LdapUserRepository {

//    @Value("${spring.}")


    @Bean
    public LdapContextSource contextSource(){
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl("");
        contextSource.setBase("");
        contextSource.setUserDn("");

        return null;
    }

    @Bean
    public LdapTemplate ldapTemplate(){
        return null;
    }



}
