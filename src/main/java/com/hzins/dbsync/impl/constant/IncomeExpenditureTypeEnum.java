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
 *
 * 收入支出类型
 * </p>
 * @author	hz15051248 
 * @date	2015年8月27日 下午3:50:40
 * @version      
 */
public enum IncomeExpenditureTypeEnum {
    /** 异常回滚*/
    EXCEPTION_CALL_BACK((byte)0,"异常回滚"),
    /** 收入*/
    INCOME((byte)1,"收入"),
    /** 支出 */
    EXPENDITURE((byte)2,"支出"),
    /** 非收入非支出*/
    NONINCOME_EXPENDITURE((byte)3,"非收入非支出")
    ;
    
    private byte value;
    private String name;
    
    IncomeExpenditureTypeEnum(byte value,String name){
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
 