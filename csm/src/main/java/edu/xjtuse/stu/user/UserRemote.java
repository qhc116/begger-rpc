package edu.xjtuse.stu.user;

import edu.xjtuse.stu.comsumer.client.Response;
import edu.xjtuse.stu.rpc.server.ServerResponse;

import java.util.List;

/**
 * @author 失了秩
 * @date 2020/6/8 14:51
 * @description
 */
public interface UserRemote {
    Response saveUser(User user);
    Response saveUsers(List<User> users);
}
