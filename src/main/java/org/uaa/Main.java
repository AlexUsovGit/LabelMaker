package org.uaa;

import org.uaa.reader.ExcelReader;
import org.uaa.reader.LabelDto;
import org.uaa.writer.PdfCreator;

import java.util.List;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        ExcelReader reader = new ExcelReader();
        List<LabelDto> labels = reader.readFile("template.xlsx");
        PdfCreator pdfCreator = new PdfCreator();
        pdfCreator.create(labels);
    }
}