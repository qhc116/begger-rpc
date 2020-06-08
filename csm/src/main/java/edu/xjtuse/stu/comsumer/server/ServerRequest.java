package edu.xjtuse.stu.comsumer.server;

/**
 * @author 失了秩
 * @date 2020/6/7 16:10
 * @description
 */
public class ServerRequest {
    private long id;
    private Object msg;
    // to get method from methods map
    private String command;

    public long getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
