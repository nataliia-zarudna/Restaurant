package sirobaba.testtask.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NavigationController {

	@RequestMapping(value={"/", "index"},method = RequestMethod.GET)
	public String index(ModelMap model) {

		return "index";
	}

	@RequestMapping(value = "contact",method = RequestMethod.GET)
	public String contact(ModelMap model) {

		return "contact";
	}

	@RequestMapping(value = "error*",method = RequestMethod.GET)
	public String error(ModelMap model, HttpServletRequest request) {

		return request.getRequestURI();
	}
}