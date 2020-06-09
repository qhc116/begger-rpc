//import com.alibaba.fastjson.JSONObject;
//import edu.xjtuse.stu.rpc.client.ClientRequest;
//import edu.xjtuse.stu.rpc.client.ClientResponse;
//import edu.xjtuse.stu.rpc.client.TcpClient;
//import edu.xjtuse.stu.user.User;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
///**
// * @author 失了秩
// * @date 2020/6/7 16:19
// * @description
// */
//public class NettyTest {
//
//    @Test
//    public void testTcpServer() {
//        ClientRequest request = new ClientRequest();
//        request.setMsg("test long tcp connection");
//        ClientResponse send = TcpClient.send(request);
//        System.out.println(send.getResp() + "aaa");
//    }
//
//    @Test
//    public  void testUserController() {
//        ClientRequest request = new ClientRequest();
//        User user = new User();
//        user.setUsername("netty user");
//        user.setPassword("123456");
//        request.setMsg(user);
//        request.setCommand("edu.xjtuse.stu.user.UserController.save");
//        ClientResponse send = TcpClient.send(request);
//        System.out.println(send.getResp() + "aaa");
//    }
//
//    @Test
//    public void testSaveUsers() {
//        ClientRequest request = new ClientRequest();
//        User user = new User();
//        user.setUsername("netty user0");
//        user.setPassword("123456");
//
//        User user1 = new User();
//        user1.setUsername("netty user1");
//        user1.setPassword("1234567");
//
//        ArrayList<User> users = new ArrayList<>();
//        users.add(user);
//        users.add(user1);
//
//        request.setMsg(users);
//        request.setCommand("edu.xjtuse.stu.user.UserController.saveUsers");
//        ClientResponse send = TcpClient.send(request);
//        System.out.println(send.getResp() + "aaa");
//    }
//}
