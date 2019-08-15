package cn.vonce.common.bean;

import cn.vonce.common.enumerate.ResultCode;

/**
 * 业务方法 执行返回结果(Service Result)
 *
 * @author Jovi
 * @version 1.0
 * @email 766255988@qq.com
 * @date 2018年1月18日下午5:39:57
 */
public class SResult<T> {

    /**
     * 结果码
     */
    private ResultCode resultCode = ResultCode.OTHERS;

    /**
     * 成功与否的消息
     */
    private String msg;

    /**
     * 返回的数据对象
     */
    private T data;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SResult [resultCode=" + resultCode + ", mess=" + msg + ", data=" + data + "]";
    }

}
