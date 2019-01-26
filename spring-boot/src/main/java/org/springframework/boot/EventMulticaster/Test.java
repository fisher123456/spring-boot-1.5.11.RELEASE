package org.springframework.boot.EventMulticaster;

public class Test {

	public static void main(String[] args) {
		Observer observer = new ObserverImpl();
		Observer observer2 = new ObserverImpl();
		Object object = new ObjectImpl();
		object.add(observer);
		object.add(observer2);
		((ObjectImpl) object).techerComing();

	}
}
