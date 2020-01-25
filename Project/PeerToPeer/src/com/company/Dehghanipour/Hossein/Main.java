package com.company.Dehghanipour.Hossein;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
        import javafx.application.Platform;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;
        import javafx.scene.control.Separator;
        import javafx.geometry.Orientation;

//from w  w w  .  j a v a  2s  . c om
public class Main extends Application {
    public static Node newNode = null;
    public static Label logs = new Label();

    public static void main(String[] args)  throws IOException {
        Application.launch(args);


    }

    @Override
    public void start(Stage stage) {
        Label username = new Label("Enter your username:");
        Label port = new Label("Enter your port:");
        Label followings = new Label("Enter those who you want trust to:(space separated host:port) ");
        Label query = new Label("Search:");
        Label brodcast = new Label("Broadcast:");
        TextField usernameField = new TextField();
        TextField portField = new TextField();
        TextField followingsField = new TextField();
        TextField queryField = new TextField();
        TextField brodcastField = new TextField();

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: blue;");


        Button setUsernameAndPortBtn = new Button("Set username and port!");
        Button setFollowingsBtn = new Button("Trust to them!");
        Button setQueryBtn = new Button("Query!");
        Button setBrodcastBtn = new Button("Broadcast!");
        Label logsLabel = new Label("Logs:");

        Button exitBtn = new Button("Exit");

        setUsernameAndPortBtn.setOnAction(e -> {
            String usernameFieldText = usernameField.getText();
            String portFieldText = portField.getText();

            try {
                newNode = Node.createNode(usernameFieldText.toString(),portFieldText.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        setFollowingsBtn.setOnAction(e -> {
            String followingsFieldText = followingsField.getText();


            try {
                newNode.updateFollowings(followingsFieldText);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });


        setBrodcastBtn.setOnAction(e -> {
            String brodcastFieldText = brodcastField.getText();
            newNode.communicate(brodcastFieldText,newNode.getUsername());


        });

        setQueryBtn.setOnAction(e -> {
            String queryFieldText = queryField.getText();
            ArrayList<Message>msgs =ListenerThread.query(queryFieldText);
            String s = "";
            for(Message m : msgs){
                s += m.getMessage() + "\n";
            }
            logs.setText(logs.getText() + '\n' + s);

        });

        exitBtn.setOnAction(e -> Platform.exit());

        VBox root = new VBox();

        root.setSpacing(5);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        Separator separator3 = new Separator(Orientation.HORIZONTAL);
        Separator separator4 = new Separator(Orientation.HORIZONTAL);
        root.getChildren().addAll(username, usernameField , port , portField, msg, setUsernameAndPortBtn,separator,followings , followingsField , setFollowingsBtn, separator2 , query , queryField , setQueryBtn ,separator3 ,brodcast , brodcastField , setBrodcastBtn, separator4

                ,logsLabel , logs , exitBtn);

        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.show();
    }
}