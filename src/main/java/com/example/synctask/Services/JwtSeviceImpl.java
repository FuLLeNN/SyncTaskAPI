package com.example.synctask.Services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class JwtSeviceImpl implements JwtSevice {
    @Override
    public Long getIdByJWT(String jwt) {
        Long id = null;
        String token = jwt.replace("Bearer ", "");
        String apiUrl = "http://localhost:5041/Auth/verifyToken?jwtToken=" + token;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = in.readLine();
                id = Long.parseLong(response);
                in.close();
            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}