package ShoppingMall;

public class CustomerVO {
	private int id;
	private String shoppingmallId;
	private String name;
	private String phoneNumber;
	private String address;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getShoppingmallId() {
		return shoppingmallId;
	}
	
	public void setShoppingmallId(String shoppingmallId) {
		this.shoppingmallId = shoppingmallId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {//오버라이딩 (재정의) 한 것.
		 return id + "\t" + shoppingmallId + "\t" + name + "\t" + phoneNumber + "\t" + address;
	 }

}
