package com.skinnybonesarv.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public final class Calculator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Calculator.fxml")));
        Parent root = loader.load();
        Controller controller = loader.getController();

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(controller::handleKeyboardInput);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        // primaryStage.setTitle("Calculator");
        primaryStage.show();
    }

}
