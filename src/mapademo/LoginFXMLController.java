/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mapademo;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import upv.ipc.sportlib.User;
import upv.ipc.sportlib.SportActivityApp;

/**
 * FXML Controller class
 *
 * @author dsanc
 */

public class LoginFXMLController implements Initializable {

    @FXML
    private TextField txtNick;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPass;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lblAvatar;

    @FXML
    private Label lblResultado;

    private File avatarSeleccionado;

    private SportActivityApp app;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        app = SportActivityApp.getInstance();

        lblAvatar.setText("Sin avatar");
        lblResultado.setText("");
    }

    @FXML
    public void seleccionarAvatar() {

        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar avatar");

        selector.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(
                "Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"
            )
        );

        avatarSeleccionado = selector.showOpenDialog(null);

        if (avatarSeleccionado != null) {
            lblAvatar.setText(avatarSeleccionado.getName());
        }
    }

    @FXML
    public void registrarUsuario() {

        String nick = txtNick.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = txtPass.getText();
        LocalDate fecha = dpFecha.getValue();

        if (nick.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            lblResultado.setText("Rellena todos los campos obligatorios.");
            return;
        }

        if (fecha == null) {
            lblResultado.setText("Selecciona la fecha de nacimiento.");
            return;
        }

        if (!User.checkNickName(nick)) {
            lblResultado.setText("Nickname no válido.");
            return;
        }

        if (!User.checkEmail(email)) {
            lblResultado.setText("Correo electrónico no válido.");
            return;
        }

        if (!User.checkPassword(pass)) {
            lblResultado.setText("Contraseña no válida.");
            return;
        }

        if (!User.isOlderThan(fecha, 12)) {
            lblResultado.setText("Debes tener más de 12 años.");
            return;
        }

        String avatarPath = null;

        if (avatarSeleccionado != null) {
            avatarPath = avatarSeleccionado.getAbsolutePath();
        }

        boolean registrado = app.registerUser(
            nick,
            email,
            pass,
            fecha,
            avatarPath
        );

        if (registrado) {
            lblResultado.setText("Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            lblResultado.setText("No se pudo registrar. Nickname en uso.");
        }
    }

    private void limpiarCampos() {

        txtNick.clear();
        txtEmail.clear();
        txtPass.clear();
        dpFecha.setValue(null);

        avatarSeleccionado = null;
        lblAvatar.setText("Sin avatar");
    }
}