package org.springframework.boot.EventMulticaster;

import java.util.ArrayList;
import java.util.List;

public abstract class Object {

	private List<Observer> observerList = new ArrayList<Observer>();

	public void notifyMethod(){
		for (Observer observer : observerList) {
			observer.hello();
		}
	}

	public void add(Observer observer){
		observerList.add(observer);
	}

	public void remove(Observer observer){
		observerList.remove(observer);
	}
}
