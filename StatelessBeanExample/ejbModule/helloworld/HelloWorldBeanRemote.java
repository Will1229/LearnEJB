package helloworld;

import javax.ejb.Remote;

@Remote
public interface HelloWorldBeanRemote {
	public String sayHello();
}
