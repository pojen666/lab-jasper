package com.demo.util;

import com.demo.report.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.activation.MimetypesFileTypeMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class JasperReportUtils {

    private static final Map<ReportFormat, Class<? extends JasperReportWriter>> writers;

    static {
        writers = new ConcurrentHashMap<>();
        writers.put(ReportFormat.docx, JasperReportDocxWriter.class);
        writers.put(ReportFormat.pdf, JasperReportPdfWriter.class);
        writers.put(ReportFormat.xls, JasperReportXlsWriter.class);
    }

    private JasperReportUtils() {
    }

    /**
     * 輸出報表
     *
     * @param setting      報表設定
     * @param outputStream Output Stream
     */
    public static void write(ReportSetting setting, OutputStream outputStream) {
        try {
            JasperReportWriter writer = getReportWriter(setting.getFormat());
            writer.write(setting, outputStream);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 建立報表內容
     *
     * @param setting 報表設定
     * @return 報表
     */
    public static byte[] generateReport(ReportSetting setting) {
        try (var baos = new ByteArrayOutputStream()) {
            write(setting, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 建立檔案下載ResponseEntity
     *
     * @param setting 報表設定
     * @return ResponseEntity
     */
    public static ResponseEntity<Resource> createResponseEntity(
            ReportSetting setting
    ) {
        String filename = setting.getFilename();
        var fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(filename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        UrlUtils.getFileDownloadHeader(filename)
                )
                .body(new ByteArrayResource(
                        JasperReportUtils.generateReport(setting)
                ));
    }

    /**
     * 取得指定格式的報表產生器
     *
     * @param format 報表格式
     * @return 報表產生器
     * @throws IllegalAccessException 總是有例外
     * @throws InstantiationException 總是有例外
     */
    private static JasperReportWriter getReportWriter(ReportFormat format)
            throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<? extends JasperReportWriter> clazz = writers.get(format);
        return clazz.getDeclaredConstructor().newInstance();
    }

    /**
     * 根據PDF設定將PDF轉為BufferedImage應用
     *
     * @param setting PDF設定檔
     */
    public static BufferedImage pdfToImage(ReportSetting setting) {
        BufferedImage bufferedImage = null;
        if (ReportFormat.pdf.equals(setting.getFormat())) {
            try {
                JasperReportPdfWriter writer = (JasperReportPdfWriter) getReportWriter(ReportFormat.pdf);
                bufferedImage = writer.reportToImage(setting);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            throw new RuntimeException("ReportFormat 非 PDF");
        }

        return bufferedImage;
    }
}
