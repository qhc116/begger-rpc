package edu.xjtuse.stu.user;

import edu.xjtuse.stu.rpc.annotation.Remote;
import edu.xjtuse.stu.rpc.server.ServerResponse;
import edu.xjtuse.stu.rpc.util.ResponseUtil;

import java.util.List;

/**
 * @author 失了秩
 * @date 2020/6/8 14:51
 * @description
 */
@Remote
public class UserRemoteImpl implements UserRemote {
    public ServerResponse saveUser(User user){
        System.out.println("save user: username = " + user.getUsername() + "password = " + user.getPassword());
        return ResponseUtil.createSuccess(user);
    }

    public ServerResponse saveUsers(List<User> users){
        System.out.println(users);
        return ResponseUtil.createSuccess(users);
    }
}
