package cn.zzy.mywebsite.Controller.TeamplatePageController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/Home")
public class HomeController {

    @Autowired
    private BlogController blogController;

    @RequestMapping(method = RequestMethod.GET)
    public String HomePage(Model model) {
        return blogController.BlogPage(model);
    }
}
