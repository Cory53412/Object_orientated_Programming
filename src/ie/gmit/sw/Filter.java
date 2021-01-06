package ie.gmit.sw;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 
 * @author Cory O'Donoghue- Filter extends thread class
 */

public class Filter extends Thread {
	/**
	 * creating variables
	 */
	private String filename = null;
	private BufferedImage input, output;
	private float[][] kernelFilters;

	/**
	 * function which retrieves directory off user
	 */

	public synchronized void addImage() {
		/**
		 * Scanner takes in users input of image  directory, 
		 * then takes input and assigns the input to a string called filename
		 */

		Scanner Img = new Scanner(System.in);
		System.out.println("Please enter the images directory:");
		String ImgDir = Img.nextLine();
		filename = new String(ImgDir);

	}

	/**
	 * function with 11 seperate matrices which dictate the kernel effect on the image
	 */

	public synchronized void chooseFilter() throws NumberFormatException, IOException {
		/**
		 * buffered reader used to take in the input
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		/**
		 * menu displaying 11 types of filtering options that user can select
		 */
		System.out.println("Please select a filter: ");
		System.out.println("1- Identity filter");
		System.out.println("2- Edge detection filter");
		System.out.println("3- Sharpen filter");
		System.out.println("4- Horizontal lines filter");
		System.out.println("5- Sobel horizontal filter");
		System.out.println("6- Vertical lines filter");
		System.out.println("7- Diagional 45 lines filter");
		System.out.println("8- Sobel vertical filter");
		System.out.println("9- Laplacian filter");
		System.out.println("10- Box Blur");
		System.out.println("11- Edge Detection 2");
		System.out.println("Optioned selected: ");

		/**
		 * if statements to apply the correct filter based on user input the kernels are
		 * applied to each kernelFilters this is what the filter is based on as seen in
		 * the brief
		 */
		int userOption = Integer.parseInt(reader.readLine());
		if (userOption == 1) {
			kernelFilters = new float[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } };
		} else if (userOption == 2) {
			kernelFilters = new float[][] { { 1, 0, -1 }, { 0, 0, 0 }, { -1, 0, 1 } };
		} else if (userOption == 3) {
			kernelFilters = new float[][] { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
		} else if (userOption == 4) {
			kernelFilters = new float[][] { { -1, -1, -1 }, { 2, 2, 2 }, { -1, -1, -1 } };
		} else if (userOption == 5) {
			kernelFilters = new float[][] { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
		} else if (userOption == 6) {
			kernelFilters = new float[][] { { 0, -1, 0 }, { -1, 4, -1 }, { 0, -1, 0 } };
		} else if (userOption == 7) {
			kernelFilters = new float[][] { { -1, 2, -1 }, { -1, 2, -1 }, { -1, 2, -1 } };
		} else if (userOption == 8) {
			kernelFilters = new float[][] { { -1, -1, 2 }, { -1, 2, -1 }, { 2, -1, -1 } };
		} else if (userOption == 9) {
			kernelFilters = new float[][] { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
		} else if (userOption == 10) {
			kernelFilters = new float[][] { { (float) 0.111, (float) 0.111, (float) 0.111 },
					{ (float) 0.111, (float) 0.111, (float) 0.111 }, { (float) 0.111, (float) 0.111, (float) 0.111 } };
		} else if (userOption == 11) {
			kernelFilters = new float[][] { { -1, -1, -1 }, { -1, 8, -1 }, { -1, -1, -1 } };
		}

		input = ImageIO.read(new File(filename));
		int width = input.getWidth();
		int height = input.getHeight();
		output = new BufferedImage(width, height, input.getType());
		System.out.println("Creating your filtered image");

		/**
		 * looping through images & width
		 */
		for (int xCordinate = 0; xCordinate < width; xCordinate++) {
			/**
			 * looping through height
			 */
			for (int yCordinate = 0; yCordinate < height; yCordinate++) {
				/**
				 * assigning variables, will be used to calculate later on
				 */
				float red = 0f, green = 0f, blue = 0f;
				for (int i = 0; i < kernelFilters.length; i++) {
					for (int j = 0; j < kernelFilters[i].length; j++) {
						/**
						 * part images into seperate channels
						 */
						int coordinateX = (xCordinate - kernelFilters.length / 2 + i + width) % width;
						int coordinateY = (yCordinate - kernelFilters[i].length / 2 + j + height) % height;

						int RGB = input.getRGB(coordinateX, coordinateY);
						int newRed = (RGB >> 16) & 0xff;
						int newGreen = (RGB >> 8) & 0xff;
						int newBlue = (RGB) & 0xff;

						/**
						 * The RGB is multiplied with current kernel element and added on to the
						 * variables red, blue and green
						 */
						red += (newRed * kernelFilters[i][j]);
						green += (newGreen * kernelFilters[i][j]);
						blue += (newBlue * kernelFilters[i][j]);
					}
				}
				int Red, Green, Blue;
				/**
				 * The value is truncated to 0 and 255 if it goes beyond
				 */
				Red = Math.min(Math.max((int) (red * 1), 0), 255);
				Green = Math.min(Math.max((int) (green * 1), 0), 255);
				Blue = Math.min(Math.max((int) (blue * 1), 0), 255);
				/**
				 * Pixel is written to output image
				 */
				output.setRGB(xCordinate, yCordinate, new Color(Red, Green, Blue).getRGB());
			}
		}
		/**
		 * save image as ".." ,
		 * in documents folder, 
		 * output message
		 */
		System.out.println("Save image as:");
		String outputfname = reader.readLine();
		ImageIO.write(output, "PNG", new File(System.getProperty("user.home") + "/Documents", outputfname + ".png"));
		System.out.println("Your filtered image: " + outputfname + ".png" + "  has been saved to your documents folder");
	}
}
