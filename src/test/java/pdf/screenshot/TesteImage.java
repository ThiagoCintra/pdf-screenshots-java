package pdf.screenshot;

import java.io.IOException;
import java.text.ParseException;

import com.itextpdf.text.DocumentException;

import pdf.screenshot.pdf.PdfGenerete;
import pdf.screenshot.take.TakeScreenshot;

public class TesteImage {

	public static void main(String[] args) throws ParseException {
		TakeScreenshot take = new TakeScreenshot();
		PdfGenerete pdfGenerete = new PdfGenerete();
		try {
			take.takeScreenShot("PRINT 1");
			take.takeScreenShot("PRINT 2");
			take.takeScreenShot("PRINT 3");
			take.takeScreenShot("PRINT 4");
			try {
				pdfGenerete.createPdf("XUXU");
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
