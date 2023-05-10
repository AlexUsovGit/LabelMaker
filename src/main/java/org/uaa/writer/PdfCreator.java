package org.uaa.writer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.uaa.reader.LabelDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfCreator
{
    public static final String FONT = "Arial.ttf";

    public void create(List<LabelDto> labels)
    {
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd MM yyyy_ HH:mm:ss");
        String fileName = formatter.format(LocalDateTime.now()) + " labels.pdf";
        System.out.println("Create file");
        Rectangle one = new Rectangle(50.0F, 50.0F);
        Document document = new Document(one);
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.setMargins(1, 1, 1, 1);
            document.open();
            BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // Font font = FontFactory.getFont(String.valueOf(Font.FontFamily.TIMES_ROMAN), "Cp1250", true);
            Font font = new Font(bf, 5, Font.NORMAL);

            float[] headWith = {4F};
            float[] columnWidths = {2F, 2F};
            float[] materialsWith = {2F};

            for (LabelDto label : labels)
            {
                PdfPTable headTable = new PdfPTable(headWith);
                headTable.setWidthPercentage(100);
                headTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell modelHead = new PdfPCell(new Phrase(label.getModel(), font));
                modelHead.setPaddingBottom(0F);
                modelHead.setPaddingTop(0F);
                modelHead.setBorderColor(BaseColor.WHITE);
                headTable.addCell(modelHead);
                PdfPCell infoHead = new PdfPCell(new Phrase(label.getRazmer() + " " + label.getPolnota() + " " + label.getDataVypuska(), font));
                infoHead.setBorderColor(BaseColor.WHITE);
                infoHead.setPaddingTop(0F);
                headTable.addCell(infoHead);
                document.add(headTable);

                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(Element.ALIGN_RIGHT);
                PdfPCell imageAll = createImageCell("222.png", 36F);
                imageAll.setBorderColor(BaseColor.WHITE);
                table.addCell(imageAll);

                PdfPTable materials = new PdfPTable(materialsWith);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell imageV = createMaterialCell(label.getMaterVerh(), 12F);
                PdfPCell imageP = createMaterialCell(label.getMaterPodkladka(), 12F);
                PdfPCell imageN = createMaterialCell(label.getMaterNiz(), 12F);

                imageV.setBorderColor(BaseColor.WHITE);
                imageP.setBorderColor(BaseColor.WHITE);
                imageN.setBorderColor(BaseColor.WHITE);
                materials.addCell(imageV);
                materials.addCell(imageP);
                materials.addCell(imageN);
                PdfPCell matCell = new PdfPCell(materials);
                matCell.setBorderColor(BaseColor.WHITE);
                table.addCell(matCell);
//
//                PdfPCell imageMat = createImageCell("mat.png");
//                imageMat.setBorderColor(BaseColor.WHITE);
//                table.addCell(imageMat);
//
//                PdfPCell emp = new PdfPCell();
//                emp.setBorderColor(BaseColor.WHITE);
//                table.addCell(emp);
//                document.add(materials);
                document.add(table);
            }
            document.close();
            System.out.println("Creating file is complete");

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    private PdfPCell createImageCell(String path, float height) throws DocumentException, IOException
    {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setFixedHeight(height);
        return cell;
    }

    private PdfPCell createMaterialCell(String path, float height) throws DocumentException, IOException
    {
        if (path.contains("/"))
        {
            float[] matTWith = {3F, 1.5F, 3F};
            PdfPTable matT = new PdfPTable(matTWith);
            Image img = Image.getInstance(path.split("/")[0] + ".png");
            PdfPCell cell = new PdfPCell(img, true);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setFixedHeight(height);
            matT.addCell(cell);

            img = Image.getInstance("sl.png");
            cell = new PdfPCell(img, true);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setFixedHeight(height);
            matT.addCell(cell);

            img = Image.getInstance(path.split("/")[1] + ".png");
            cell = new PdfPCell(img, true);
            cell.setFixedHeight(height);
            cell.setBorderColor(BaseColor.WHITE);
            matT.addCell(cell);
            return new PdfPCell(matT);
        }
        else
        {
            Image img = Image.getInstance(path + ".png");
            PdfPCell cell = new PdfPCell(img, true);
            cell.setFixedHeight(height);
            return cell;
        }

    }
}
