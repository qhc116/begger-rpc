package edu.xjtuse.stu.comsumer.client;


import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 失了秩
 * @date 2020/6/7 9:08
 * @description
 */
public class ClientRequest {
    private Long id;
    private static final AtomicLong atomicLong = new AtomicLong(0);
    private Object msg;
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public static AtomicLong getAtomicLong() {
        return atomicLong;
    }

    public ClientRequest() {
        id = atomicLong.incrementAndGet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
