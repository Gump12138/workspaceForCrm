package com.bjpowernode.crm.utils;

public class ServiceFactory {
	
	public static Object getService(Object service){
		
		return new com.bjpowernode.crm.utils.TransactionInvocationHandler(service).getProxy();
		
	}
	
}
