/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package com.hzins.dbsync.impl.constant; 

/**
 * <p>
 * 
 * 账户操作请求日志 操作状态
 * 账户类型枚举
 * </p>
 * @author	hz15051248 
 * @date	2015年8月27日 下午3:50:40
 * @version      
 */
public enum OperationStatusEnum {
    /** 操作失败 */
    FAILD((byte)1,"操作失败"),
     /**操作成功  */
    SUCCESS((byte)2,"操作成功 ");
    
    private byte value;
    private String name;
    
    OperationStatusEnum(byte value,String name){
	this.value=value;
	this.name=name;
    }
    
    public byte getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
    
    public void setValue(Byte value) {
        this.value = value;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
 