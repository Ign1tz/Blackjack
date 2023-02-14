package com.example.testing;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CreateAccount {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public void writeAccount(String email, String name, String password, double money) throws IOException {
        Account account = new Account(email, name, password, money);
        List<Account> accountList = readAccount();
        accountList.add(account);
        Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/com/example/testing/Game/Accounts.json"));
        gson.toJson(accountList, writer);
        writer.close();
    }
    public void changeMoney() throws IOException {
        List<Account> accounts = readAccount();
        Account account = new Account(accounts.get(Vars.currentAccount).EMAIL, accounts.get(Vars.currentAccount).NAME, accounts.get(Vars.currentAccount).PASSWORD, Vars.money);
        accounts.remove(Vars.currentAccount);
        accounts.add(Vars.currentAccount, account);
        Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/com/example/testing/Game/Accounts.json"));
        gson.toJson(accounts, writer);
        writer.close();

    }
    public List<Account> readAccount() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/com/example/testing/Game/Accounts.json"));
         return new Gson().fromJson(reader, new TypeToken<List<Account>>() {}.getType());
    }
    public static void main(String[] args) throws IOException {
        CreateAccount ca = new CreateAccount();
        ca.writeAccount("test@gmx.at", "tester", "test", 100);
        System.out.println(ca.readAccount());
    }
}
