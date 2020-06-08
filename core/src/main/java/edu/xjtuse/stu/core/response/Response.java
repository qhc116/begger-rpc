package edu.xjtuse.stu.core.response;

/**
 * @author 失了秩
 * @date 2020/6/7 10:25
 * @description
 */
public class Response {
    private long id;
    private Object resp;
    private int code;
    private String failReason;

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getResp() {
        return resp;
    }

    public void setResp(Object resp) {
        this.resp = resp;
    }
}
