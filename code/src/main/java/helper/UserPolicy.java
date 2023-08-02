package helper;

public class UserPolicy {
	
	public String CheckPolicy(String Role,String Check){
		if(Role==null) {
			return "You Should SignIn First";
		}
		if(Role!=Check) {
			return "You dont have permission to access this featur";
		}
		return "OK";
	}
}
