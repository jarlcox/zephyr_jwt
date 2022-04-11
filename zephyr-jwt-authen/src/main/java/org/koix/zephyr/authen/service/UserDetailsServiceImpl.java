package org.koix.zephyr.authen.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (!"admin".equals(username))
            throw new UsernameNotFoundException(username);
        
        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("member:add"));

        //return new User(username, "$2a$10$0e84aafBoFvBY2eTIuFTgOYYoRNKbpOIOscfTlMAPM4Mc/8SAYHSK", authorityList)
        return new User(username, this.passwordEncoder.encode("123456"), authorityList);
    }
}
