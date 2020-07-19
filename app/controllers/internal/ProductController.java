package controllers.internal;

import daos.ProductDAO;
import models.Product;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import validators.ProductValidator;
import views.html.product.add;
import views.html.product.edit;
import views.html.product.list;

import javax.inject.Inject;
import java.util.Optional;

public class ProductController extends Controller {

	@Inject
	private FormFactory formFactory;
	@Inject
	private ProductValidator productValidator;
	@Inject
	private ProductDAO productDAO;

	public Result list() {
		return ok(list.render(productDAO.list()));
	}
	
	public Result save() {
		Form<Product> form = formFactory.form(Product.class).bindFromRequest();

		if (productValidator.isInvalid(form)) {
			flash("danger", "There are errors on your form.");
			return badRequest(add.render(form));
		}

		Product product = form.get();
		product.save();

		flash("success", "Product '"+ product.getTitle()+"' saved.");
		return redirect(routes.ProductController.list());
	}
	
	public Result create() {
		return ok(add.render(formFactory.form(Product.class)));
	}

	public Result delete(Long id) {
		Optional<Product> optional = productDAO.findById(id);
		if (!optional.isPresent()) {
			flash("danger", "Informed product does not exist.");
			return badRequest(list.render(productDAO.list()));
		}
		Product product = optional.get();
		product.delete();
		flash("success", "Product '"+ product.getTitle()+"' deleted.");
		return ok(list.render(productDAO.list()));
	}

	public Result edit(Long id) {
		Optional<Product> optional = productDAO.findById(id);
		if (!optional.isPresent()) {
			flash("danger", "Informed product does not exist.");
			return notFound();
		}
		System.out.println("ID = " +  optional.get().getId());
		return ok(edit.render(formFactory.form(Product.class).fill(optional.get()), id));
	}

	public Result update(Long id) {
		Form<Product> form = formFactory.form(Product.class).bindFromRequest();

		if (productValidator.isInvalid(form)) {
			flash("danger", "There are errors on your form.");
			return badRequest(edit.render(form, id));
		}

		Product product = form.get();
		product.update();

		flash("success", "Product '"+ product.getTitle()+"' updated.");
		return redirect(routes.ProductController.list());
	}
}
