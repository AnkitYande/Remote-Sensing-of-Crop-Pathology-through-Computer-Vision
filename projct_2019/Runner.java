package projct_2019;

import java.awt.*;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Runner {

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
		Mat webcam_image = new Mat();
		VideoCapture webCam = new VideoCapture(0);

		// Setup
		setup();

		if (webCam.isOpened() && webCam.isOpened()) {
			Thread.sleep(1000); // Delay for cameras to initialize

			while (true) { // Main Loop
				webCam.read(webcam_image);
				// Flip the camera image
				Core.flip(webcam_image, webcam_image, +1);

				if (!webcam_image.empty()) {

					frame.setSize(webcam_image.width() / 2 + 20, webcam_image.height() / 2 + 120);

					// Detect Object
					System.out.println(sliderHmin.getValue() + " , "+ sliderSmin.getValue() + " , "+
							sliderVmin.getValue()+ "\n "+ sliderHmax.getValue()+ " , "+ sliderSmax.getValue()+ " , "+ sliderVmax.getValue());
					webcam_image = ColorDetector.detect(webcam_image, sliderHmin.getValue(), sliderSmin.getValue(),
							sliderVmin.getValue(), sliderHmax.getValue(), sliderSmax.getValue(), sliderVmax.getValue());
					
					// Display the image
					panel.matToBufferedImage(webcam_image);
					panel.matToBufferedImage(webcam_image);

					panel.repaint();

				} else {
					System.out.println("Error: No captured frame from webcam !");
					break;
				}
			}
		}
		webCam.release(); 
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