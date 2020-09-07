package com.arvonit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public final class Controller {

    @FXML
    private Label output;
    private String prevOutput, input;
    private Model.Operation operation;
    private double firstNumber;
    private boolean needToClear = false;

    private final Model model = new Model();
    private final List<String> numpadInputs = List.of(
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", "."
    );
    private final List<String> operationInputs = List.of(
            "/",
            "*",
            "-",
            "+",
            "="
    );

    @FXML
    private void handleNumpad(ActionEvent event) {
        prevOutput = output.getText();
        input = ((Button) event.getSource()).getText();

        if (prevOutput.equals("0") && !input.equals(".")) {
            output.setText(input);
        } else if (needToClear) {
            output.setText(input);
            needToClear = false;
        } else {
            output.setText(prevOutput + input);
        }
    }

    @FXML
    private void handleSignChange(ActionEvent event) {
        prevOutput = output.getText();

        if (prevOutput.startsWith("-")) {
            output.setText(prevOutput.substring(1));
        } else {
            output.setText("-" + prevOutput);
        }
    }

    @FXML
    private void handlePercent(ActionEvent event) {
        prevOutput = output.getText();
        double percent = Double.parseDouble(prevOutput) / 100.0;

        output.setText(String.valueOf(percent));
        needToClear = true;
    }

    @FXML
    private void handleClear(ActionEvent event) {
        output.setText("0");
        needToClear = false;
    }

    @FXML
    private void handleOperation(ActionEvent event) {
        prevOutput = output.getText();
        input = ((Button) event.getSource()).getText();

        if (!input.equals("=")) {
            // Store first number
            firstNumber = Double.parseDouble(prevOutput);
            operation = Model.Operation.getFromSymbol(input);
            // output.setText("0");
        } else {
            // Calculate
            double secondNumber = Double.parseDouble(prevOutput);

            double calculation = model.calculate(firstNumber, secondNumber, operation);
            String calculationString = String.valueOf(calculation);

            // Chop off .0 from floating-point calculation
            if (calculationString.endsWith(".0")) {
                calculationString = calculationString.substring(0, calculationString.indexOf("."));
            }

            // Catch division-by-zero error
            if (calculationString.equals("Infinity")) {
                calculationString = "NaN";
            }

            output.setText(calculationString);
        }
        needToClear = true;
    }

    private void handleBackspace() {
        prevOutput = output.getText();
        if (!prevOutput.equals("0")) {
            String backspacedOutput = prevOutput.substring(0, prevOutput.length() - 1);
            if (backspacedOutput.equals("")) {
                backspacedOutput = "0";
            }
            output.setText(backspacedOutput);
        }
    }

    public void handleKeyboardInput(KeyEvent event) {
        input = event.getText().toLowerCase();
        Button button;

        if (numpadInputs.contains(input)) {
            button = new Button(input);
            handleNumpad(new ActionEvent(button, button));
        } else if (operationInputs.contains(input)) {
            button = new Button(input);
            handleOperation(new ActionEvent(button, button));
        } else if (input.equals("c")) {
            button = new Button(input);
            handleClear(new ActionEvent(button, button));
        } else if (input.equals("%")) {
            button = new Button(input);
            handlePercent(new ActionEvent(button, button));
        }

        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.BACK_SPACE) {
            handleBackspace();
        } else if (keyCode == KeyCode.ENTER) {
            button = new Button("=");
            handleOperation(new ActionEvent(button, button));
        }
    }

}
