package com.nd.pbl.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;
import com.xms.task.util.DateFormatUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class CsvUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);
    private static final String format = "yyyy-MM-dd HH:mm";

    public static void writeCSVFile(OutputStream os, JSONArray exportData, List<String> fields, List<String> dateField) {
        CsvWriter csvWriter = new CsvWriter(os, ',', Charset.forName("UTF-8"));

        try {
            // 标题列
            for (String field : fields) {
                csvWriter.write(field);
            }
            csvWriter.endRecord();

            // 写入文件内容
            for (Object obj : exportData) {
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));

                for (String field : fields) {
                    Object value = jsonObject.get(field);

                    if (dateField.contains(field)) {
                        csvWriter.write(new DateTime(jsonObject.getDate(field)).toString(format) + "\t");
                    } else {
                        if (value instanceof String) {
                            csvWriter.write(jsonObject.getString(field) + "\t");
                        } else if (value instanceof Integer) {
                            csvWriter.write(jsonObject.getInteger(field).toString() + "\t");
                        } else if (value instanceof Float) {
                            csvWriter.write(jsonObject.getFloat(field).toString() + "\t");
                        } else if (value instanceof Double) {
                            csvWriter.write(jsonObject.getDouble(field).toString() + "\t");
                        } else if (value instanceof Date) {
                            csvWriter.write(new DateTime(jsonObject.getDate(field)).toString(format) + "\t");
                        } else if (value instanceof Long) {
                            csvWriter.write(jsonObject.getLong(field).toString() + "\t");
                        } else {
                            csvWriter.write(JSON.toJSONString(jsonObject.get(field)) + "\t");
                        }
                    }

                }

                csvWriter.endRecord();
            }
        } catch (Exception e) {
            logger.error("writer csvFile error:", e);
        } finally {
            csvWriter.close();
        }

    }


//    /**
//     * 自己实现csv下载
//     */
//    public static void createCSVFile(JSONArray exportData, List<String> fields, List<String> dateField, OutputStream os) {
//        BufferedWriter csvFileOutputStream = null;
//        try {
//            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"), 1024);
//            // 标题列
//            for (String field : fields) {
//                csvFileOutputStream.write(field);
//                csvFileOutputStream.write(",");
//            }
//            csvFileOutputStream.write("\r\n");
//            // 写入文件内容,
//            // ============ //第一种格式：Arraylist<实体类>填充实体类的基本信息==================
//            for (Object obj : exportData) {
//                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
//
//                for (String field : fields) {
//                    Object value = jsonObject.get(field);
//
//                    if (dateField.contains(field)) {
//                        csvFileOutputStream.write(new DateTime(jsonObject.getDate(field)).toString(format) + "\t");
//                    } else {
//                        if (value instanceof String) {
//                            csvFileOutputStream.write(jsonObject.getString(field) + "\t");
//                        } else if (value instanceof Integer) {
//                            csvFileOutputStream.write(jsonObject.getInteger(field).toString() + "\t");
//                        } else if (value instanceof Float) {
//                            csvFileOutputStream.write(jsonObject.getFloat(field).toString() + "\t");
//                        } else if (value instanceof Double) {
//                            csvFileOutputStream.write(jsonObject.getDouble(field).toString() + "\t");
//                        } else if (value instanceof Date) {
//                            csvFileOutputStream.write(new DateTime(jsonObject.getDate(field)).toString(format) + "\t");
//                        } else if (value instanceof Long) {
//                            csvFileOutputStream.write(jsonObject.getLong(field).toString() + "\t");
//                        } else {
//                            csvFileOutputStream.write(JSON.toJSONString(jsonObject.get(field)) + "\t");
//                        }
//                    }
//
//                    csvFileOutputStream.write(",");
//                }
//
//                csvFileOutputStream.write("\r\n");
//            }
//
//            csvFileOutputStream.flush();
//        } catch (Exception e) {
//            logger.error("failed to export mongo data to CSV :", e);
//        } finally {
//            try {
//                csvFileOutputStream.close();
//            } catch (IOException e) {
//                logger.error("failed to close CSV IO :", e);
//            }
//        }
//    }

    /**
     * 下载csv文件
     *
     * @param response
     * @param exportData
     * @param fields
     * @throws IOException
     */
    public static void exportFile(HttpServletResponse response, JSONArray exportData, List<String> fields, List<String> dateField) throws IOException {
        response.setContentType("application/csv;charset=UTF-8");
        String fileName = String.format("%s-%s.csv", UUID.randomUUID(), DateFormatUtils.df.get().format(new Date()));
        response.setHeader("Content-Disposition", "attachment;  filename="
                + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.setCharacterEncoding("UTF-8");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            writeCSVFile(out, exportData, fields, dateField);
            out.flush();
        } catch (Exception e) {
            logger.error("export csvFile fail:", e);
        } finally {

            try {
                out.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

}
