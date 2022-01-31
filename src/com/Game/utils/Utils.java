package com.Game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/*
* A class that contains useful functions that are helpful across the project
* */
public class Utils {
    // Safely parses a 'number' from a string and returns that number as int
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Returns the content of a file as a string
    public static String loadFileAsString(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(101);
        }
        return stringBuilder.toString();
    }

    // Loads an image from a given path to the image
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Utils.class.getResource(path)));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(102);
        }
        return null;
    }
}
