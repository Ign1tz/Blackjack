package com.example.testing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RegisterController {
    CreateAccount cA = new CreateAccount();
    private List<Account> accounts = cA.readAccount();
    @FXML
    private TextField email, name, password, repeatPassword;

    public RegisterController() throws IOException {
    }

    public void Return(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);

    }

    public void Finish(ActionEvent event) throws IOException {
        boolean exists = false;
        for(int i = 0; i < accounts.size(); i++){
            if(Objects.equals(accounts.get(i).NAME, name.getText()) || Objects.equals(accounts.get(i).EMAIL, email.getText()) || !Objects.equals(password.getText(), repeatPassword.getText())) {
                exists = true;
            }
            if(email.getText().contains(" ") || !email.getText().contains("@") || !email.getText().contains(".")){
                exists = true;
            }
        }
        if(!exists) {
            cA.writeAccount(email.getText(), name.getText(), password.getText(), 2000);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        }
    }
}
