package br.com.jm.tarefas.interfaces.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller para renderização da página de 
 * login
 */
@Controller
public class LoginController {

    @Autowired
    @Qualifier("inicializadorBD")
    private Boolean inicializadorBD;

    @RequestMapping(value = "/login")
    public ModelAndView iniciar() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("exibeUsuarios", inicializadorBD);
        return mav;
    }
}
