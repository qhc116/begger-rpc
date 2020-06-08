package edu.xjtuse.stu.api.remote;


import edu.xjtuse.stu.api.bean.User;
import edu.xjtuse.stu.api.response.Response;

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
