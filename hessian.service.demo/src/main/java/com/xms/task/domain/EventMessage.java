/*
 * 文件名：EventData.java 版权：Copyright by www.nd.com 描述： 修改人：Administrator 修改时间：2016年7月5日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.xms.task.domain;


import java.io.Serializable;
import java.util.Arrays;


public class EventMessage implements Serializable
{
    private String msgID;

    private String receiptHandle;

    private byte[] body;

    public String getMsgID()
    {
        return msgID;
    }

    public void setMsgID(String msgID)
    {
        this.msgID = msgID;
    }

    public String getReceiptHandle()
    {
        return receiptHandle;
    }

    public void setReceiptHandle(String receiptHandle)
    {
        this.receiptHandle = receiptHandle;
    }

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }

    @Override
    public String toString()
    {
        return "EopEventMessage [msgID=" + msgID + ", receiptHandle=" + receiptHandle
               + ", eventData=" + Arrays.toString(body) + "]";
    }

}
