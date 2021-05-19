package com.chatbot;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

@ServerEndpoint("/chatbot")
public class ChatBotController {

    @OnOpen
    public void handleOpen() {
        System.out.println("Connected to ChatBot");
        System.out.println("Hello My Name is WeatherWizard");
        System.out.println("Tell me city names and I will tell you weather");
        System.out.println("or type ALL and you will see everything");
        System.out.println("I am new here, so i Only Know 3 Cities yet");
        System.out.println("Ask about Tbilis,Kutaisi,Batumi,Zugdidi");
    }

    @OnMessage
    public String handleMessage(String message) throws Exception {
        String requestText = message.toLowerCase(Locale.ROOT);
        String response;
        if (requestText.equals("all")){
            response = get("");
        }else{
            response = get(requestText);
        }
        return response;
    }

    @OnClose
    public void handleClose() {
        System.out.println("Bye Bye :)");
    }

    @OnError
    public void handleError(Throwable t) {
        t.printStackTrace();
    }

    public static String get(String path) throws Exception {
        URL url = new URL("http://localhost:8080/nodartopuriaweatherbtu-1.0-SNAPSHOT/api/weather"+"/"+path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        String output;
        StringBuilder data = new StringBuilder();
        System.out.println("Output from Server :  \n");
        while ((output = bufferedReader.readLine()) != null) {
            data.append(output);
            System.out.println(output);
        }
        conn.disconnect();
        return data.toString();
    }

}
