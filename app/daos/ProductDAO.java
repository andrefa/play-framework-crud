package daos;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Finder;

import models.Product;

public class ProductDAO {

	private Finder<Long, Product> productFinder = new Finder<>(Product.class);

	public Optional<Product> findByCode(String code) {
		System.out.println(productFinder);
		Product product = productFinder
				.where()
				.eq(Product.CODE, code)
				.findUnique();
		return Optional.ofNullable(product);
	}

	public List<Product> list() {
		return productFinder.all();
	}

	public List<Product> list(Map<String, String> params) {
		ExpressionList<Product> query = productFinder.where();
		params.entrySet().forEach(entry -> {
			query.eq(entry.getKey(), entry.getValue());
		});
		return query.findList();
	}

	public List<Product> listByCategory(String category) {
		return productFinder.where().eq(Product.CATEGORY, category).findList();
	}

	public Optional<Product> findById(Long id) {
		System.out.println(productFinder);
		Product product = productFinder
				.where()
				.eq(Product.ID, id)
				.findUnique();
		return Optional.ofNullable(product);
	}
}
