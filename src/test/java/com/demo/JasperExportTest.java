package com.demo;

import com.demo.model.Employee;
import com.demo.report.ReportFormat;
import com.demo.report.ReportSetting;
import com.demo.util.JasperReportUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@DisplayName("利用JasperReport產出報表測試")
@SpringBootTest
class JasperExportTest {

    private ReportSetting provideSetting() {
        List<Employee> sourceList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Employee source = new Employee();
            source.setName(String.format("測試名稱%s", i));
            source.setTitle(String.format("測試職稱%s", i));
            source.setNote(String.format("測試備註%s", i));
            source.setTel(String.format("測試電話%s", i));
            sourceList.add(source);
        }
        ReportSetting setting = new ReportSetting();
        setting.setJasperName("jasper/simple-demo.jasper");
        setting.setDataSource(new JRBeanCollectionDataSource(sourceList));
        var builder = new DateTimeFormatterBuilder();
        builder.appendPattern("yyyyMMdd");
        DateTimeFormatter formatter = builder.toFormatter();
        setting.putParameter("exportDate", LocalDate.now().format(formatter));
        setting.setSheetName("JasperReport展示");
        return setting;
    }

    @DisplayName("產出excel")
    @Test
    void exportExcel() throws IOException {
        ReportSetting setting = provideSetting();
        setting.setFormat(ReportFormat.xls);
        OutputStream outputStream= new FileOutputStream("target/simple-demo." + ReportFormat.xls.getCode());
        JasperReportUtils.write(setting, outputStream);
        outputStream.close();
    }

    @DisplayName("產出DPF")
    @Test
    void exportPDF() throws IOException {
        ReportSetting setting = provideSetting();
        setting.setFormat(ReportFormat.pdf);
        OutputStream outputStream= new FileOutputStream("target/simple-demo." + ReportFormat.pdf.getCode());
        JasperReportUtils.write(setting, outputStream);
        outputStream.close();
    }

    @DisplayName("產出word")
    @Test
    void exportWord() throws IOException {
        ReportSetting setting = provideSetting();
        setting.setFormat(ReportFormat.docx);
        OutputStream outputStream= new FileOutputStream("target/simple-demo." + ReportFormat.docx.getCode());
        JasperReportUtils.write(setting, outputStream);
        outputStream.close();
    }
}
