package com.ImageFilter.jpg2GreyScale;

/**
 * Auth: Roop Ghosh
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@org.springframework.stereotype.Component
public class Jpg2GreyScale {

    BufferedImage  image;
    int width;
    int height;

    public String getimage(String filepath) throws IOException {
        try {
            File input = new File(filepath);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            for(int i=0; i<height; i++){

                for(int j=0; j<width; j++){

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,

                            red+green+blue,red+green+blue);

                    image.setRGB(j,i,newColor.getRGB());
                }
            }

            String fileParent= new File(filepath).getParent();
            File output = new File(fileParent, "grayscale.jpg");
            ImageIO.write(image, "jpg", output);
            image.flush();
            return output.getPath();
        } catch (Exception e) {
           throw new IOException("something bad happened");
        }
    }
}