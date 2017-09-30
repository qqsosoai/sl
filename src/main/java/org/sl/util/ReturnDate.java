package org.sl.util;

/**
 * Created by hasee on 2017/9/30.
 */
public class ReturnDate  {
    private int status=0;//信息状态0失败，1成功
    private Object data;//成功返回的数据
    private String messages;//信息提示语句

    public ReturnDate() {
    }

    /**
     * 自定义返回数据
     * @param status 状态
     * @param data 数据
     * @param messages 提示信息
     */
    public ReturnDate(int status, Object data, String messages) {
        this.status = status;
        this.data = data;
        this.messages = messages;
    }

    /**
     * 返回处理失败信息
     * @param messages 提示信息
     */
    public ReturnDate(String messages) {
        this.messages = messages;
    }

    /**
     * 返回成功构造
     * @param data
     */
    public ReturnDate(Object data) {
        this.status = 1;
        this.data = data;
    }
}
