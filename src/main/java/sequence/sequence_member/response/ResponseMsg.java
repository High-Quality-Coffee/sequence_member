package sequence.sequence_member.response;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseMsg {
    private int status;
    private String msg;
    private Map<String, String> resultMsg;

    public ResponseMsg(int status, String msg,  Map<String, String> resultMsg){
        this.status = status;
        this.msg=msg;
        this.resultMsg = resultMsg;
    }

}
