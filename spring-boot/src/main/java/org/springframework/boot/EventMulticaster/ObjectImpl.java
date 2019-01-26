package org.springframework.boot.EventMulticaster;

public class ObjectImpl extends Object {

	public void techerComing(){
		System.out.println("老师来了");
		super.notifyMethod();
	}
}
