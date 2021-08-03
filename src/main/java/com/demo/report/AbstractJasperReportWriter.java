package com.demo.report;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;


/**
 * Abstract JasperReport Writer
 */
@Slf4j
public abstract class AbstractJasperReportWriter implements JasperReportWriter {

    @Override
    public void write(ReportSetting setting, OutputStream outputStream) {
        try {
            Optional<JasperReport> optionalJasperReport = getJasperReport(setting);
            if (optionalJasperReport.isPresent()) {
                var jasperReport = optionalJasperReport.get();
                var jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        setting.getParameters(),
                        setting.getDataSource()
                );
                ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
                OutputStreamExporterOutput exporterOutput =
                        new SimpleOutputStreamExporterOutput(outputStream);
                writeReport(setting, exporterInput, exporterOutput);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 取得JaspreReport實體，jasper檔優先 其次才是jrxml
     *
     * @param setting 報表設定
     * @return 有對應的報表檔才有內容
     */
    protected Optional<JasperReport> getJasperReport(ReportSetting setting) throws IOException, JRException {
        var loader = Thread.currentThread().getContextClassLoader();
        JasperReport jasperReport = null;
        if (!StringUtils.isEmpty(setting.getJasperName())) {
            try (InputStream jasperStream =
                         loader.getResourceAsStream(setting.getJasperName())) {
                jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            }
        } else if (!StringUtils.isEmpty(setting.getJrxmlName())) {
            try (InputStream jrxmlStream = loader.getResourceAsStream(setting.getJrxmlName())) {
                jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            }
        }
        return Optional.ofNullable(jasperReport);
    }
}
