package mapademo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    
    private ChangeListener<String> validUserLetter;
    private ChangeListener<String> validEmailLetter;
    private ChangeListener<String> validPassLetter;
    private ChangeListener<String> validDateLetter;
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
    private void initialize() {
        //Boton para el cambio entre autentificacion y registro
        enableLog = new SimpleBooleanProperty(Boolean.TRUE);
        
        //Validacion para el boton de registro
        validUser = new SimpleBooleanProperty(Boolean.FALSE);
        validPass = new SimpleBooleanProperty(Boolean.FALSE);
        validEmail = new SimpleBooleanProperty(Boolean.FALSE);
        validDate = new SimpleBooleanProperty(Boolean.FALSE);
        
        //Validacion para el boton de autentificacion
        validLogUser = new SimpleBooleanProperty(Boolean.FALSE);
        validLogPass = new SimpleBooleanProperty(Boolean.FALSE);
        
        //Se activa si todo es TRUE
        btnReg.disableProperty().bind(Bindings.or(validUser.not(), validPass.not()).or(validEmail.not()).or(validDate.not()));
        btnAut.disableProperty().bind(Bindings.or(validLogUser.not(), validLogPass.not()));
        
        //Desactivacion y activacion para ver los menus
        AuthBox.visibleProperty().bind(enableLog);
        AuthBox.disableProperty().bind(enableLog.not());
        RegBox.visibleProperty().bind(enableLog.not());
        RegBox.disableProperty().bind(enableLog);
        
        //Oyentes de los campos del registro
        txtRegUser.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                checkUser();
                if(!validUser.get()){
                    if(validUserLetter == null){
                        validUserLetter = (a,b,c) -> checkUser();
                        txtRegUser.textProperty().addListener(validUserLetter);
                    }
                }
            }
        });
        
        txtRegPass.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                checkPass();
                if(!validPass.get()){
                    if(validPassLetter == null){
                        validPassLetter = (a,b,c) -> checkPass();
                        txtRegPass.textProperty().addListener(validPassLetter);
                    }
                }
            }
        });
        
        txtRegEmail.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                checkEmail();
                if(!validEmail.get()){
                    if(validEmailLetter == null){
                        validEmailLetter = (a,b,c) -> checkEmail();
                        txtRegEmail.textProperty().addListener(validEmailLetter);
                    }
                }
            }
        });
        
        txtRegDate.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                checkDate();
            }
        });
        
        //Oyentes para la autentificacion
        txtLogUser.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                if(txtLogUser.getText().length() > 0){
                    validLogUser.set(true);
                }else{
                    validLogUser.set(false);
                }
            }
        });
        
        txtLogPass.focusedProperty().addListener((obv, oldValue, newValue) ->{
            if(!newValue){
                if(txtLogPass.getText().length() > 0){
                    validLogPass.set(true);
                }else{
                    validLogPass.set(false);
                }
            }
        });
        
        
    }

    @FXML
    void mostrarLogin(ActionEvent event) {
        enableLog.set(Boolean.TRUE);
        txtRegUser.clear();
        txtRegEmail.clear();
        txtRegPass.clear();
        txtRegDate.setValue(null);
        
        validUser.set(false);
        validEmail.set(false);
        validPass.set(false);
        validDate.set(false);
    }
    
    @FXML
    void mostrarReg(ActionEvent event) {
        enableLog.set(Boolean.FALSE);
        txtLogUser.clear();
        txtLogPass.clear();
        
    }
    
    //Muestra de errores copiado de las practicas
    private void showError(boolean valor, Node nodo, Node labelError){
        SportActivityApp aux = SportActivityApp.getInstance();
        if(aux.nickNameExists(txtRegUser.getText())){
            labelErrUser.setText("NickName ya registrada");
            valor = false;
        }else{
            labelErrUser.setText("Nickname debe de tener de entre 6 y 15 caracteres, y sin caracteres especiales");
        }
        labelError.setVisible(!valor);
        nodo.setStyle(((valor) ? "" : "-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10"));
    }
    
    //Metodos auxiliares para las comprobaciones de los campos
    private void checkUser(){
        validUser.set(User.checkNickName(txtRegUser.getText()));
        showError(validUser.get(), txtRegUser, labelErrUser);
    }
    
    private void checkPass(){
        validPass.set(User.checkPassword(txtRegPass.getText()));
        showError(validPass.get(), txtRegPass, labelErrPass);
    }
    
    private void checkEmail(){
        validEmail.set(User.checkEmail(txtRegEmail.getText()));
        showError(validEmail.get(), txtRegEmail, labelErrEmail);
    }
    
    private void checkDate(){
        validDate.set(User.isOlderThan(txtRegDate.getValue(), 13));
        showError(validDate.get(), txtRegDate, labelErrDate);
    }

    @FXML
    private void pasarAlMenu(ActionEvent event) throws IOException {
        SportActivityApp aux = SportActivityApp.getInstance();
        if((txtLogUser.getText().equals("Pepe") && txtLogPass.getText().equals("12345"))){
            labelLogErr.setVisible(false);
            txtLogUser.clear();
            txtLogPass.clear();
            validLogUser.set(false);
            validLogPass.set(false);
            
            MapaDemoApp.setRoot(FXMLLoader.load(getClass().getResource("/FXMLFiles/FXMLMainMenu.fxml")));
            
            
        }else{
            txtLogUser.clear();
            txtLogPass.clear();
            
            txtLogUser.setStyle("-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10");
            txtLogPass.setStyle("-fx-background-color: #FCE5E0; -fx-background-radius: 25; -fx-border-radius: 10");
            
            validLogUser.set(false);
            validLogPass.set(false);
            
            labelLogErr.setVisible(true);
        }
    }

    
    

}
