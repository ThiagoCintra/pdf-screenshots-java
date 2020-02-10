package pdf.screenshot.take;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;

public class TakeScreenshot {

	private static TakeScreenshot instance;

	private static LinkedHashMap<String,BufferedImage> bufferImageMap = new LinkedHashMap<>();

	public  LinkedHashMap<String, BufferedImage> getBufferImageMap() {
		return bufferImageMap;
	}

	public  void setBufferImageMap(LinkedHashMap<String, BufferedImage> bufferImageMap) {
		TakeScreenshot.bufferImageMap = bufferImageMap;
	}

	public static synchronized TakeScreenshot getInstance() {
		if (instance == null) {
			instance = new TakeScreenshot();
		}
		return instance;
	}

	

	public  void takeScreenShot(String txt) throws IOException {
		Robot r;
		try {
			r = new Robot();
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);
			bufferImageMap.put(txt, image);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}
}
