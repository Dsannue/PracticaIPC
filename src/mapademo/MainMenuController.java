package mapademo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.format.DateTimeFormatter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import upv.ipc.sportlib.Activity;
import upv.ipc.sportlib.Annotation;
import upv.ipc.sportlib.AnnotationType;
import upv.ipc.sportlib.GeoPoint;
import upv.ipc.sportlib.MapProjection;
import upv.ipc.sportlib.MapRegion;
import upv.ipc.sportlib.Session;
import upv.ipc.sportlib.SportActivityApp;
import upv.ipc.sportlib.TrackPoint;
import upv.ipc.sportlib.User;

public class MainMenuController implements Initializable {

    private final SportActivityApp app = SportActivityApp.getInstance();

    private Group zoomGroup;
    private Pane mapPane;
    private MapProjection projection;
    private ContextMenu mapContextMenu;
    private Activity currentActivity;
    private AnnotationType pendingType;
    private GeoPoint pendingFirstPoint;
    private String pendingColor = "#E74C3C";
    private String pendingText = "";
    private File selectedMapFile;
    private String avatarPath;
    private Circle hoverPointMarker;

    @FXML
    private ListView<Activity> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private SplitPane mapAndChartSplitPane;
    @FXML
    private VBox vboxElevationProfile;
    @FXML
    private Slider zoom_slider;
    @FXML
    private Label mousePosition;
    @FXML
    private Button btnMapChanger;
    @FXML
    private StackPane leftPanelContainer;
    @FXML
    private VBox detailView;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnBorrarActividad;
    @FXML
    private Label lblDetalleNombre;
    @FXML
    private Label lblDetalleDistancia;
    @FXML
    private Label lblDetalleDuracion;
    @FXML
    private Label lblDetalleDesnivel;
    @FXML
    private Label lblDetalleVelocidad;
    @FXML
    private Label lblDetalleRitmo;
    @FXML
    private Label lblDetalleAltitud;
    @FXML
    private VBox masterView;
    @FXML
    private Button btnAddActivity;
    @FXML
    private Button btnActAcumulation;
    @FXML
    private StackPane centerContainer;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnVolverDesdePerfil;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Label lblErrEmailMod;
    @FXML
    private Label lblErrPassMod;
    @FXML
    private Label lblErrDateMod;
    @FXML
    private SplitPane menuMapa;
    @FXML
    private VBox menuAñadirMapa;
    @FXML
    private VBox menuGestionMapas;
    @FXML
    private VBox menuModPerfil;
    @FXML
    private Button btnCamAvaModPer;
    @FXML
    private Button btnDescartarModPer;
    @FXML
    private Button btnGuardarModPer;
    @FXML
    private VBox menuHistorial;
    @FXML
    private Label lblTotalSessions;
    @FXML
    private Label lblTotalTime;
    @FXML
    private VBox vboxRecentSessions;
    @FXML
    private Button btnVolverDesdeHistorial;
    @FXML
    private ImageView imgAvatarMod;
    @FXML
    private ImageView imgAvatarTop;
    
    private IntegerProperty cambioPestaña;
    private BooleanProperty modEmailValid;
    private BooleanProperty modPassValid;
    private BooleanProperty modDateValid;
    @FXML
    private Button btnSelectMapa;
    @FXML
    private Label labelPathMap;
    @FXML
    private Button btnBorrarMap;
    @FXML
    private Button btnSelecMapa;
    @FXML
    private TextField txtNombreRegion;
    @FXML
    private ListView<MapRegion> mapRegionsList;
    
    private BooleanProperty coord1;
    private BooleanProperty coord2;
    private BooleanProperty coord3;
    private BooleanProperty coord4;
    private BooleanProperty mapaSelected;
    @FXML
    private TextField txtCoord1;
    @FXML
    private TextField txtCoord2;
    @FXML
    private TextField txtCoord3;
    @FXML
    private TextField txtCoord4;
    
    private Button btnVolverDesdeAñadirMapa;
    @FXML
    private MenuItem btnLogOut;
    @FXML
    private MenuItem btnprofileMod;
    @FXML
    private MenuItem btnHistorialSesion;
    
    @FXML
    void zoomIn(javafx.event.ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + 0.1);
    }

    @FXML
    void zoomOut(javafx.event.ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal - 0.1);
    }

    private void zoom(double scaleValue) {
        if (zoomGroup == null) {
            return;
        }
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
   void listClicked(MouseEvent event) {
        Activity selected = map_listview.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        renderActivity(selected);
    }

    private void buildMap(File imageFile, MapRegion region) {
        if (imageFile == null || !imageFile.exists()) {
            map_scrollpane.setContent(new Label("Imagen no encontrada: " + (imageFile == null ? "-" : imageFile.getPath())));
            return;
        }

        Image img = new Image(imageFile.toURI().toString());
        projection = new MapProjection(region, img.getWidth(), img.getHeight());
        mapPane = new Pane();
        mapPane.setPrefSize(img.getWidth(), img.getHeight());

        ImageView iv = new ImageView(img);
        iv.setFitWidth(img.getWidth());
        iv.setFitHeight(img.getHeight());
        mapPane.getChildren().add(iv);

        mapPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                onMapRightClick(e.getX(), e.getY());
            } else if (e.getButton() == MouseButton.PRIMARY && pendingType != null && pendingFirstPoint != null) {
                GeoPoint second = projection.unproject(e.getX(), e.getY());
                saveAnnotation(pendingType, pendingText, pendingColor, List.of(pendingFirstPoint, second));
                pendingType = null;
                pendingFirstPoint = null;
                pendingText = "";
                pendingColor = "#E74C3C";
                mapPane.setStyle("");
            }
        });

        zoomGroup = new Group();
        Group contentGroup = new Group();
        zoomGroup.getChildren().add(mapPane);
        contentGroup.getChildren().add(zoomGroup);
        double zoom = zoom_slider.getValue();
        zoomGroup.setScaleX(zoom);
        zoomGroup.setScaleY(zoom);
        map_scrollpane.setContent(contentGroup);
    }

    private void onMapRightClick(double x, double y) {
        if (currentActivity == null || projection == null) {
            showInfo("Selecciona una actividad para añadir anotaciones.");
            return;
        }
        mapContextMenu.hide();
        final double clickX = x;
        final double clickY = y;
        mapContextMenu.getItems().get(0).setOnAction(e -> addAnnotationFromClick(AnnotationType.POINT, clickX, clickY));
        mapContextMenu.getItems().get(1).setOnAction(e -> addAnnotationFromClick(AnnotationType.TEXT, clickX, clickY));
        mapContextMenu.getItems().get(2).setOnAction(e -> startTwoPointAnnotation(AnnotationType.LINE, clickX, clickY));
        mapContextMenu.getItems().get(3).setOnAction(e -> startTwoPointAnnotation(AnnotationType.CIRCLE, clickX, clickY));

        mapContextMenu.show(
                mapPane.getScene().getWindow(),
                mapPane.localToScreen(x, y).getX(),
                mapPane.localToScreen(x, y).getY()
        );
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((observable, oldVal, newVal) -> zoom((Double) newVal));

        MenuItem miPoint = new MenuItem("Añadir punto");
        MenuItem miText = new MenuItem("Añadir texto");
        MenuItem miLine = new MenuItem("Añadir línea");
        MenuItem miCircle = new MenuItem("Añadir círculo");
        mapContextMenu = new ContextMenu(miPoint, miText, miLine, miCircle);

        map_listview.setCellFactory(listView -> new ListCell<Activity>() {
            @Override
            protected void updateItem(Activity activity, boolean empty) {
                super.updateItem(activity, empty);
                if (empty || activity == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(activity.getName());
                }
            }
        });

        cambioPestaña = new SimpleIntegerProperty(0);
        menuMapa.visibleProperty().bind(Bindings.equal(0, cambioPestaña));
        menuGestionMapas.visibleProperty().bind(Bindings.equal(1, cambioPestaña));
        menuModPerfil.visibleProperty().bind(Bindings.equal(2, cambioPestaña));
        menuHistorial.visibleProperty().bind(Bindings.equal(3, cambioPestaña));

        menuMapa.managedProperty().bind(menuMapa.visibleProperty());
        menuGestionMapas.managedProperty().bind(menuGestionMapas.visibleProperty());
        menuModPerfil.managedProperty().bind(menuModPerfil.visibleProperty());
        menuHistorial.managedProperty().bind(menuHistorial.visibleProperty());

        detailView.managedProperty().bind(detailView.visibleProperty());
        masterView.managedProperty().bind(masterView.visibleProperty());

        coord1 = new SimpleBooleanProperty(Boolean.FALSE);
        coord2 = new SimpleBooleanProperty(Boolean.FALSE);
        coord3 = new SimpleBooleanProperty(Boolean.FALSE);
        coord4 = new SimpleBooleanProperty(Boolean.FALSE);
        mapaSelected = new SimpleBooleanProperty(Boolean.FALSE);

        btnGuardar.disableProperty().bind(Bindings.or(coord1.not(), coord2.not()).or(coord3.not()).or(coord4.not()).or(mapaSelected.not()));
        txtCoord1.disableProperty().bind(mapaSelected.not());
        txtCoord2.disableProperty().bind(mapaSelected.not());
        txtCoord3.disableProperty().bind(mapaSelected.not());
        txtCoord4.disableProperty().bind(mapaSelected.not());

        txtCoord1.textProperty().addListener((a, b, c) -> coord1.set(isDouble(c)));
        txtCoord2.textProperty().addListener((a, b, c) -> coord2.set(isDouble(c)));
        txtCoord3.textProperty().addListener((a, b, c) -> coord3.set(isDouble(c)));
        txtCoord4.textProperty().addListener((a, b, c) -> coord4.set(isDouble(c)));

        mapRegionsList.setCellFactory(list -> new ListCell<MapRegion>() {
            @Override
            protected void updateItem(MapRegion item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        modEmailValid = new SimpleBooleanProperty(true);
        modPassValid = new SimpleBooleanProperty(true);
        modDateValid = new SimpleBooleanProperty(true);

        btnGuardarModPer.disableProperty().bind(
            modEmailValid.not().or(modPassValid.not()).or(modDateValid.not())
        );

        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            boolean valid = User.checkEmail(newVal);
            modEmailValid.set(valid);
            if (valid) {
                lblErrEmailMod.setVisible(false);
                txtEmail.setStyle("");
            } else {
                lblErrEmailMod.setText("Correo electrónico no válido");
                lblErrEmailMod.setVisible(true);
                txtEmail.setStyle("-fx-background-color: #FCE5E0; -fx-border-color: #E74C3C; -fx-border-radius: 8; -fx-background-radius: 8;");
            }
        });

        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                modPassValid.set(true);
                lblErrPassMod.setVisible(false);
                txtPassword.setStyle("");
            } else {
                boolean valid = User.checkPassword(newVal);
                modPassValid.set(valid);
                if (valid) {
                    lblErrPassMod.setVisible(false);
                    txtPassword.setStyle("");
                } else {
                    lblErrPassMod.setText("Contraseña no válida (8-20 car., Mayús, Minús, Núm, Símb)");
                    lblErrPassMod.setVisible(true);
                    txtPassword.setStyle("-fx-background-color: #FCE5E0; -fx-border-color: #E74C3C; -fx-border-radius: 8; -fx-background-radius: 8;");
                }
            }
        });

        dpFecha.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                modDateValid.set(false);
                lblErrDateMod.setText("Fecha requerida");
                lblErrDateMod.setVisible(true);
                dpFecha.setStyle("-fx-background-color: #FCE5E0; -fx-border-color: #E74C3C; -fx-border-radius: 8; -fx-background-radius: 8;");
            } else {
                boolean valid = User.isOlderThan(newVal, 12);
                modDateValid.set(valid);
                if (valid) {
                    lblErrDateMod.setVisible(false);
                    dpFecha.setStyle("");
                } else {
                    lblErrDateMod.setText("Debes ser mayor de 12 años");
                    lblErrDateMod.setVisible(true);
                    dpFecha.setStyle("-fx-background-color: #FCE5E0; -fx-border-color: #E74C3C; -fx-border-radius: 8; -fx-background-radius: 8;");
                }
            }
        });

        if (mapAndChartSplitPane != null && vboxElevationProfile != null) {
            mapAndChartSplitPane.getItems().remove(vboxElevationProfile);
        }

        loadProfileData();
        loadActivities();
        loadMapRegions();
    }

    @FXML
    private void showPosition(MouseEvent event) {
        mousePosition.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY());
    }

    @FXML
    private void about(javafx.event.ActionEvent event) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        Stage dialogStage = (Stage) mensaje.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logoDef.png")));
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2026");
        mensaje.setContentText("Running la Safor");
        mensaje.showAndWait();
    }

    @FXML
    private void cambiarMapa(javafx.event.ActionEvent event) {
        cambioPestaña.set(1);
        btnMapChanger.setDisable(true);
        loadMapRegions();
    }

    @FXML
    private void volverDesdePerfilAlMapa(javafx.event.ActionEvent event) {
        cambioPestaña.set(0);
    }

    @FXML
    private void cambioAvatar(javafx.event.ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        fc.getExtensionFilters().addAll(new ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File imgFile = fc.showOpenDialog(zoom_slider.getScene().getWindow());
        if (imgFile != null) {
            avatarPath = imgFile.getAbsolutePath();
            if (imgAvatarMod != null) {
                imgAvatarMod.setImage(new Image(imgFile.toURI().toString()));
            }
        }
    }

    @FXML
    private void descartarModPerfil(javafx.event.ActionEvent event) {
        loadProfileData();
    }

    @FXML
    private void guardarModPerfil(javafx.event.ActionEvent event) {
        User current = app.getCurrentUser();
        if (current == null) {
            return;
        }
        String pass = (txtPassword.getText() == null || txtPassword.getText().trim().isEmpty())
                ? current.getPassword()
                : txtPassword.getText();
        String avatar = avatarPath == null ? current.getAvatarPath() : avatarPath;
        boolean ok = app.updateCurrentUser(txtEmail.getText().trim(), pass, dpFecha.getValue(), avatar);
        if (ok) {
            txtPassword.clear();
            loadProfileData();
            showInfo("Perfil actualizado correctamente.");
        } else {
            showInfo("No se pudo actualizar el perfil. Revisa los datos.");
        }
    }

    @FXML
    private void volverDesdeHistorial(javafx.event.ActionEvent event) {
        cambioPestaña.set(0);
    }

    @FXML
    private void mapaSeleccionado(javafx.event.ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        fc.getExtensionFilters().addAll(new ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File imgFile = fc.showOpenDialog(zoom_slider.getScene().getWindow());

        if (imgFile != null) {
            labelPathMap.setVisible(true);
            labelPathMap.setText(imgFile.getAbsolutePath());
            selectedMapFile = imgFile;
            mapaSelected.set(true);
        } else {
            labelPathMap.setVisible(false);
            selectedMapFile = null;
            mapaSelected.set(false);
        }
    }

    @FXML
    private void seleccionarMapaDeLista(javafx.event.ActionEvent event) {
        MapRegion selected = mapRegionsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Selecciona un mapa de la lista para cargarlo.");
            return;
        }
        buildMap(new File(selected.getImagePath()), selected);
        if (currentActivity != null) {
            drawRoute(currentActivity);
            drawAnnotations(currentActivity);
            centerOnActivityStart(currentActivity);
        }
        cambioPestaña.set(0);
        btnMapChanger.setDisable(false);
    }

    @FXML
    private void descartarAñadirMapa(javafx.event.ActionEvent event) {
        selectedMapFile = null;
        txtCoord1.clear();
        txtCoord2.clear();
        txtCoord3.clear();
        txtCoord4.clear();
        labelPathMap.setVisible(false);
        mapaSelected.set(false);
        coord1.set(false);
        coord2.set(false);
        coord3.set(false);
        coord4.set(false);
    }

    @FXML
    private void guardarMapa(javafx.event.ActionEvent event) {
        if (selectedMapFile == null) {
            return;
        }
        String name = txtNombreRegion.getText() == null || txtNombreRegion.getText().trim().isEmpty()
                ? selectedMapFile.getName()
                : txtNombreRegion.getText().trim();
        MapRegion region = app.addMapRegion(
                name,
                selectedMapFile,
                Double.parseDouble(txtCoord2.getText()),
                Double.parseDouble(txtCoord1.getText()),
                Double.parseDouble(txtCoord4.getText()),
                Double.parseDouble(txtCoord3.getText())
        );
        if (region != null) {
            showInfo("Mapa añadido: " + region.getName());
            descartarAñadirMapa(event);
            loadMapRegions();
        } else {
            showInfo("No se pudo añadir el mapa.");
        }
        btnMapChanger.setDisable(false);
        cambioPestaña.set(0);
    }

    @FXML
    private void volverDesdeAñadirMapa(javafx.event.ActionEvent event) {
        cambioPestaña.set(0);
        btnMapChanger.setDisable(false);
    }

    private boolean confirmAction(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Button btnAceptar = (Button) root.lookup("#btnAceptar");
            Button btnCancelar = (Button) root.lookup("#btnCancelar");

            Stage stage = new Stage();
            stage.initOwner(zoom_slider.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);

            final boolean[] result = {false};

            if (btnAceptar != null) {
                btnAceptar.setOnAction(e -> {
                    result[0] = true;
                    stage.close();
                });
            }

            if (btnCancelar != null) {
                btnCancelar.setOnAction(e -> {
                    result[0] = false;
                    stage.close();
                });
            }

            stage.showAndWait();
            return result[0];
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @FXML
    private void logout(javafx.event.ActionEvent event) {
        boolean ok = confirmAction("/FXMLFiles/FXMLDoubleCheckLogOut.fxml");
        if (ok) {
            app.logout();
            try {
                MapaDemoApp.setRoot(javafx.fxml.FXMLLoader.load(getClass().getResource("/FXMLFiles/FXMLAuthentificator.fxml")));
            } catch (IOException ex) {
                showInfo("No se pudo volver al login.");
            }
        }
    }

    @FXML
    private void cambiaMenuModPerfil(javafx.event.ActionEvent event) {
        cambioPestaña.set(2);
        loadProfileData();
    }

    @FXML
    private void cambiaHistorialSesion(javafx.event.ActionEvent event) {
        cambioPestaña.set(3);
        showSessionSummary();
    }

    @FXML
    private void importarActividad(javafx.event.ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("."));
        fc.getExtensionFilters().add(new ExtensionFilter("Ficheros GPX", "*.gpx"));
        File gpx = fc.showOpenDialog(zoom_slider.getScene().getWindow());
        if (gpx == null) {
            return;
        }
        Activity act = app.importActivity(gpx);
        if (act != null) {
            loadActivities();
            map_listview.getSelectionModel().select(act);
            renderActivity(act);
        } else {
            showInfo("No se pudo importar la actividad.");
        }
    }

    @FXML
    private void borrarActividadSeleccionada(javafx.event.ActionEvent event) {
        Activity sel = map_listview.getSelectionModel().getSelectedItem();
        if (sel == null) {
            return;
        }
        boolean ok = confirmAction("/FXMLFiles/FXMLDoubleCheckDelete.fxml");
        if (ok) {
            app.removeActivity(sel);
            ocultarGrafica(null);
            loadActivities();
        }
    }

    @FXML
    private void verAcumulado(javafx.event.ActionEvent event) {
        List<Activity> acts = app.getUserActivities();
        double dist = 0;
        double gain = 0;
        double loss = 0;
        Duration total = Duration.ZERO;
        for (Activity a : acts) {
            dist += a.getTotalDistance();
            gain += a.getElevationGain();
            loss += a.getElevationLoss();
            total = total.plus(a.getDuration());
        }
        showInfo(String.format("Actividades: %d\nDistancia: %.2f km\nTiempo: %s\nDesnivel+: %.0f m\nDesnivel-: %.0f m",
                acts.size(), dist / 1000.0, total, gain, loss));
    }

    @FXML
    private void borrarMapa(javafx.event.ActionEvent event) {
        MapRegion selected = mapRegionsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Selecciona un mapa de la lista para borrarlo.");
            return;
        }
        List<MapRegion> unused = app.getUnusedMapRegions();
        if (unused.stream().noneMatch(m -> m.getName().equals(selected.getName()))) {
            showInfo("Ese mapa está siendo usado por alguna actividad y no se puede borrar.");
            return;
        }
        if (app.removeMapRegion(selected)) {
            showInfo("Mapa eliminado.");
            loadMapRegions();
        } else {
            showInfo("No se pudo borrar el mapa.");
        }
    }

    private void loadActivities() {
        List<Activity> activities = app.getUserActivities();
        map_listview.getItems().setAll(activities);
        if (!activities.isEmpty()) {
            renderActivity(activities.get(0));
            map_listview.getSelectionModel().select(0);
        } else {
            renderActivity(null);
        }
    }

    private void showMapPlaceholder() {
        VBox placeholder = new VBox();
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setSpacing(18);
        placeholder.setPadding(new Insets(40));
        placeholder.setStyle("-fx-background-color: #f8fafc;");

        Label mapIcon = new Label("🗺️");
        mapIcon.setFont(Font.font("System", 64));

        Label title = new Label("Carga un mapa para empezar");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.web("#0f172a"));

        Label desc = new Label("Selecciona un mapa disponible en la lista o añade uno nuevo para visualizar tu actividad.");
        desc.setFont(Font.font("System", 14));
        desc.setTextFill(Color.web("#64748b"));
        desc.setWrapText(true);
        desc.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        desc.setMaxWidth(420);

        Button btnGoToMapManager = new Button("Ir a Gestor de Mapas");
        btnGoToMapManager.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20; -fx-cursor: hand;");
        btnGoToMapManager.setOnAction(e -> cambiarMapa(null));

        placeholder.getChildren().addAll(mapIcon, title, desc, btnGoToMapManager);

        map_scrollpane.setContent(placeholder);

        placeholder.prefWidthProperty().bind(Bindings.createDoubleBinding(
            () -> Math.max(800.0, map_scrollpane.getViewportBounds().getWidth() - 2),
            map_scrollpane.viewportBoundsProperty()
        ));
        placeholder.prefHeightProperty().bind(Bindings.createDoubleBinding(
            () -> Math.max(600.0, map_scrollpane.getViewportBounds().getHeight() - 2),
            map_scrollpane.viewportBoundsProperty()
        ));
    }

    private void renderActivity(Activity activity) {
        currentActivity = activity;
       if (activity == null) {
            ocultarGrafica(null);
            showMapPlaceholder();
            zoomGroup = null;
            mapPane = null;
            projection = null;
            populateDetailView(null);
            return;
        }
        MapRegion region = activity.getSuggestedMap();
        if (region == null) {
            region = app.findMapForActivity(activity);
        }
        if (region == null) {
            showInfo("No se encontró mapa para la actividad.");
            showMapPlaceholder();
            return;
        }
        buildMap(new File(region.getImagePath()), region);
       drawAnnotations(activity);
        centerOnActivityStart(activity);
        populateDetailView(activity);
        showElevationProfile(activity);
        
    }

    private void drawRoute(Activity activity) {
        if (projection == null || mapPane == null) {
            return;
        }
        List<TrackPoint> points = activity.getTrackPoints();
        for (int i = 1; i < points.size(); i++) {
            TrackPoint p1 = points.get(i - 1);
            TrackPoint p2 = points.get(i);
            Point2D xy1 = projection.project(p1);
            Point2D xy2 = projection.project(p2);
            Line segment = new Line(xy1.getX(), xy1.getY(), xy2.getX(), xy2.getY());
            segment.setStrokeWidth(2.5);
            segment.setStroke(colorForSpeed(p1.speedTo(p2)));
            mapPane.getChildren().add(segment);
        }
        if (activity.getStartPoint() != null) {
            Point2D start = projection.project(activity.getStartPoint());
            Circle c = new Circle(start.getX(), start.getY(), 5, Color.GREEN);
            mapPane.getChildren().add(c);
        }
        if (activity.getEndPoint() != null) {
            Point2D end = projection.project(activity.getEndPoint());
            Circle c = new Circle(end.getX(), end.getY(), 5, Color.RED);
            mapPane.getChildren().add(c);
        }
    }

    private Color colorForSpeed(double speedKmh) {
        if (Double.isNaN(speedKmh) || speedKmh <= 0) {
            return Color.GRAY;
        }
        if (speedKmh < 7) {
            return Color.DARKBLUE;
        }
        if (speedKmh < 10) {
            return Color.DODGERBLUE;
        }
        if (speedKmh < 13) {
            return Color.GOLDENROD;
        }
        return Color.CRIMSON;
    }

    private void drawAnnotations(Activity activity) {
        for (Annotation ann : activity.getAnnotations()) {
            List<GeoPoint> geoPoints = ann.getGeoPoints();
            if (geoPoints.isEmpty()) {
                continue;
            }
            Color color = Color.web(ann.getColor() == null ? "#E74C3C" : ann.getColor());
            if (ann.getType() == AnnotationType.POINT) {
                Point2D p = projection.project(geoPoints.get(0));
                Circle c = new Circle(p.getX(), p.getY(), 6, color);
                mapPane.getChildren().add(c);
                if (ann.getText() != null && !ann.getText().isBlank()) {
                    Text t = new Text(p.getX() + 8, p.getY() - 8, ann.getText());
                    mapPane.getChildren().add(t);
                }
            } else if (ann.getType() == AnnotationType.TEXT) {
                Point2D p = projection.project(geoPoints.get(0));
                Text t = new Text(p.getX(), p.getY(), ann.getText());
                t.setFill(color);
                mapPane.getChildren().add(t);
            } else if (ann.getType() == AnnotationType.LINE && geoPoints.size() >= 2) {
                Point2D p1 = projection.project(geoPoints.get(0));
                Point2D p2 = projection.project(geoPoints.get(1));
                Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                line.setStroke(color);
                line.setStrokeWidth(Math.max(1.0, ann.getStrokeWidth()));
                mapPane.getChildren().add(line);
            } else if (ann.getType() == AnnotationType.CIRCLE && geoPoints.size() >= 2) {
                Point2D c = projection.project(geoPoints.get(0));
                Point2D b = projection.project(geoPoints.get(1));
                Circle circle = new Circle(c.getX(), c.getY(), c.distance(b));
                circle.setStroke(color);
                circle.setFill(Color.TRANSPARENT);
                circle.setStrokeWidth(Math.max(1.0, ann.getStrokeWidth()));
                mapPane.getChildren().add(circle);
            }
        }
    }

    private void centerOnActivityStart(Activity activity) {
        TrackPoint start = activity.getStartPoint();
        if (start == null || mapPane == null || zoomGroup == null) {
            return;
        }
        Point2D startPoint = projection.project(start);
        double mapWidth = mapPane.getWidth() * zoomGroup.getScaleX();
        double mapHeight = mapPane.getHeight() * zoomGroup.getScaleY();
        double poiX = startPoint.getX() * zoomGroup.getScaleX();
        double poiY = startPoint.getY() * zoomGroup.getScaleY();
        double viewW = map_scrollpane.getViewportBounds().getWidth();
        double viewH = map_scrollpane.getViewportBounds().getHeight();
        double scrollH = (poiX - viewW / 2) / Math.max(1.0, (mapWidth - viewW));
        double scrollV = (poiY - viewH / 2) / Math.max(1.0, (mapHeight - viewH));
        scrollH = Math.max(0, Math.min(1, scrollH));
        scrollV = Math.max(0, Math.min(1, scrollV));
        Timeline timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.millis(300),
                        new KeyValue(map_scrollpane.hvalueProperty(), scrollH),
                        new KeyValue(map_scrollpane.vvalueProperty(), scrollV))
        );
        timeline.play();
    }

    private void addAnnotationFromClick(AnnotationType type, double x, double y) {
        GeoPoint point = projection.unproject(x, y);
        Dialog<AnnotationDraft> dialog = new Dialog<>();
        dialog.setTitle("Nueva anotación");
        ButtonType okButton = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Texto (opcional)");
        ColorPicker colorPicker = new ColorPicker(Color.CRIMSON);
        dialog.getDialogPane().setContent(new VBox(8, new Label("Texto"), textField, new Label("Color"), colorPicker));
        dialog.setResultConverter(bt -> bt == okButton ? new AnnotationDraft(textField.getText(), toHex(colorPicker.getValue())) : null);
        Optional<AnnotationDraft> result = dialog.showAndWait();
        result.ifPresent(draft -> saveAnnotation(type, draft.text(), draft.color(), List.of(point)));
    }

    private void startTwoPointAnnotation(AnnotationType type, double x, double y) {
        Dialog<AnnotationDraft> dialog = new Dialog<>();
        dialog.setTitle("Nueva anotación");
        ButtonType okButton = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        TextField textField = new TextField();
        textField.setPromptText("Texto (opcional)");
        ColorPicker colorPicker = new ColorPicker(Color.CRIMSON);
        dialog.getDialogPane().setContent(new VBox(8, new Label("Texto"), textField, new Label("Color"), colorPicker));
        dialog.setResultConverter(bt -> bt == okButton ? new AnnotationDraft(textField.getText(), toHex(colorPicker.getValue())) : null);
        Optional<AnnotationDraft> draft = dialog.showAndWait();
        if (draft.isEmpty()) {
            return;
        }
        pendingType = type;
        pendingFirstPoint = projection.unproject(x, y);
        pendingText = draft.get().text();
        pendingColor = draft.get().color();
        mapPane.setStyle("-fx-cursor: crosshair;");
        showInfo("Haz clic izquierdo para marcar el segundo punto de la anotación.");
    }

    private void saveAnnotation(AnnotationType type, String text, String color, List<GeoPoint> points) {
        if (currentActivity == null) {
            return;
        }
        Annotation ann = new Annotation(type, text, color, 2.0, points);
        Annotation saved = app.addAnnotation(currentActivity, ann);
        if (saved != null) {
            renderActivity(currentActivity);
        }
    }

    private void loadProfileData() {
        User current = app.getCurrentUser();
        if (current == null) {
            return;
        }
        txtEmail.setText(current.getEmail());
        dpFecha.setValue(current.getBirthDate());
        txtPassword.clear();
        avatarPath = current.getAvatarPath();

        Image avatar = current.getAvatar();
        if (avatar != null) {
            if (imgAvatarMod != null) imgAvatarMod.setImage(avatar);
            if (imgAvatarTop != null) imgAvatarTop.setImage(avatar);
        } else {
            Image defaultImg = new Image(getClass().getResourceAsStream("/resources/logoDef.png"));
            if (imgAvatarMod != null) imgAvatarMod.setImage(defaultImg);
            if (imgAvatarTop != null) imgAvatarTop.setImage(defaultImg);
        }

        if (lblErrEmailMod != null) lblErrEmailMod.setVisible(false);
        if (lblErrPassMod != null) lblErrPassMod.setVisible(false);
        if (lblErrDateMod != null) lblErrDateMod.setVisible(false);
        if (txtEmail != null) txtEmail.setStyle("");
        if (txtPassword != null) txtPassword.setStyle("");
        if (dpFecha != null) dpFecha.setStyle("");
    }

    private String formatDuration(Duration duration) {
        if (duration == null) {
            return "0s";
        }
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        if (hours > 0) {
            return String.format("%dh %dm", hours, minutes);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    private void showSessionSummary() {
        User current = app.getCurrentUser();
        if (current == null) {
            return;
        }
        List<Session> sessions = app.getSessionsByUser(current);
        int imported = 0;
        int viewed = 0;
        int annotations = 0;
        Duration total = Duration.ZERO;
        for (Session s : sessions) {
            imported += s.getImportedActivities();
            viewed += s.getViewedActivities();
            annotations += s.getAnnotationsCreated();
            total = total.plus(s.getDuration());
        }
        if (lblTotalSessions != null) {
            lblTotalSessions.setText(String.valueOf(sessions.size()));
        }
        if (lblTotalTime != null) {
            lblTotalTime.setText(formatDuration(total));
        }
        if (vboxRecentSessions != null) {
            vboxRecentSessions.getChildren().clear();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
            for (int i = sessions.size() - 1; i >= 0; i--) {
                Session s = sessions.get(i);
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setSpacing(20.0);
                hbox.setPadding(new Insets(15, 20, 15, 20));
                hbox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 10, 0, 0, 4);");

                Label icon = new Label("📅");
                icon.setFont(new Font(28.0));

                VBox vboxLeft = new VBox();
                HBox.setHgrow(vboxLeft, Priority.ALWAYS);
                Label lblDate = new Label(s.getStartTime() != null ? s.getStartTime().format(formatter) : "Fecha desconocida");
                lblDate.setTextFill(Color.web("#0f172a"));
                lblDate.setFont(Font.font("System", FontWeight.BOLD, 16.0));
                Label lblDuration = new Label("Duración: " + formatDuration(s.getDuration()));
                lblDuration.setTextFill(Color.web("#64748b"));
                lblDuration.setFont(new Font(13.0));
                vboxLeft.getChildren().addAll(lblDate, lblDuration);

                VBox vboxRight = new VBox();
                vboxRight.setAlignment(Pos.CENTER_RIGHT);
                Label lblImported = new Label(s.getImportedActivities() + " Rutas");
                lblImported.setTextFill(Color.web("#2196F3"));
                lblImported.setFont(Font.font("System", FontWeight.BOLD, 14.0));
                Label lblAnnotations = new Label(s.getAnnotationsCreated() + " Anotaciones");
                lblAnnotations.setTextFill(Color.web("#64748b"));
                lblAnnotations.setFont(new Font(13.0));
                vboxRight.getChildren().addAll(lblImported, lblAnnotations);

                hbox.getChildren().addAll(icon, vboxLeft, vboxRight);
                vboxRecentSessions.getChildren().add(hbox);
            }
        }
    }

    private void populateDetailView(Activity activity) {
        if (activity == null) {
            detailView.setVisible(false);
            masterView.setVisible(true);
            return;
        }
        lblDetalleNombre.setText("Actividad: " + activity.getName());
        lblDetalleDistancia.setText(String.format("Distancia: %.2f km", activity.getTotalDistance() / 1000.0));
        lblDetalleDuracion.setText("Tiempo: " + formatDuration(activity.getDuration()));
        lblDetalleDesnivel.setText(String.format("Desnivel: +%.0f m / -%.0f m", activity.getElevationGain(), activity.getElevationLoss()));
        lblDetalleVelocidad.setText(String.format("Velocidad: %.2f km/h", activity.getAverageSpeed()));
        lblDetalleRitmo.setText(String.format("Ritmo: %.2f min/km", activity.getAveragePace()));
        lblDetalleAltitud.setText(String.format("Altitud mín/máx: %.0f / %.0f m", activity.getMinElevation(), activity.getMaxElevation()));

        masterView.setVisible(false);
        detailView.setVisible(true);
    }

    @FXML
    private void volverAlMaster(javafx.event.ActionEvent event) {
        detailView.setVisible(false);
        masterView.setVisible(true);
    }

    private void loadMapRegions() {
        mapRegionsList.getItems().setAll(app.getMapRegions());
    }

    private void showElevationProfile(Activity activity) {
        if (activity == null || activity.getTrackPoints().isEmpty()) {
            return;
        }
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Distancia (km)");
        yAxis.setLabel("Altitud (m)");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        chart.setTitle("Perfil de desnivel - " + activity.getName());

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        List<TrackPoint> points = activity.getTrackPoints();
        double accMeters = 0;
        for (int i = 0; i < points.size(); i++) {
            if (i > 0) {
                accMeters += points.get(i - 1).distanceTo(points.get(i));
            }
            series.getData().add(new XYChart.Data<>(accMeters / 1000.0, points.get(i).getElevation()));
        }
        chart.getData().add(series);

        if (hoverPointMarker != null && mapPane != null) {
            mapPane.getChildren().remove(hoverPointMarker);
        }
        hoverPointMarker = new Circle(6, Color.ORANGE);
        hoverPointMarker.setVisible(false);
        if (mapPane != null) {
            mapPane.getChildren().add(hoverPointMarker);
        }

        chart.setOnMouseMoved(e -> {
            if (projection == null || mapPane == null) {
                return;
            }
            Point2D axisPoint = xAxis.sceneToLocal(e.getSceneX(), e.getSceneY());
            double xValue = xAxis.getValueForDisplay(axisPoint.getX()).doubleValue();
            int idx = nearestTrackPointIndex(points, xValue);
            if (idx < 0) {
                return;
            }
            Point2D projected = projection.project(points.get(idx));
            hoverPointMarker.setCenterX(projected.getX());
            hoverPointMarker.setCenterY(projected.getY());
            hoverPointMarker.setVisible(true);
        });
        chart.setOnMouseExited(e -> {
            if (hoverPointMarker != null) {
                hoverPointMarker.setVisible(false);
            }
        });

        if (vboxElevationProfile.getChildren().size() > 1) {
            vboxElevationProfile.getChildren().remove(1, vboxElevationProfile.getChildren().size());
        }
        VBox.setVgrow(chart, Priority.ALWAYS);
        vboxElevationProfile.getChildren().add(chart);

        if (mapAndChartSplitPane != null && !mapAndChartSplitPane.getItems().contains(vboxElevationProfile)) {
            mapAndChartSplitPane.getItems().add(vboxElevationProfile);
            mapAndChartSplitPane.setDividerPositions(0.7);
        }
    }

    @FXML
    private void ocultarGrafica(javafx.event.ActionEvent event) {
        if (mapAndChartSplitPane != null && vboxElevationProfile != null) {
            mapAndChartSplitPane.getItems().remove(vboxElevationProfile);
        }
        if (hoverPointMarker != null && mapPane != null) {
            mapPane.getChildren().remove(hoverPointMarker);
            hoverPointMarker = null;
        }
    }

    private int nearestTrackPointIndex(List<TrackPoint> points, double km) {
        double accMeters = 0;
        int bestIndex = -1;
        double bestDist = Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            if (i > 0) {
                accMeters += points.get(i - 1).distanceTo(points.get(i));
            }
            double d = Math.abs((accMeters / 1000.0) - km);
            if (d < bestDist) {
                bestDist = d;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    private String toHex(Color c) {
        int r = (int) Math.round(c.getRed() * 255);
        int g = (int) Math.round(c.getGreen() * 255);
        int b = (int) Math.round(c.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    private static class AnnotationDraft {
        private final String text;
        private final String color;

        AnnotationDraft(String text, String color) {
            this.text = text;
            this.color = color;
        }

        String text() {
            return text;
        }

        String color() {
            return color;
        }
    }

    private boolean isDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}