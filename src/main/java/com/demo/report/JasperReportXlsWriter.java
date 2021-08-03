package com.demo.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.commons.lang3.StringUtils;

/**
 * Excel報表產生器
 */
public class JasperReportXlsWriter extends AbstractJasperReportWriter {

    @Override
    public void writeReport(
            ReportSetting setting,
            ExporterInput exporterInput,
            OutputStreamExporterOutput exporterOutput
    ) throws JRException {
        var exporter = new JRXlsExporter();
        exporter.setExporterInput(exporterInput);
        exporter.setExporterOutput(exporterOutput);
        var config = new SimpleXlsReportConfiguration();
        if (!StringUtils.isEmpty(setting.getSheetName())) {
            config.setSheetNames(new String[]{setting.getSheetName()});
        }
        config.setIgnorePageMargins(true);
        config.setRemoveEmptySpaceBetweenColumns(true);
        exporter.setConfiguration(config);
        exporter.exportReport();
    }
}
