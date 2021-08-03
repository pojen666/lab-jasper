package com.demo.report;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * PDF報表產生器，目前字型只支援「標楷體」，如果設定其他字體，需要到Jasper Studio 設定中將字型
 * 匯出成jar檔
 */
@Slf4j
public class JasperReportPdfWriter extends AbstractJasperReportWriter {
    @Override
    public void writeReport(
            ReportSetting setting,
            ExporterInput exporterInput,
            OutputStreamExporterOutput exporterOutput
    ) throws JRException {
        var pdfExporter = new JRPdfExporter();
        pdfExporter.setExporterInput(exporterInput);
        pdfExporter.setExporterOutput(exporterOutput);
        if (setting.getConfiguration() instanceof PdfExporterConfiguration) {
            pdfExporter.setConfiguration((PdfExporterConfiguration) setting.getConfiguration());
        }

        pdfExporter.exportReport();
    }

    public BufferedImage reportToImage(ReportSetting setting) {
        BufferedImage bufferedImage = null;
        try {

            Optional<JasperReport> jasperReportOptional = getJasperReport(setting);
            if (jasperReportOptional.isPresent()) {
                var jasperReport = jasperReportOptional.get();
                var jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        setting.getParameters(),
                        setting.getDataSource()
                );
                JRGraphics2DExporter exporter = new JRGraphics2DExporter();
                bufferedImage = new BufferedImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.exportReportToGraphics2D(g);
                g.dispose();
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return bufferedImage;
    }
}
