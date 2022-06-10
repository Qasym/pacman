package com.Game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
            File worldFile = Utils.getResourceAsFile(path);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(worldFile.getPath()));
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

    // Source: https://stackoverflow.com/a/35466006/11955017
    public static File getResourceAsFile(String resourcePath) {
        File file = null;
        try {
            InputStream inputStream = Utils.class.getResourceAsStream(resourcePath);
            assert inputStream != null;
            Path path = Files.createTempFile(String.valueOf(inputStream.hashCode()), ".tmp");
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            file = path.toFile();
            file.deleteOnExit();
            inputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(103);
        }
        return file;
    }
}
