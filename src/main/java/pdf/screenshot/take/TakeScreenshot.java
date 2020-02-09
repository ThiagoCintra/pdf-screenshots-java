package pdf.screenshot.take;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TakeScreenshot {

	private static TakeScreenshot instance;
	private static ArrayList<BufferedImage> bufferImageList = new ArrayList<BufferedImage>();

	public static synchronized TakeScreenshot getInstance() {
		if (instance == null) {
			instance = new TakeScreenshot();
		}
		return instance;
	}

	public ArrayList<BufferedImage> getBufferImageList() {
		return bufferImageList;
	}

	public void setBufferImageList(ArrayList<BufferedImage> bufferImageList) {
		this.bufferImageList = bufferImageList;
	}

	public  void takeScreenShot() throws IOException {
		Robot r;
		try {
			r = new Robot();
			Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage image = r.createScreenCapture(capture);
			bufferImageList.add(image);
			//ImageIO.write(image, "jpg", new File("txtNameImage")); 

		} catch (AWTException e) {
			e.printStackTrace();
		}

	}
}
