package springjdbc.ayman;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
public class App 
{
	
    static String configString = "springjdbc/ayman/config.xml";
    
    static ApplicationContext context = new ClassPathXmlApplicationContext(configString);
    
    static JdbcTemplate template  = (JdbcTemplate)context.getBean("template");
    
    static Scanner scanner = new Scanner(System.in);
	
    static String currentuser;

	//Main functions, Calls the Main Menu
    public static void main( String[] args )
    {
    	mainmenu();
    }

    public static void usermenu() {
    	boolean back = false;
		while(!back) {
			
			System.out.println("\n\n MENU \n\n");
	     	   System.out.println("1. Show Profile");
	            System.out.println("2. Edit Bio");
	            System.out.println("3. Delete Account");
	            System.out.println("4. Back");

	            System.out.print("Enter your option: ");
	            String op = scanner.nextLine();
	            int option = Integer.parseInt(op);
	            
	            switch (option) {
				case 1:
					Client val = getUser();
					val.display();
					break;
				case 2:
					System.out.println("Enter New Bio: ");
					String new_bio =  scanner.nextLine();
					updateUser(new_bio);
					break;
				case 3:
					deleteUser();
					back = true;
					break;
				case 4:
					back = true;
					break;

				default:
					System.out.println("Option Not Found");
					break;
				}
		}
	}

    public static void mainmenu() {
    	while (true) {
    		System.out.println("\n\n MENU \n\n");
     	   System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quit");

            System.out.print("Enter your option: ");
            String op = scanner.nextLine();
            int option = Integer.parseInt(op);
            
            switch (option) {
 		case 1:
 			System.out.print("Enter username: ");
 	        String username = scanner.nextLine();
 	        System.out.print("Enter Password: ");
 	        String password = scanner.nextLine();

 	        String regainPassword = getPassword(username);
 	        if(regainPassword==null) {
 	        	System.out.println("User or password incorrect!!!");
 	        	break;
 	        }
 	        if(password.equals(regainPassword))
 	        {
 	        	currentuser = username;
 	        	usermenu();
 	        	break;
 	        }
 	        else 
				System.out.println("User or password incorrect!!!");
 			break;
 		case 2:
 			System.out.print("Enter username: ");
 	        String new_user = scanner.nextLine();
 	        System.out.print("Enter Password: ");
 	        String new_pass = scanner.nextLine();
 	        System.out.print("Enter Name: ");
	        String new_name = scanner.nextLine();
	        System.out.print("Enter Bio: ");
 	        String new_bio = scanner.nextLine();
 	        
 	        setUser(new_user, new_pass, new_name, new_bio);
 			break;
 		case 3:
 			scanner.close();
 			((ClassPathXmlApplicationContext)context).close();
 			System.exit(0);
 			break;
 		
 		default:
 			System.out.println("Option Not Found");
 			break;
 		}
 	}
     	
    }

    public static void setUser(String user,String pass,String name,String bio) {
    	String query = "insert into Information values(?,?,?,?)";
    	int val =  template.update(query,user,pass,name,bio);
    	if(val==1) {
    		System.out.println("User Added Successfully");
    	}
	}

    public static String getPassword(String username) {
		String query = "select password from Information where username=?";
		String password = (String) template.queryForObject(query, new Object[] { username }, String.class);
		
		return password;
    	
	}

    public static void deleteUser() {
    	String query = "delete from Information where username=?";
    	int val =  template.update(query,currentuser);
    	if(val==1) {
    		System.out.println("User Deleted Successfully");
    	}
	}

    public static void updateUser(String bio) {
    	String query = "update Information set bio=? where username=?";
    	int val =  template.update(query,bio,currentuser);
    	if(val==1) {
    		System.out.println("Bio Updated Successfully");
    	}
	}

    public static Client getUser() {
    	String query = "select * from Information where username=?";
    	return template.queryForObject(query, new Object[]{currentuser}, (rs, rowNum) ->
        new Client(
                
                rs.getString("name"),
                rs.getString("bio")
        ));
	}
}
