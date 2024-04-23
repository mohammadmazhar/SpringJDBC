package springjdbc.ayman;

public class Client {

	
		private String username;
		private String password;
		private String name;
		private String bio;
		
		public Client(String new_name, String new_bio) {
			
			name = new_name;
			bio = new_bio;
		}
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getBio() {
			return bio;
		}
		public void setBio(String bio) {
			this.bio = bio;
		}
		
		public  void display() {
			System.out.println("Name: "+ name);
			System.out.println("Bio: "+ bio);
		}
	
}
	
	

