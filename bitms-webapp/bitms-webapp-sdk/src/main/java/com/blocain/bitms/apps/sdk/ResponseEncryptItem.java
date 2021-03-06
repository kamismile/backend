/**
 * blocain.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.blocain.bitms.apps.sdk;

import java.io.Serializable;

/**
 * 
 * 
 * @author playguy
 * @version $Id: EncryptReponseItem.java, v 0.1 2016-3-28 下午6:20:04 playguy Exp $
 */
public class ResponseEncryptItem implements Serializable
{
    /**  */
    private static final long serialVersionUID = 6680775791485372169L;
    
    /**
     * 响应返回
     */
    private String            respContent;
    
    /**
     * 处理完后的返回
     */
    private String            realContent;
    
    /**
     * @param respContent
     * @param realContent
     */
    public ResponseEncryptItem(String respContent, String realContent)
    {
        super();
        this.respContent = respContent;
        this.realContent = realContent;
    }
    
    /**
     * Getter method for property <tt>respContent</tt>.
     * 
     * @return property value of respContent
     */
    public String getRespContent()
    {
        return respContent;
    }
    
    /**
     * Getter method for property <tt>realContent</tt>.
     * 
     * @return property value of realContent
     */
    public String getRealContent()
    {
        return realContent;
    }
}
