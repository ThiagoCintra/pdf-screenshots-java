package pdf.screenshot.pdf;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pdf.screenshot.take.TakeScreenshot;

public class PdfGenerete {

	private static PdfGenerete instace;
	private Document document = new Document();
	private TakeScreenshot take = new TakeScreenshot();

	private static Font CAT_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

//	public PdfGenerete() {
//		try {
//			createPdfImage();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public static synchronized PdfGenerete getInstance() {
		if (instace == null) {
			instace = new PdfGenerete();
		}
		return instace;
	}

	public void createDocument() throws DocumentException {
		try {
			PdfWriter.getInstance(document, new FileOutputStream("src\\test\\resources\\target\\teste.pdf"));
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		document.open();
		document.addTitle("Teste");

	}

	public Paragraph addImagesInPdf() throws IOException, DocumentException {
		Paragraph paragraph = new Paragraph("", HEADER_FONT);
		PdfPTable table = new PdfPTable(1);

		for (BufferedImage image : take.getBufferImageList()) {
			table.addCell(getImageInBuffer(image, 30f, true));
		}
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		paragraph.add(table);
		paragraph.setLeading(0);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		return paragraph;

	}

	public PdfPCell getImageInBuffer(BufferedImage bufferedImage, float scalePercent, boolean isHorizontallyCentered)
			throws BadElementException, IOException {

		Image image = Image.getInstance(bufferedImage, null);
		image.scalePercent(scalePercent);
		image.setAlignment(Image.MIDDLE);
		PdfPCell imageCell = new PdfPCell(image);
		
		imageCell.setBorderWidthBottom(60);
		imageCell.setBorder(0);
		if (isHorizontallyCentered) {
			imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		} else {
			imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		}

		imageCell.setVerticalAlignment(Element.ALIGN_TOP);
		int height = (int) Math.ceil(bufferedImage.getHeight() * scalePercent / 100);
		imageCell.setFixedHeight(height);
		return imageCell;

	}

	public void createPdfImage() throws DocumentException {
		createDocument();
		try {
			document.add(addImagesInPdf());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}
}
