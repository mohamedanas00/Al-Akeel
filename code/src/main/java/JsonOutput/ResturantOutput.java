package JsonOutput;

import java.util.List;

public class ResturantOutput {
     private  long id;
     private String name;
     private List<MealOutput> menu;
     private long ownerId;
     
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
	public List<MealOutput> getMenu() {
		return menu;
	}
	public void setMenu(List<MealOutput> menu) {
		this.menu = menu;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

     
     
}
