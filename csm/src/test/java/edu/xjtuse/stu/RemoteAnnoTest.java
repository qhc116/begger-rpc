package edu.xjtuse.stu;

import edu.xjtuse.stu.comsumer.annotation.RemoteMethodCall;
import edu.xjtuse.stu.comsumer.client.Response;
import edu.xjtuse.stu.user.User;
import edu.xjtuse.stu.user.UserRemote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * @author 失了秩
 * @date 2020/6/8 15:12
 * @description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RemoteAnnoTest.class)
@ComponentScan("edu.xjtuse.stu.comsumer")
public class RemoteAnnoTest {
    @RemoteMethodCall
    private UserRemote userRemote;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("netty user");
        user.setPassword("123456");
        Response response = userRemote.saveUser(user);
        System.out.println(response);
    }

    @Test
    public void testSaveUsers() {
        User user = new User();
        user.setUsername("netty user0");
        user.setPassword("123456");

        User user1 = new User();
        user1.setUsername("netty user1");
        user1.setPassword("1234567");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        userRemote.saveUsers(users);
    }
}
