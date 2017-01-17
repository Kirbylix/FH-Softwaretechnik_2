package de.team42.vivalamerkel.view.login;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;
import de.team42.vivalamerkel.model.Player;
import de.team42.vivalamerkel.model.global.GameField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {
    private final Logger LOG = LogManager.getLogger(LoginController.class);

    private final LoginView loginView;
    private final ViewListener viewListener;

    /**
     * Konstruktor
     */
    public LoginController() {
        this.viewListener = new ViewListener();
        this.loginView = new LoginView();
        this.loginView.addLoginViewListener(this.viewListener);
    }

    /**
     * Get view
     * @return view
     */
    public View getView(){
        return this.loginView;
    }

    /**
     * Login Button wurde gedrückt
     */
    private void loginButtonPress(){
        if(GameField.getInstance().getSizeOfPlayer()<5){
            Player player = new Player(this.loginView.getUsernameInput());
            LOG.info("ID: " + player.getId());
            UI.getCurrent().getSession().setAttribute("userId", player.getId());
            GameField.getInstance().addPlayer(player);

            UI.getCurrent().getNavigator().navigateTo("field");
        }else{
            this.loginView.showErrorMessage("Kein Spielerplatz mehr vorhanden");
            this.loginView.resetView();
        }
    }

    /**
     * Listener Klasse
     */
    class ViewListener implements LoginViewListener{
        @Override
        public void loginClicked() {
            loginButtonPress();
        }
    }
}