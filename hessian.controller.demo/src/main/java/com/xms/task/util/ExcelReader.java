package com.xms.task.util;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.springframework.util.ObjectUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelReader {

    /**
     * 解析并显示一个表的内容和使用指定的样式
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public static List<String[]> processSheet(StylesTable styles, ReadOnlySharedStringsTable strings,
                                              InputStream sheetInputStream, int minColumns)
            throws IOException, ParserConfigurationException, SAXException {

        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ExcelReaderHandler handler = new ExcelReaderHandler(styles, strings, minColumns, System.out);

        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
        return handler.getRows();
    }

    /**
     * 解析第一个sheet
     *
     * @param path
     * @param minColumns
     * @return List<String[]>
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static List<String[]> processOneSheet(String path, InputStream inputStream,int minColumns)
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
        OPCPackage p = null;
        if (ObjectUtils.isEmpty(path) && !ObjectUtils.isEmpty(inputStream)){
            p = OPCPackage.open(inputStream);
        }else {
            p = OPCPackage.open(path, PackageAccess.READ);
        }

        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(p);
        XSSFReader xssfReader = new XSSFReader(p);
        StylesTable styles = xssfReader.getStylesTable();
        InputStream stream = xssfReader.getSheet("rId1");
        List<String[]> list = processSheet(styles, strings, stream, minColumns);
        stream.close();
        return list;
    }

    public static void main(String[] args) throws Exception {

//          long begin = System.currentTimeMillis() ;
////          List<String[]> list = ExcelReader.processOneSheet("E://b.xlsx" , 6);
//          List<String[]> list1 = ExcelReader.processOneSheet("E://b.xlsx" , 6);
//          for(String[] list : list1){
//              for (String cell : list)
//              {
//                  System.out.println(cell + "  ");
////              System.out.println(cell == null);
//              }
//          }
//
//          long end = System.currentTimeMillis() ; System.out.println("用时：" + (end - begin) /1000 + "秒");

        long begin = System.currentTimeMillis();
        System.out.println(begin);
        InputStream inputStream = new FileInputStream(new File("E://b.xlsx"));
        List<String[]> list = ExcelReader.processOneSheet(null,inputStream, 6);
        long end = System.currentTimeMillis();
        System.out.println("读取用时：" + (end - begin) / 1000 + "秒，总量：" + list.size());
        Connection conn = getNew_Conn();
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement("insert into items values(?,?,?,?,?,?)");
        for (int i = 1; i < list.size(); i++) {
            String[] row = list.get(i);
//            for (int index = 1; index <= 6; index++) {
//                if (row[index - 1] == null) {
//                    pstmt.setNull(index, Types.NULL);
//                } else {
//                    pstmt.setObject(index, row[index - 1]);
//                }
//            }
            pstmt.setObject(1, row[0]);
            pstmt.setObject(2, row[1]);
            pstmt.setObject(3, row[2]);
            pstmt.setObject(4, row[3]);
            pstmt.setObject(5, row[4]);
            Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(row[5]);
            pstmt.setObject(6, date);

//            pstmt.execute();
            pstmt.addBatch();
            if (i > 0 && i % 10000 == 0) {
                pstmt.executeBatch();
                System.out.println("提交：" + i);
            }
        }
        pstmt.executeBatch();
        conn.commit();
        pstmt.close();
        conn.close();
        end = System.currentTimeMillis();
        System.out.println("插入用时：" + (end - begin) / 1000 + "秒");
    }

    private static Connection getNew_Conn() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8", "root",
                    "123456");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

}