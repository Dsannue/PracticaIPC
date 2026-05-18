/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapademo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class MapaDemoApp extends Application {
    
    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/FXMLMainMenu.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        scene = new Scene(root);
        stage.setTitle("Running La Safor");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setRoot(Parent root){
        scene.setRoot(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
