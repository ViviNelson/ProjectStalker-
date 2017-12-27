package com.appspot.blacktenure188118.system;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

public class Network {

    private static final String CONTENT_TYPE = "application/json";

    private static final int timeout = 50000;

    // HTTP GET request
    public static String sendGet(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con;
        con = (HttpURLConnection) obj.openConnection();


        // optional default is GET
        con.setRequestMethod("GET");
        con.setReadTimeout(timeout);
        con.setConnectTimeout(timeout);

        //add request header
        con.setRequestProperty("Content-Type", CONTENT_TYPE);

        int responseCode = con.getResponseCode();

        if(responseCode!= HttpURLConnection.HTTP_OK){
            throw new IOException(String.format("Not ok %d",responseCode));
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    // HTTP POST request
    public static String sendPost(String url, String data) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con;
        con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setReadTimeout(timeout);
        con.setConnectTimeout(timeout);

        con.setRequestProperty("Content-Type", CONTENT_TYPE);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(data.getBytes(Charset.forName("UTF-8")));
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        if(responseCode!= HttpURLConnection.HTTP_OK){

            System.out.println(url);
            System.out.println(data);

            throw new IOException(String.format("Not ok %d",responseCode));
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

}
