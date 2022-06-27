package ru.job4j.ref;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserCacheTest {

    @Test
    public void findAll() {
        UserCache cache = new UserCache();
        User user1 = new User();
        user1.setName("user1");
        cache.add(user1);
        User user2 = new User();
        user2.setName("user2");
        cache.add(user2);
        assertThat(cache.findAll().get(0).getName(), is("user1"));
        assertThat(cache.findAll().get(1).getName(), is("user2"));
    }
}