package com.demo.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;

import java.io.OutputStream;

/**
 * 單一格式的JasperReport報表產生器
 */
public interface JasperReportWriter {

    /**
     * 輸出報表
     *
     * @param setting      報表設定
     * @param outputStream Output Stream
     */
    void write(ReportSetting setting, OutputStream outputStream);

    /**
     * 實際執行輸出的行為
     *
     * @param setting        報表設定
     * @param exporterInput  輸入的報表樣板
     * @param exporterOutput 輸出的報表
     * @throws JRException 總是有例外
     */
    default void writeReport(
            ReportSetting setting,
            ExporterInput exporterInput,
            OutputStreamExporterOutput exporterOutput
    ) throws JRException {

    }
}
