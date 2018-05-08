package cn.zzy.mywebsite.Controller.TeamplatePageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/Home")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String HomePage()
    {
        return "Home";
    }
}
