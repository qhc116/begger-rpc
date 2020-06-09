package edu.xjtuse.stu.rpc.util;

import edu.xjtuse.stu.rpc.constants.Constants;
import edu.xjtuse.stu.rpc.server.Response;

/**
 * @author 失了秩
 * @date 2020/6/8 13:37
 * @description
 */
public class ResponseUtil {
    public static Response createSuccess(Object response) {
        Response serverResponse = new Response();
        serverResponse.setResp(response);
        serverResponse.setCode(Constants.SUCCESS_CODE);
        return serverResponse;
    }

    public static Response createFail(int code, String failReason) {
        Response response = new Response();
        response.setCode(code);
        response.setFailReason(failReason);
        return response;
    }
}
