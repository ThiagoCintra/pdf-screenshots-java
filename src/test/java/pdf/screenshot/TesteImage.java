package pdf.screenshot;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import pdf.screenshot.pdf.PdfGenerete;
import pdf.screenshot.take.TakeScreenshot;

public class TesteImage {

	public static void main(String[] args) {
		TakeScreenshot take = new TakeScreenshot();
		PdfGenerete pdfGenerete = new PdfGenerete();
		try {
			take.takeScreenShot();
			take.takeScreenShot();
			take.takeScreenShot();
			take.takeScreenShot();
			try {
				pdfGenerete.createPdfImage();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
