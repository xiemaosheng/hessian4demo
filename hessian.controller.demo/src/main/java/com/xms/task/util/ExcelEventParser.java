package com.xms.task.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28 0028.
 */
public class ExcelEventParser {
    private String filename;
    private InputStream inputStream;
    private XSSFSheetXMLHandler.SheetContentsHandler handler;

    public ExcelEventParser(String filename){
        this.filename = filename;
    }

    public ExcelEventParser(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ExcelEventParser setHandler(XSSFSheetXMLHandler.SheetContentsHandler handler) {
        this.handler = handler;
        return this;
    }

    public void parse(){
        OPCPackage pkg = null;
        InputStream sheetInputStream = null;

        try {

            if (inputStream != null){
                pkg = OPCPackage.open(inputStream);
            }else {
                pkg = OPCPackage.open(filename, PackageAccess.READ);
            }

            XSSFReader xssfReader = new XSSFReader(pkg);

            StylesTable styles = xssfReader.getStylesTable();
            InputStream stream = xssfReader.getSheet("rId1");
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            sheetInputStream = xssfReader.getSheetsData().next();

            processSheet(styles, strings, sheetInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            if(sheetInputStream != null){
                try {
                    sheetInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            if(pkg != null){
                try {
                    pkg.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws SAXException, ParserConfigurationException, IOException{
        XMLReader sheetParser = SAXHelper.newXMLReader();

        if(handler != null){
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, handler, false));
        }else{
            sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, new SimpleSheetContentsHandler(), false));
        }

        sheetParser.parse(new InputSource(sheetInputStream));
    }

    public static class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        protected List<String> row = new LinkedList<String>();


        public void startRow(int rowNum) {
//            row.clear();
        }


        public void endRow(int rowNum) {
            System.out.println(rowNum + " : " + row);
        }


        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            row.add(formattedValue);
        }


        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }


    public static void main(String[] args) throws Throwable{
        long start = System.currentTimeMillis();

        final List<List<String>> table = new ArrayList<List<String>>();
        new ExcelEventParser("E:/b.xlsx").setHandler(new SimpleSheetContentsHandler(){

            private List<String> fields;

            @Override
            public void endRow(int rowNum) {
                if(rowNum == 0){
                    // 第一行中文描述忽略
                }else if(rowNum == 1){
                    // 第二行字段名
                    fields = row;
                }else {
                    // 数据
                    table.add(row);
                }

            }
        }).parse();

        long end = System.currentTimeMillis();

        System.err.println(table.size());

        System.err.println(end - start);
    }

}
