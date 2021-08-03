package com.demo.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.DocxExporterConfiguration;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;

/**
 * DOCX報表產生器
 */
public class JasperReportDocxWriter extends AbstractJasperReportWriter {

    @Override
    public void writeReport(
            ReportSetting setting,
            ExporterInput exporterInput,
            OutputStreamExporterOutput exporterOutput
    ) throws JRException {
        var docxExporter = new JRDocxExporter();
        docxExporter.setExporterInput(exporterInput);
        docxExporter.setExporterOutput(exporterOutput);
        if (setting.getConfiguration() instanceof DocxExporterConfiguration) {
            docxExporter.setConfiguration((DocxExporterConfiguration) setting.getConfiguration());

        }
        docxExporter.exportReport();
    }
}
