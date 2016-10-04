package helloworld;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class HelloWorldBean
 */
@Stateless
public class HelloWorldBean implements HelloWorldBeanRemote {

	/**
	 * Default constructor.
	 */
	public HelloWorldBean() {
	}

	@Override
	public String sayHello() {
		return "Hello World !!!";
	}

}
