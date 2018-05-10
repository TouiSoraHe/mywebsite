package cn.zzy.mywebsite.Data;

public class ResponseJson {
    private String code;
    private String message;
    private Object data;

    public ResponseJson(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseJson{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static ResponseJson CreateError(String errorCode,String errorMessage)
    {
        return new ResponseJson(errorCode,errorMessage,null);
    }

    public static ResponseJson CreateSuccess(Object data)
    {
        return new ResponseJson("200","success",data);
    }
}
