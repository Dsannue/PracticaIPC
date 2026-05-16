package mapademo;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.Binding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import upv.ipc.sportlib.User;

public class AuthController {

    @FXML
    private VBox AuthBox;

    @FXML
    private VBox RegBox;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReg;

    @FXML
    private Label labelErrAge;

    @FXML
    private Label labelErrEmail;

    @FXML
    private Label labelErrName;

    @FXML
    private Label labelErrPass;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker txtRegDate;

    @FXML
    private TextField txtRegEmail;

    @FXML
    private PasswordField txtRegPass;

    @FXML
    private TextField txtRegUser;

    @FXML
    private TextField usernameField;
    
    private BooleanProperty btnLog = new SimpleBooleanProperty(Boolean.TRUE);
    private BooleanProperty validUser;
    private BooleanProperty validPass;
    private BooleanProperty validEmail;
    private BooleanProperty validAge;
    
    private ChangeListener<String> validUserLetter;
    private ChangeListener<String> validEmailLetter;
    private ChangeListener<String> validPassLetter;
    private ChangeListener<String> validAgeLetter;
    
    @FXML
    private void initialize() {
        System.out.print("Hola");
        btnLog = new SimpleBooleanProperty(Boolean.TRUE);
        validUser = new SimpleBooleanProperty(Boolean.FALSE);
        validPass = new SimpleBooleanProperty(Boolean.FALSE);
        validEmail = new SimpleBooleanProperty(Boolean.FALSE);
        validAge = new SimpleBooleanProperty(Boolean.FALSE);
        
        AuthBox.visibleProperty().bind(btnLog);
        AuthBox.disableProperty().bind(btnLog);
        
        
        txtRegUser.focusedProperty().addListener((obv,old,n) ->{
            if(!n){
                if(!User.checkNickName(txtRegUser.getText())){
                    if(validUserLetter == null){
                        validUserLetter = (a,b,c) -> User.checkNickName(txtRegUser.getText());
                        txtRegUser.textProperty().addListener(validUserLetter);
                        labelErrName.visibleProperty().bind(validUser);
                    }
                }
            }
            validUser.set(User.checkNickName(txtRegUser.getText()));
        });
        
        txtRegEmail.focusedProperty().addListener((obv,old,n) ->{
            if(!n){
                if(!User.checkEmail(txtRegEmail.getText())){
                    if(validEmailLetter == null){
                        validEmailLetter = (a,b,c) -> User.checkEmail(txtRegEmail.getText());
                        txtRegEmail.textProperty().addListener(validEmailLetter);
                        labelErrEmail.visibleProperty().bind(validEmail);
                    }
                }
            }
            validEmail.set(User.checkNickName(txtRegEmail.getText()));
        });
    }

    @FXML
    void handleLogin(ActionEvent event) {
    
    }

    @FXML
    void mostrarLogin(ActionEvent event) {
        btnLog.setValue(Boolean.FALSE);
        RegBox.visibleProperty().bind(btnLog);
        RegBox.disableProperty().bind(btnLog.not());
        AuthBox.visibleProperty().bind(btnLog.not());
        AuthBox.disableProperty().bind(btnLog);
    }
    @FXML
    void mostrarReg(ActionEvent event) {
        btnLog.setValue(Boolean.TRUE);
    }

    
    

}
