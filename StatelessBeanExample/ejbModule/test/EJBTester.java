package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import helloworld.HelloWorldBean;
import helloworld.HelloWorldBeanRemote;
import statelessBeanExample.LibrarySessionBean;
import statelessBeanExample.LibrarySessionBeanRemote;

public class EJBTester {

    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;
    {
	props = new Properties();
	File file = new File(
		"C:\\Users\\egaozhi\\Documents\\GitHub\\LearnEJB\\StatelessBeanExample\\ejbModule\\test\\jndi2.properties");
	try {
	    props.load(new FileInputStream(file));
	} catch (IOException ex) {
	    ex.printStackTrace();
	}

	Hashtable jndiProperties = new Hashtable();
	jndiProperties.put("jboss.naming.client.ejb.context", true);
	jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

	try {
	    ctx = new InitialContext(props);
	    // ctx = new InitialContext(jndiProperties);
	} catch (NamingException ex) {
	    ex.printStackTrace();
	}
	brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) {

	EJBTester ejbTester = new EJBTester();

//	ejbTester.testHelloWorld();
	ejbTester.testStatelessEjb();
    }

    private void showGUI() {
	System.out.println("**********************");
	System.out.println("Welcome to Book Store");
	System.out.println("**********************");
	System.out.print("Options \n1. Add Book\n2. Exit \nEnter Choice: ");
    }

    private void testHelloWorld() {
	// HelloWorldBean bean = null;
	final String appName = "";
	final String moduleName = "StatelessBeanExample";
	final String distinctName = "";
	final String beanName = HelloWorldBean.class.getSimpleName();
	final String viewClassName = HelloWorldBeanRemote.class.getName();
	String name = getBeanName(appName, moduleName, distinctName, beanName, viewClassName);
	HelloWorldBeanRemote bean = null;
	try {
	    bean = (HelloWorldBean) ctx.lookup(name);
	} catch (NamingException e) {
	    e.printStackTrace();
	}

	System.out.println(bean.sayHello());
    }

    /*
     * - appName The app name is the application name of the deployed EJBs. This
     * is typically the ear name without the .ear suffix. However, the
     * application name could be overridden in the application.xml of the EJB
     * deployment on the server. Since we haven't deployed the application as a
     * .ear, the app name for us will be an empty string
     * 
     * - moduleName This is the module name of the deployed EJBs on the server.
     * This is typically the jar name of the EJB deployment, without the .jar
     * suffix, but can be overridden via the ejb-jar.xml In this example, we
     * have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module
     * name is jboss-as-ejb-remote-app
     * 
     * - distinctName AS7 allows each deployment to have an (optional) distinct
     * name. We haven't specified a distinct name for our EJB deployment, so
     * this is an empty string
     * 
     * - beanName The EJB name which by default is the simple class name of the
     * bean implementation class
     * 
     * - viewClassName the remote view fully qualified class name
     * 
     */
    private String getBeanName(String appName, String moduleName, String distinctName, String beanName,
	    String viewClassName) {
	String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
	System.out.println(name);
	return name;
    }

    private void testStatelessEjb() {

	try {
	    int choice = 1;

	    String libraryBeanName = getBeanName("", "StatelessBeanExample", "",
		    LibrarySessionBean.class.getSimpleName(), LibrarySessionBeanRemote.class.getName());
	    LibrarySessionBeanRemote libraryBean = (LibrarySessionBeanRemote) ctx.lookup(libraryBeanName);

	    while (choice != 2) {
		String bookName;
		showGUI();
		String strChoice = brConsoleReader.readLine();
		choice = Integer.parseInt(strChoice);
		if (choice == 1) {
		    System.out.print("Enter book name: ");
		    bookName = brConsoleReader.readLine();
		    String book = new String(bookName);
		    libraryBean.addBook(book);
		} else if (choice == 2) {
		    break;
		}
	    }

	    List<String> booksList = libraryBean.getBooks();

	    System.out.println("Book(s) entered so far: " + booksList.size());
	    int i = 0;
	    for (String book : booksList) {
		System.out.println((i + 1) + ". " + book);
		i++;
	    }
	    // LibrarySessionBeanRemote libraryBean1 =
	    // (LibrarySessionBeanRemote)ctx.lookup("LibrarySessionBean/remote");
	    LibrarySessionBeanRemote libraryBean1 = (LibrarySessionBeanRemote) ctx.lookup(libraryBeanName);
	    ;
	    List<String> booksList1 = libraryBean1.getBooks();
	    System.out.println("***Using second lookup to get library stateless object***");
	    System.out.println("Book(s) entered so far: " + booksList1.size());
	    for (i = 0; i < booksList1.size(); ++i) {
		System.out.println((i + 1) + ". " + booksList1.get(i));
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	} finally {
	    try {
		if (brConsoleReader != null) {
		    brConsoleReader.close();
		}
	    } catch (IOException ex) {
		System.out.println(ex.getMessage());
	    }
	}
    }
}