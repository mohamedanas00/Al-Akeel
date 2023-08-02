package JsonOutput;

import java.util.List;

public class OrderOutput {
	private long orderId;
	private  String time;
	private  float totalPrice;
	private String Order_State;
	private String message;
	private ResturantOutput resturantData;
    private RunnerOutput runnerData; 
	private List<MealOutput> mealsData;
	
	public OrderOutput() {}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public ResturantOutput getResturantData() {
		return resturantData;
	}

	public void setResturantData(ResturantOutput resturantData) {
		this.resturantData = resturantData;
	}

	public List<MealOutput> getMealsData() {
		return mealsData;
	}

	public void setMealsData(List<MealOutput> mealsData) {
		this.mealsData = mealsData;
	}

	public String getOrder_State() {
		return Order_State;
	}

	public void setOrder_State(String order_State) {
		Order_State = order_State;
	}

	public RunnerOutput getRunnerData() {
		return runnerData;
	}

	public void setRunnerData(RunnerOutput runnerData) {
		this.runnerData = runnerData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    
	
	
}
