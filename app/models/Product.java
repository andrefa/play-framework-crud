package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

import java.util.Arrays;
import java.util.List;

@Entity
public class Product extends Model {

	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CODE = "code";
	public static final String CATEGORY = "category";
	public static final String DESCRIPTION = "description";
	public static final String PRICE = "price";

	public static final List<String> FIELDS = Arrays.asList(
			ID, TITLE, CODE, CATEGORY, DESCRIPTION, PRICE
	);

	@Id
	@GeneratedValue
	private Long id;

	@Required(message = "Title is mandatory")
	private String title;

	@Required(message = "Code is mandatory")
	private String code;

	@Required(message = "Category is mandatory")
	private String category;

	private String description;

	@Required(message = "Price is mandatory")
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
