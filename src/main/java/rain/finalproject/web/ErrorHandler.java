package rain.finalproject.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Throwable T) {
        System.out.println(T.getMessage());
        System.out.println(T.toString());
        T.printStackTrace();

        //System.out.println(T.getCause().getMessage());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}