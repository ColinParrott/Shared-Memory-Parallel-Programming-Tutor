package com.colinparrott.parallelsimulator.engine.simulator.gui;

import com.colinparrott.parallelsimulator.compiler.MultithreadedParser;
import com.colinparrott.parallelsimulator.compiler.MultithreadedParserResult;
import com.colinparrott.parallelsimulator.compiler.ProgramJsonProducer;
import com.colinparrott.parallelsimulator.compiler.SingleProgramCompiler;
import com.colinparrott.parallelsimulator.compiler.errorhandlers.CompilationError;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class CompilerGUIController implements Initializable
{

    @FXML
    public AnchorPane programAdderAnchor;

    @FXML
    public TextArea codeBox;

    @FXML
    private Label compilerErrorMessage;

    @FXML
    private JFXButton btnAddProgram;


    @FXML
    private JFXTextField compilerProgramTitle;

    private Stage programAdderWindow;

    private MultithreadedParser parser;
    private SingleProgramCompiler compiler;
    private Stack<Integer> lineSizes;

    void showWindow()
    {
        Parent root;
        try
        {
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/program_adder.fxml")));
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/program_adder.fxml"));
            root = loader.load();
            programAdderWindow = new Stage();
            programAdderWindow.setTitle("Add program");
            programAdderWindow.setScene(new Scene(root, 640, 700));
            programAdderWindow.setResizable(false);
            programAdderWindow.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    private void setListeners()
    {
        setCodeBoxListener(codeBox);

        btnAddProgram.setOnAction(event -> {
//            System.out.println(compilerProgramTitle.getText().length());
            if (compilerProgramTitle.getText().length() == 0)
            {
                JFXSnackbar snackbar = new JFXSnackbar(programAdderAnchor);
                Node snackbarContent = snackbar.getChildren().get(0);
                snackbarContent.setStyle("-fx-background-color: red");
                snackbar.show("Please set a program title", 2000);
            }
            else
            {
                compileProgram();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void compileProgram()
    {
        MultithreadedParserResult parseResult = parser.parseProgram(codeBox.getText());

        if (parseResult.hasErrors())
        {
            JFXSnackbar snackbar = new JFXSnackbar(programAdderAnchor);
            Node snackbarContent = snackbar.getChildren().get(0);
            snackbarContent.setStyle("-fx-background-color: red");
            snackbar.show("Error compiling program", 2000);
            return;
        }

        String[][] assemblyLines = new String[parseResult.getThreadCode().size()][];

        int i = 0;
        for (String prog : parseResult.getThreadCode())
        {
            String[] singleProgLines = compiler.compileProgram(prog).getAssemblyLines().toArray(new String[0]);
            assemblyLines[i] = singleProgLines;
            i++;
        }

        ProgramJsonProducer.produceJsonFile(compilerProgramTitle.getText(), codeBox.getText().split("\n"), assemblyLines);
    }

    private void setCodeBoxListener(TextArea codeBox)
    {
        codeBox.setOnKeyReleased(event -> checkForErrors(codeBox.getText()));
    }


    private void checkForErrors(String code)
    {
        MultithreadedParserResult parserResult = parser.parseProgram(code);

        if (parserResult.hasErrors())
        {
            CompilationError error = parserResult.getErrors().get(0);
            compilerErrorMessage.setVisible(true);
            compilerErrorMessage.setText(formatErrorMessage(error.getErrorMessage(), error.getLine(), error.getCharacter()));
            btnAddProgram.setVisible(false);
        }
        else
        {
//            System.out.println(compilerProgramTitle.getText());
            compilerErrorMessage.setText("");
            compilerErrorMessage.setVisible(false);
            btnAddProgram.setVisible(true);
        }
    }

    private String formatErrorMessage(String error, int line, int charIndex)
    {

        if (line >= 0 && charIndex >= 0)
        {
            String errorTemplate = "%s (%d:%d)";
            return String.format(errorTemplate, error, line, charIndex + 1);
        }
        else
        {
            return error;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Hide add program button
        btnAddProgram.setVisible(false);
        compilerErrorMessage.setText("");
        compilerErrorMessage.setVisible(false);
        setListeners();
        parser = new MultithreadedParser();
        compiler = new SingleProgramCompiler();


    }
}
