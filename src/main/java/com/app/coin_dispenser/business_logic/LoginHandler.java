package com.app.coin_dispenser.business_logic;

import com.app.coin_dispenser.domain.Login;

import java.io.*;
import java.net.URL;

import static com.app.coin_dispenser.server.Server.CONTENT_DELIMITER;

public class LoginHandler {

    public boolean verifyLoginRequest(Login login) throws IOException {

        URL url = ClassLoader.getSystemResource("users.txt");

        File file = new File(url.getFile());

        FileReader reader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;

        while ((line = bufferedReader.readLine()) != null) {

            String username = line.split(CONTENT_DELIMITER)[0];
            String password = line.split(CONTENT_DELIMITER)[1];

            if (username.equals(login.getUsername()) && password.equals(login.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
