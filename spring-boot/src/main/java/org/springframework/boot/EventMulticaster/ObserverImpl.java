package org.springframework.boot.EventMulticaster;

public class ObserverImpl extends Observer {
	@Override
	public void hello() {
		System.out.println("老师好.....");
	}
}
