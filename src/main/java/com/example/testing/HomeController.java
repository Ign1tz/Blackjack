package com.example.testing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {
    @FXML
    TextField name;
    @FXML
    PasswordField password;
    CreateAccount cA = new CreateAccount();
    private List<Account> accounts = cA.readAccount();

    public HomeController() throws IOException {
    }

    public void login(ActionEvent event) throws IOException {
        for(int i = 0; i < accounts.size(); i++){
            if(Objects.equals(accounts.get(i).NAME, name.getText()) && Objects.equals(accounts.get(i).PASSWORD, password.getText())){
                Vars.name = name.getText();
                Vars.money = accounts.get(i).MONEY;
                Vars.currentAccount = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
                Parent root = loader.load();
                Scene scene = ((Node) event.getSource()).getScene();
                scene.setRoot(root);
            }
        }
    }

    public void register(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    public void quit(ActionEvent event) {
        System.exit(0);
    }
}
