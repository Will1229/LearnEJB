package greeter;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(Greeter.class)
public class GreeterBean implements Greeter {

    @Override
    public String greet(String user) {
	return "Hello " + user + ", have a pleasant day!";
    }
}
