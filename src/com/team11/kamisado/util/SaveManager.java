package com.team11.kamisado.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Diyan on 27/03/2017.
 */
public class SaveManager {
    
    private static final String SAVE_FILE = "saves/resume.ser";
    private static final String LEADERBOARD = "saves/leaderboard.ser";
    
    public static void deleteFile() {
        new File(SAVE_FILE).delete();
    }
    
    public static boolean fileExists() {
        return new File(SAVE_FILE).exists();
    }
    
    public static ArrayList<Serializable> loadFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(SAVE_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Serializable> list = (ArrayList<Serializable>) objectInputStream.readObject();
            objectInputStream.close();
            return list;
        }
        catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void saveToFile(Serializable... toBeSaved) {
        ArrayList<Serializable> list = new ArrayList<>();
    
        Collections.addAll(list, toBeSaved);
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            
           outputStream.writeObject(list);
            
            outputStream.close();
            fileOutputStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
