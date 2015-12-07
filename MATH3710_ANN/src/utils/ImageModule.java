package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ImageModule {

	public static double[] loadImage(File path) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
		}

		/*
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer())
				.getData();
		*/
		
		//int sRbgColor = img.getRGB(int x, int y);

		
		int w = img.getWidth();
		int h = img.getHeight();
		double[] result = new double[w*h];
		int i = 0;
		
		Raster raster = img.getData();
		for (int j = 0; j < w; j++) {
		    for (int k = 0; k < h; k++) {
		        result[i] = raster.getSample(j, k, 0);
		        i++;
		    }
		}
		
		return result;
	}

}
