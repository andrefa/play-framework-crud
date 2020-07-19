package controllers.v1;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import daos.ProductDAO;
import models.Product;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.libs.Json;
import play.mvc.*;

public class ProductApiController extends Controller {

	@Inject
	private ProductDAO productDAO;
	@Inject
	private FormFactory formFactory;
	
	public Result all() {
		return ok(productsJson(productDAO.list()));
	}
	
	public Result ofCategory(String category) {
		return ok(productsJson(productDAO.listByCategory(category)));
	}
	
	public Result withFilters() {
		DynamicForm form = formFactory.form().bindFromRequest();

		validateForm(form);
		if (form.hasErrors()) {
			JsonNode errors = Json.newObject().set("errors", form.errorsAsJson());
			return badRequest(errors);
		}

		return ok(productsJson(productDAO.list(form.data())));
	}

	private void validateForm(DynamicForm form) {
		Map<String, String> params = form.data();
		params.keySet().forEach(key -> {
			if(!Product.FIELDS.contains(key)) {
				form.reject(new ValidationError("Invalid filter key", key));
			}
		});
	}

	private JsonNode productsJson(List<Product> products) {
		return Json.newObject().set("products", Json.toJson(products));
	}
}
