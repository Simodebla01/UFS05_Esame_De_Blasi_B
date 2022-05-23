package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientHandler {
    private Socket clientSocket;
    private static BufferedReader in = null;
    private static PrintWriter out = null;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void handle() {
        in = allocateReader(clientSocket);
        out = allocateWriter(clientSocket);
        buildProductList();
        handleInput();
    }

    private PrintWriter allocateWriter(Socket clientSocket) {
    }

    private BufferedReader allocateReader(Socket clientSocket) {
    }

    public void handleInput() {
        String userInput;
        try {
            while ((userInput = in.readLine()) != null) {
                System.out.println("Client: " + (userInput));
                out.println(process(userInput));
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    static ArrayList<Wine> products = new ArrayList<>();
    static void buildProductList() {
        products.add(new Wine(13,"Don Perignon Vintage Moet & Chandon 2008",225.94, "white"));
        products.add(new Wine(14,"Pignoli Radikon Radikon 2009",133.94, "red"));
        products.add(new Wine(15,"Pinto Nero elena Walch Elena Walch 20018",43.94, "red"));
    }



    public String process(String input) {

        Gson gson = new Gson();

        String intro="";
        String result="";
        switch(input){
            case "red":
                //intro = "The red wines: ";
                result=redManagement(gson);
                break;
            case "white":
                // intro = "The white wines: ";
                result=whiteManagement(gson);

                break;
            
            case "sorted_by_name":
                // intro = "List of all the products: ";

                result = sorted_by_name(gson);
                break;

            case "sorted_by_price":
                //  intro = "List of all the products sorted from cheapest: ";


                result = sorted_by_price(gson);
                break;
            default:
                result="Insert: 'sorted_by_name' -> ordina per nome | 'white' -> lista dei vini bianchi |'red' -> lista dei vini rossi | 'sorted_by_price' -> lista dei vini ordinati per prezzo";
        }
        return result;


    }

    public String redManagement(Gson gson){
        ArrayList<Wine> red = new ArrayList<>();
        red(red);
        String result=gson.toJson(red);
        return result;
    }
    public String whiteManagement(Gson gson){
        ArrayList<Wine> white = new ArrayList<>();
        white(white);
        String result=gson.toJson(white);
        return result;
    }
    

    public String sorted_by_price(Gson gson){
        cheapest();
        String result = gson.toJson(Wine);
        return result;
    }

    public void cheapest(){
        Wine.add((Wine o1, Wine o2) -> {
            return o1.price.compareTo(o2.price);
        });
    }

    public String sorted_by_name(Gson gson){
        sort();
        String result = gson.toJson(products);
        return result;

    }

    public void sort(){
        products.sort((Wine o1,Wine o2)->{return o1.getName().compareTo(o2.getName());});
    }

    public ArrayList red(ArrayList red) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).type == "red") {
                red.add(products.get(i));
            }
        }
        return red;
    }

    public ArrayList white(ArrayList white) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).type == "white") {
                white.add(products.get(i));
            }
        }
        return white;
    }

    


