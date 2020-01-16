package projct_2019;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ColorDetector {

	Point center;
	Double width;

	// constructor
	public ColorDetector() {
		center = new Point(0, 0);
		width = 0.0;
	}

	// Detects the object and creates a bounding box
	public Mat detect(Mat inputframe, int h, int s, int v, int H, int S, int V) throws InterruptedException {

		Scalar min = new Scalar(h, s, v);// HSV
		Scalar max = new Scalar(H, S, V);// HSV
		
		center = new Point(0, 0);
		width = (double) inputframe.width();
		Mat imgThreshold = new Mat();
		
		// Convert image from BGR to HSV
		Imgproc.cvtColor(inputframe, imgThreshold, Imgproc.COLOR_RGB2HSV);

		// Create a mask of the areas within the min and max threshold
		Core.inRange(imgThreshold, min, max, imgThreshold);

		// Erode and dilate the mask
		 Mat kernelOpen = Mat.ones(2, 2, 0);
		 Mat kernelClose = Mat.ones(1, 1, 0);
		 Imgproc.morphologyEx(imgThreshold, imgThreshold, Imgproc.MORPH_OPEN,
		 kernelOpen);
		 Imgproc.morphologyEx(imgThreshold, imgThreshold, Imgproc.MORPH_CLOSE,
		 kernelClose);

		// Find the contours of the mask
		Mat mat1 = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(imgThreshold, contours, mat1, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

		double area = 0;
		double dist = 50.0;

		Iterator<MatOfPoint> iterator = contours.iterator();
		while (iterator.hasNext()) {
			MatOfPoint contour = iterator.next();
			area = Imgproc.contourArea(contour);
		}

		// area calculations
		area= Math.round(area * ((720.0 / 1280) / (720.0 * 1280))
				* Math.pow((dist * 2 * Math.sin(31 * Math.PI / 180)), 2) * 100.0)/ 100.0;
		
		System.out.println(area);
		
		for (int i = 0; i < contours.size(); i++) {
			Rect rect = Imgproc.boundingRect(contours.get(i));
			if (rect.width * rect.height > 10000)
				Imgproc.rectangle(inputframe, rect.tl(), rect.br(), new Scalar(255, 0, 0), 5);

		}

		return imgThreshold;
	}

}
