package com.task.lms.repository;

import com.task.lms.entity.ERole;
import com.task.lms.entity.Role;
import com.task.lms.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void should_find_users_if_repository_is_not_empty() {
        Iterable<User> users = userRepository.findAll();

        assertThat(users).isNotEmpty();
    }

    @Test
    public void should_store_a_user() {
        int userPostFix = 1;
        User newUser = getUser(userPostFix);
        User user = userRepository.save(newUser);

        assertThat(user).hasFieldOrPropertyWithValue("username", "test.user"+userPostFix);
        assertThat(user).hasFieldOrPropertyWithValue("email", "test.user"+userPostFix+"@email.com");
        assertThat(user).hasFieldOrPropertyWithValue("address", "Dhaka"+userPostFix);
    }

    @Test
    public void should_find_user_by_id() {
        User newUser = getUser(1);
        entityManager.persist(newUser);
        User foundUser = userRepository.findById(newUser.getId()).get();

        assertThat(foundUser).isEqualTo(newUser);
    }

    @Test
    public void should_delete_user_by_id() {
        Long usersCountBeforeDelete =  userRepository.findAll().stream().count();
        User newUser = getUser(1);
        entityManager.persist(newUser);
        userRepository.deleteById(newUser.getId());
        Long usersCountAfterDelete =  userRepository.findAll().stream().count();

        assertThat(usersCountBeforeDelete).isEqualTo(usersCountAfterDelete);
    }

    private User getUser(int userPostFix){
        User newUser = User.builder()
                .fullName("Test User "+userPostFix)
                .username("test.user"+userPostFix)
                .password("123456"+userPostFix)
                .email("test.user"+userPostFix+"@email.com")
                .address("Dhaka"+userPostFix)
                .createdAt(Instant.now())
                .roles(getUserRole())
                .build();
        entityManager.persist(newUser);
        return  newUser;
    }
    private Set<Role> getUserRole(){
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(null);
        roles.add(userRole);
        return roles;
    }



}