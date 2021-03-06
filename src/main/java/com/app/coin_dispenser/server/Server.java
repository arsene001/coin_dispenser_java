package com.app.coin_dispenser.server;

import com.app.coin_dispenser.business_logic.LoginHandler;
import com.app.coin_dispenser.business_logic.NoteHandler;
import com.app.coin_dispenser.domain.Command;
import com.app.coin_dispenser.domain.Login;
import com.app.coin_dispenser.domain.Note;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String COMMAND_DELIMITER = "#";
    public static final String CONTENT_DELIMITER = ":";
    private ServerSocket serverSocket;

    public static class ServerHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;
        private LoginHandler loginHandler;
        private NoteHandler noteHandler;

        public ServerHandler(Socket socket) {
            this.clientSocket = socket;
            this.loginHandler = new LoginHandler();
            this.noteHandler = new NoteHandler();
        }

        public void run() {
            try {

                writer = new PrintWriter(clientSocket.getOutputStream(), true); //OUT
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //IN

                String input;

                while ((input = reader.readLine()) != null) {

                    String[] instruction = input.split(COMMAND_DELIMITER);

                    String command = instruction[0];

                    if (command.equals(Command.LOGIN.name())) {
                        //Deal with login interface
                        String username = instruction[1].split(CONTENT_DELIMITER)[1];
                        String password = instruction[2].split(CONTENT_DELIMITER)[1];

                        Login login = new Login(username, password);

                        boolean success = loginHandler.verifyLoginRequest(login);
                        writer.println(success);

                    } else if (command.equals(Command.CALCULATION.name())) {
                        //Deal with calculation
                        BigDecimal amountDue = new BigDecimal(instruction[1].split(CONTENT_DELIMITER)[1]);
                        BigDecimal amountCaptured = new BigDecimal(instruction[2].split(CONTENT_DELIMITER)[1]);

                        Note note = new Note(amountDue, amountCaptured);

                        String results = noteHandler.calculateDenominationBreakDown(note);

                        writer.println(results);
                    } else {
                        throw new RuntimeException("Invalid command sent to server.");
                    }
                }

                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void start(int port) {

        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                new ServerHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
