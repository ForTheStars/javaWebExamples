package info.model;

public class Product {
	private int id;
	@ValidateForm(type=ValidateType.NotNull,errorMsg="商品名称不能为空")
	private String name;
	@ValidateForm(type=ValidateType.Number,errorMsg="商品价格的格式不正确，必须是数字")
	private double price;
	private String intro;
	private String img;
	@ValidateForm(type=ValidateType.Number,errorMsg="商品库存的格式必须是数字")
	private int stock;
	/**
	 * 有两个状态，1表示可以购买商品，-1表示下架的商品
	 */
	private int status;
	private Category category;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", intro=" + intro + ", img=" + img + ", stock=" + stock
				+ ", status=" + status + ", category=" + category + "]";
	}
}
