package edu.xjtuse.stu.remote;



import edu.xjtuse.stu.api.bean.User;
import edu.xjtuse.stu.api.remote.UserRemote;
import edu.xjtuse.stu.comsumer.comsumer.annotation.RemoteMethodCall;
import org.springframework.stereotype.Service;

/**
 * @author 失了秩
 * @date 2020/6/9 13:23
 * @description
 */

@Service
public class BasicService {
    @RemoteMethodCall
    private UserRemote userRemote;

    public void testSaveUser() {
        User user = new User();
        user.setUsername("netty user");
        user.setPassword("123456");
        Object response = userRemote.saveUser(user);
        System.out.println(response.toString());
    }


}
