package com.marbl.messaging;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class JMSSettings {

    public static final String CONNECTION = "CONNECTION";
    public static final String CLIENT_REQUEST = "CLIENT_REQUEST";
    public static final String CLIENT_REPLY = "CLIENT_REPLY";
    public static final String CLIENT_REPLY_2 = "CLIENT_REPLY_2";
    public static final String PARAFIKSIT_REQUEST = "PARAFIKSIT_REQUEST";
    public static final String PARAFIKSIT_REPLY = "PARAFIKSIT_REPLY";
    public static final String WAREHOUSE_REQUEST = "WAREHOUSE_REQUEST";
    public static final String WAREHOUSE_REPLY = "WAREHOUSE_REPLY";
    private HashMap<String, String> map;

    public JMSSettings(String fileName) {
        File file = new File(fileName);
        map = new HashMap<>();
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().replaceAll(" ", "");
                StringTokenizer tk = new StringTokenizer(line, "=");
                String key = tk.nextToken();
                String value = tk.nextToken();
                System.out.println(key + "=" + value);
                map.put(key, value);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JMSSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String get(String queue) {
        return map.get(queue);
    }
}
