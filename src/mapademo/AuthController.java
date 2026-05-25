package mapademo;

import java.io.File;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import upv.ipc.sportlib.SportActivityApp;
import upv.ipc.sportlib.User;

public class AuthController {

    @FXML
    private VBox AuthBox;

    @FXML
    private VBox RegBox;

    @FXML
    private Button btnReg;

    @FXML
    private Label labelErrEmail;

    @FXML
    private Label labelErrPass;


    @FXML
    private DatePicker txtRegDate;

    @FXML
    private TextField txtRegEmail;

    @FXML
    private PasswordField txtRegPass;

    @FXML
    private TextField txtRegUser;

    
    private BooleanProperty enableLog;
    private BooleanProperty validUser;
    private BooleanProperty validPass;
    private BooleanProperty validEmail;
    private BooleanProperty validDate;
    
    private BooleanProperty validLogUser;
    private BooleanProperty validLogPass;
    
    @FXML
    private Label labelErrUser;
    @FXML
    private Label labelErrDate;
    @FXML
    private Button btnChangeLogin;
    @FXML
    private Button btnAut;
    @FXML
    private Button btnChangeReg;
    @FXML
    private TextField txtLogUser;
    @FXML
    private PasswordField txtLogPass;
    @FXML
    private Label labelLogErr;
    @FXML
    private Label labelRegAvatarPath;
    @FXML
    private Button btnSelectRegAvatar;
    
    private String regAvatarPath;
    
    @FXML
    private void initialize() {
        enableLog = new SimpleBooleanProperty(Boolean.TRUE);

        validUser = new SimpleBooleanProperty(Boolean.FALSE);
        validPass = new SimpleBooleanProperty(Boolean.FALSE);
        validEmail = new SimpleBooleanProperty(Boolean.FALSE);
        validDate = new SimpleBooleanProperty(Boolean.FALSE);

        validLogUser = new SimpleBooleanProperty(Boolean.FALSE);
        validLogPass = new SimpleBooleanProperty(Boolean.FALSE);

        btnReg.disableProperty().bind(Bindings.or(validUser.not(), validPass.not()).or(validEmail.not()).or(validDate.not()));
        btnAut.disableProperty().bind(Bindings.or(validLogUser.not(), validLogPass.not()));

        AuthBox.visibleProperty().bind(enableLog);
        AuthBox.disableProperty().bind(enableLog.not());
        RegBox.visibleProperty().bind(enableLog.not());
        RegBox.disableProperty().bind(enableLog);

        txtRegUser.textProperty().addListener((a, b, c) -> checkUser());
        txtRegPass.textProperty().addListener((a, b, c) -> checkPass());
        txtRegEmail.textProperty().addListener((a, b, c) -> checkEmail());
        txtRegDate.valueProperty().addListener((a, b, c) -> checkDate());

        txtLogUser.textProperty().addListener((a, b, c) -> validLogUser.set(!txtLogUser.getText().trim().isEmpty()));
        txtLogPass.textProperty().addListener((a, b, c) -> validLogPass.set(!txtLogPass.getText().trim().isEmpty()));

        labelErrUser.setVisible(false);
        labelErrPass.setVisible(false);
        labelErrEmail.setVisible(false);
        labelErrDate.setVisible(false);
        labelLogErr.setVisible(false);
    }
    
    /**
     * Alterna la interfaz para mostrar el formulario de Inicio de Sesión (Login).
     * Además, limpia por seguridad todos los campos que el usuario pudiera haber
     * rellenado a medias en el formulario de registro.
     */
    @FXML
    void mostrarLogin(ActionEvent event) {
        enableLog.set(Boolean.TRUE);
        txtRegUser.clear();
        txtRegEmail.clear();
        txtRegPass.clear();
        txtRegDate.setValue(null);
        regAvatarPath = null;
        if (labelRegAvatarPath != null) {
            labelRegAvatarPath.setText("Ninguno seleccionado");
        }
        
        validUser.set(false);
        validEmail.set(false);
        validPass.set(false);
        validDate.set(false);
    }
    
    /**
     * Alterna la interfaz para mostrar el formulario de Registro.
     * Limpia los campos de inicio de sesión y oculta sus posibles mensajes de error.
     */
    @FXML
    void mostrarReg(ActionEvent event) {
        enableLog.set(Boolean.FALSE);
        txtLogUser.clear();
        txtLogPass.clear();
        labelLogErr.setVisible(false);
    }

    /**
     * Método auxiliar para proporcionar feedback visual al usuario.
     * Si un campo no es válido ('valor' es false), muestra un mensaje de texto explicativo
     * debajo del campo y tiñe el fondo del cuadro de texto de un color rojizo.
     */
    private void showError(boolean valor, Node nodo, Label labelError, String message) {
        labelError.setText(message);
        labelError.setVisible(!valor);
        nodo.setStyle(((valor) ? "" : "-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10"));
    }

    /**
     * Comprueba si el nombre de usuario (Nickname) introducido en el registro es válido.
     * Verifica que cumpla con los requisitos mínimos y que NO exista ya en la base de datos.
     */
    private void checkUser() {
        boolean ok = User.checkNickName(txtRegUser.getText());
        if (ok && SportActivityApp.getInstance().nickNameExists(txtRegUser.getText().trim())) {
            ok = false;
            showError(false, txtRegUser, labelErrUser, "Nickname ya registrado");
        } else {
            showError(ok, txtRegUser, labelErrUser, "Nickname inválido (6-15, letras/dígitos/-/_)");
        }
        validUser.set(ok);
    }

    /**
     * Comprueba si la contraseña introducida en el registro cumple con los estándares
     * de seguridad exigidos (al menos una mayúscula, minúscula, número, símbolo y longitud correcta).
     */
    private void checkPass() {
        String pass = txtRegPass.getText() == null ? "" : txtRegPass.getText().trim();
        validPass.set(User.checkPassword(pass) || isPasswordValidBySpec(pass));
        showError(validPass.get(), txtRegPass, labelErrPass, "Contraseña inválida (8-20 con may/min/número/símbolo)");
    }

    /**
     * Verifica que el correo electrónico introducido tenga un formato estándar válido.
     */
    private void checkEmail() {
        validEmail.set(User.checkEmail(txtRegEmail.getText()));
        showError(validEmail.get(), txtRegEmail, labelErrEmail, "Email inválido");
    }

    /**
     * Verifica la fecha de nacimiento introducida. El sistema requiere que
     * el usuario tenga como mínimo 12 años de edad para poder registrarse.
     */
    private void checkDate() {
        validDate.set(txtRegDate.getValue() != null && User.isOlderThan(txtRegDate.getValue(), 12));
        showError(validDate.get(), txtRegDate, labelErrDate, "Debes ser mayor de 12 años");
    }

    /**
     * Se ejecuta al pulsar el botón "Autentificar". Intenta iniciar sesión con las
     * credenciales dadas. Si tiene éxito, carga la ventana principal de la aplicación.
     * Si falla, muestra un error visual advirtiendo de credenciales incorrectas.
     */
    @FXML
    private void pasarAlMenu(ActionEvent event) throws IOException {
        SportActivityApp app = SportActivityApp.getInstance();
        boolean ok = app.login(txtLogUser.getText().trim(), txtLogPass.getText());
        if (ok) {
            labelLogErr.setVisible(false);
            txtLogUser.clear();
            txtLogPass.clear();
            validLogUser.set(false);
            validLogPass.set(false);

            MapaDemoApp.setRoot(FXMLLoader.load(getClass().getResource("/FXMLFiles/FXMLMainMenu.fxml")));
        } else {
            txtLogUser.clear();
            txtLogPass.clear();

            txtLogUser.setStyle("-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10");
            txtLogPass.setStyle("-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10");

            validLogUser.set(false);
            validLogPass.set(false);

            labelLogErr.setVisible(true);
        }
    }
    
    /**
     * Se ejecuta al pulsar "Registrarse". Recoge los datos de todos los campos, intenta
     * crear un nuevo usuario en el sistema y, si lo consigue, devuelve al usuario a la 
     * pantalla de Login para que inicie sesión.
     */
    @FXML
    private void registrarUsuario(ActionEvent event) {
        SportActivityApp app = SportActivityApp.getInstance();
        String password = txtRegPass.getText() == null ? "" : txtRegPass.getText().trim();
        boolean ok = app.registerUser(
                txtRegUser.getText().trim(),
                txtRegEmail.getText().trim(),
                password,
                txtRegDate.getValue(),
                regAvatarPath
        );
        if (ok) {
            mostrarLogin(event);
        } else {
            checkUser();
            checkEmail();
            checkPass();
            checkDate();
        }
    }

    /**
     * Método auxiliar que utiliza una Expresión Regular (Regex) para validar de 
     * forma estricta que la contraseña cumpla los patrones de seguridad dictados
     * por las especificaciones de la aplicación.
     */
    private boolean isPasswordValidBySpec(String pass) {
        if (pass == null) {
            return false;
        }
        return pass.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%&*()_\\-+=]).{8,20}$");
    }

    /**
     * Abre un cuadro de diálogo del sistema operativo (FileChooser) para permitir 
     * al usuario seleccionar una imagen desde su ordenador como avatar personal.
     */
    @FXML
    private void seleccionarRegAvatar(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        fc.getExtensionFilters().addAll(new ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File imgFile = fc.showOpenDialog(btnSelectRegAvatar.getScene().getWindow());
        if (imgFile != null) {
            regAvatarPath = imgFile.getAbsolutePath();
            labelRegAvatarPath.setText(imgFile.getName());
        }
    }

}