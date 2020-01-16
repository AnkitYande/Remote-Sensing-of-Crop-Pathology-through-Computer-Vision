package projct_2019;

import java.awt.*;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Runner2 {

	static JSlider sliderHmin = new JSlider(0, 255);
	static JSlider sliderSmin = new JSlider(0, 255);
	static JSlider sliderVmin = new JSlider(0, 255);
	static JSlider sliderHmax = new JSlider(0, 255);
	static JSlider sliderSmax = new JSlider(0, 255);
	static JSlider sliderVmax = new JSlider(0, 255);
	static JavaPanel panel = new JavaPanel();
	static JFrame frame = new JFrame("detection cam");

	public static void main(String arg[]) throws InterruptedException {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// Initialize
		ColorDetector ColorDetector = new ColorDetector();
		Mat webcam_image;

		// VideoCapture webCam = new VideoCapture(0);

		// Setup
		setup();

		while (true) { // Main Loop
			// webCam.read(webcam_image);
			// Flip the camera image
			webcam_image = Imgcodecs.imread("/Users/Ankit/Desktop/20190211_125259.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
			Imgproc.cvtColor(webcam_image, webcam_image, Imgproc.COLOR_RGB2HSV);
			
			if (!webcam_image.empty()) {

				frame.setSize(webcam_image.width() / 2 + 20, webcam_image.height() / 2 + 120);

				// Detect Object

				webcam_image = ColorDetector.detect(webcam_image, sliderHmin.getValue(), sliderSmin.getValue(),
						sliderVmin.getValue(), sliderHmax.getValue(), sliderSmax.getValue(), sliderVmax.getValue());
				
				Imgproc.cvtColor(webcam_image, webcam_image, Imgproc.COLOR_HSV2RGB);
				// Display the image
				panel.matToBufferedImage(webcam_image);

				panel.repaint();

			} else {
				System.out.println("Error: No captured frame from webcam !");
				break;
			}
		}

	} // end main

	public static void setup() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.add(sliderHmin);
		panel.add(sliderSmin);
		panel.add(sliderVmin);
		panel.add(sliderHmax);
		panel.add(sliderSmax);
		panel.add(sliderVmax);

		sliderHmin.setValue(30);
		sliderSmin.setValue(75);
		sliderVmin.setValue(60);
		sliderHmax.setValue(55);
		sliderSmax.setValue(255);
		sliderVmax.setValue(255);

		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}