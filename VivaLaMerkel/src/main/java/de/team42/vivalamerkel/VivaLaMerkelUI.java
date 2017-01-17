package de.team42.vivalamerkel;

import com.vaadin.annotations.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import de.team42.vivalamerkel.controller.GameController;
import de.team42.vivalamerkel.view.field.FieldController;
import de.team42.vivalamerkel.view.login.LoginController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;

@Push
@Theme("valo")
@Title("Viva La Merkel")
@PreserveOnRefresh
public class VivaLaMerkelUI extends UI{
    private final Logger LOG = LogManager.getLogger(VivaLaMerkelUI.class.getName());

    @Override
    protected void init(VaadinRequest request) {
        LoginController loginController = new LoginController();
        FieldController fieldController = new FieldController();

        Navigator navigator = new Navigator(this, this);
        navigator.addView("login", loginController.getView());
        navigator.addView("field", fieldController.getView());

        GameController.getInstance().getUis().add(this);

        UI.getCurrent().getNavigator().navigateTo("login");
    }

    @WebServlet(urlPatterns = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = VivaLaMerkelUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    }
}