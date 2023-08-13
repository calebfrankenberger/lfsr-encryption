package com.calebfrankenberger.lfsr;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.*;
import java.nio.IntBuffer;

public class EncryptImage {

    // XOR the a, r, g, & b values of each pixel with a number from the LFSR
    public static BufferedImage encrypt(BufferedImage image, LFSR lfsr, TextArea textArea) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for(int col = 0; col < image.getWidth(); col++) {
            for (int row = 0; row < image.getHeight(); row++) {
                int rgb = image.getRGB(col, row);
                int alpha = (rgb >> 24) & 0xFF;
                int red =   (rgb >> 16) & 0xFF;
                int green = (rgb >>  8) & 0xFF;
                int blue =  (rgb      ) & 0xFF;

                alpha = lfsr.generate(8) ^ alpha;
                red = lfsr.generate(8) ^ red;
                green = lfsr.generate(8) ^ green;
                blue = lfsr.generate(8) ^ blue;
                Color color = new Color(red, green, blue, alpha);
                newImage.setRGB(col, row, color.getRGB());
            }
        }

        return newImage;
    }

}
