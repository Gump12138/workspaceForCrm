package com.bjpowernode.crm.utils;

public class ServiceFactory {
	
	public static Object getService(Class aclass){
		return new com.bjpowernode.crm.utils.TransactionInvocationHandler(aclass).getProxy();
	}
	
}
