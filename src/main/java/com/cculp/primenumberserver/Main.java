package com.cculp.primenumberserver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Client operations on server
        final int EXIT_SERVER = 1337;

        // Scene setup
        TextArea ta = new TextArea();
        ScrollPane scrollPane = new ScrollPane(ta);
        Scene scene = new Scene(scrollPane, 450, 200);
        stage.setTitle("Prime Number Server");
        stage.setScene(scene);
        stage.show();

        // Socket thread and server loop.
        Thread t = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(1337);
                Socket socket = serverSocket.accept();
                Platform.runLater(() -> {
                    ta.appendText("Server started at " + new Date() + '\n');
                });

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    int numberToCheck = inputStream.readInt();
                    // check to see if input is exit_code
                    if (numberToCheck == EXIT_SERVER) {
                        socket.close();
                        Platform.runLater(() -> {
                            ta.appendText("Server closed at " + new Date() + '\n');
                        });
                        break;
                    } else {
                        boolean userNumberIsPrime = PrimeNumbers.isPrime(numberToCheck);
                        outputStream.writeBoolean(userNumberIsPrime);
                        Platform.runLater(() -> {
                            ta.appendText("Client sent " + numberToCheck + " at " + new Date() + '\n');
                        });
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
