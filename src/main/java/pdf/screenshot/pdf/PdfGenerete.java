package pdf.screenshot.pdf;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

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

	public static synchronized PdfGenerete getInstance() {
		if (instace == null) {
			instace = new PdfGenerete();
		}
		return instace;
	}

	public void createDocument(String nameScenario) throws DocumentException, IOException, ParseException {
		Path path = Paths.get("src\\test\\resources\\target\\" + getDate());

		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			PdfWriter.getInstance(document, new FileOutputStream(path + "\\" + nameScenario + ".pdf"));

		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		document.open();
		document.addTitle(nameScenario);
	}


	public Paragraph addImagesInPdf() throws IOException, DocumentException {
		Paragraph paragraph = new Paragraph("", HEADER_FONT);
		PdfPTable table = new PdfPTable(1);

		for (Entry<String, BufferedImage> textAdnImage : take.getBufferImageMap().entrySet()) {
			table.addCell(getTextsInBuffer(textAdnImage.getKey()));
			table.addCell(getImageInBuffer(textAdnImage.getValue(), 30f, true));
		}
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		paragraph.add(table);
		paragraph.setLeading(0);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		return paragraph;

	}

	public PdfPCell getImageInBuffer(BufferedImage bufferedImage, float scalePercent, boolean isHorizontallyCentered) throws BadElementException, IOException {

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

	public PdfPCell getTextsInBuffer(String description) throws BadElementException, IOException {

		PdfPCell text = new PdfPCell(new Paragraph(description));

		text.setBorderWidthBottom(60);
		text.setBorder(0);
		text.setHorizontalAlignment(Element.ALIGN_CENTER);

		text.setVerticalAlignment(Element.ALIGN_TOP);
		return text;

	}

	public void createPdf(String name) throws DocumentException, IOException, ParseException {
		createDocument(name);
		try {
			document.add(addImagesInPdf());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}

	public String getDate() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = format.format(new Date());
		return dateString;
	}
}
