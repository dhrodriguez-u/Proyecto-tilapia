package Proyect.adapters.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) {

    MainView mainView = new MainView(stage);

    mainView.iniciar();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
