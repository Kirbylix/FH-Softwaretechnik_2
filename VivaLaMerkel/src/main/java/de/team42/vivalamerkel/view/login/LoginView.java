package de.team42.vivalamerkel.view.login;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class LoginView  extends VerticalLayout implements View {
    private final Logger LOG = LogManager.getLogger(LoginView.class);
    private final List<LoginViewListener> listeners;

    private TextField tfUsername;
    private Button btnLogin = new Button();

    /**
     * Konstruktor
     */
    LoginView(){
        this.setSizeFull();
        this.setSpacing(true);
        this.setMargin(true);

        this.listeners = new LinkedList<>();
        this.setUpComponents();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        tfUsername.focus();
    }

    /**
     * Komponenten Deklaration
     */
    private void setUpComponents(){
        this.btnLogin = this.createButtonLogin();
        this.tfUsername = new TextField("Spielername:");

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addComponent(this.tfUsername);
        vLayout.addComponent(this.btnLogin);
        vLayout.setComponentAlignment(this.btnLogin, Alignment.MIDDLE_CENTER);

        FormLayout form = new FormLayout();
        form.setSpacing(true);
        form.setCaption("Login");

        form.addComponent(this.tfUsername);
        form.addComponent((this.btnLogin));

        this.addComponent(form);
        this.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
    }

    /**
     * Button Login
     * @return Button
     */
    private Button createButtonLogin() {
        final Button button = new Button("Login");
        button.addClickListener(
                event -> fireLoginClickedEvent());
        button.setIcon(FontAwesome.SIGN_IN);
        return button;
    }

    /**
     * Event Button Login
     */
    private void fireLoginClickedEvent() {
        for (final LoginViewListener listener: this.listeners) {
            listener.loginClicked();
        }
    }

    /**
     * Listener hinzufügen
     * @param listener LoginViewListener
     */
    void addLoginViewListener(final LoginViewListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        if (listeners.contains(listener)) {
            LOG.warn("Listener already registered: " + listener);
            return;
        }
        this.listeners.add(listener);
    }

    /**
     * Get Username
     * @return username
     */
    String getUsernameInput(){
        return this.tfUsername.getValue();
    }

    /**
     * ResetView
     */
    void resetView(){
        this.tfUsername.clear();
    }

    /**
     * Show Error Nachricht
     * @param message Nachricht
     */
    void showErrorMessage(String message) {
        final Notification notification = new Notification(message, Notification.Type.ERROR_MESSAGE);
        notification.show(UI.getCurrent().getPage());
    }

    /**
     * Show Nachricht
     * @param message Nachricht
     */
    public void showMessage(String message) {
        final Notification notification = new Notification(message, Notification.Type.HUMANIZED_MESSAGE);
        notification.show(UI.getCurrent().getPage());
    }
}
