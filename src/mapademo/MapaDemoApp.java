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
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/FXMLAuthentificator.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logoDef.png")));
        scene = new Scene(root);
        stage.setTitle("Running La Safor");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            upv.ipc.sportlib.SportActivityApp.getInstance().logout();
        });
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