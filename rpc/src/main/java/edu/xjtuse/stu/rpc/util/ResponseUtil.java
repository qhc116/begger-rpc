package edu.xjtuse.stu.rpc.util;

import edu.xjtuse.stu.rpc.constants.Constants;
import edu.xjtuse.stu.rpc.server.ServerResponse;

/**
 * @author 失了秩
 * @date 2020/6/8 13:37
 * @description
 */
public class ResponseUtil {
    public static ServerResponse createSuccess(Object response) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setResp(response);
        serverResponse.setCode(Constants.SUCCESS_CODE);
        return serverResponse;
    }

    public static ServerResponse createFail(int code, String failReason) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(code);
        serverResponse.setFailReason(failReason);
        return serverResponse;
    }
}
