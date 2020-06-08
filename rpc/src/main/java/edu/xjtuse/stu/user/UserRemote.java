package edu.xjtuse.stu.user;

import edu.xjtuse.stu.rpc.annotation.Remote;
import edu.xjtuse.stu.rpc.server.ServerResponse;

import java.util.List;

/**
 * @author 失了秩
 * @date 2020/6/8 14:51
 * @description
 */
public interface UserRemote {
    ServerResponse saveUser(User user);
    ServerResponse saveUsers(List<User> users);
}
