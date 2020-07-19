package validators;

import javax.inject.Inject;

import daos.ProductDAO;
import models.Product;
import play.data.Form;
import play.data.validation.ValidationError;

import java.util.Optional;

public class ProductValidator {

	@Inject 
	private ProductDAO productDAO;

	public boolean isInvalid(Form<Product> form) {
		Product product = form.get();

		if (product.getPrice() < 0.0) {
			form.reject(new ValidationError("price", "Price should be positive"));
		}

		Optional<Product> optionalProduct = productDAO.findByCode(product.getCode());
		if (optionalProduct.isPresent() && !optionalProduct.get().getId().equals(product.getId())) {
			form.reject(new ValidationError("code", "Code already in use"));
		}

		return form.hasErrors();
	}

}
