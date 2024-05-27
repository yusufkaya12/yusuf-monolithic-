package com.threepounds.caseproject.security;

import com.threepounds.caseproject.data.entity.Permission;
import com.threepounds.caseproject.data.entity.Role;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService{
  private final UserRepository userRepository;
  @Override
  public UserDetailsService userDetailsService() {
      return new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username) {
          User user =  userRepository.findByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
          List<GrantedAuthority> authorities =
              buildUserAuthority(new HashSet<>(user.getRoles()));
          return buildUserForAuthentication(user, authorities);
        }
      };
    }

  private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        user.isActive(), true, true, true, authorities) {
    };
  }

  private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

    Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

    // add user's authorities
    for (Role userRole : userRoles) {
      setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
      userRole.getPermissions().stream()
          .map(p -> new SimpleGrantedAuthority(p.getName()))
          .forEach(setAuths::add);
    }

    return new ArrayList<GrantedAuthority>(setAuths);

  }
}
