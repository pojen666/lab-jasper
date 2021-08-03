package com.demo.report;

import lombok.Data;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.export.ExporterConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * 報表設定
 */
@Data
public class ReportSetting {

    /**
     * 報表格式
     */
    private ReportFormat format;

    /**
     * jasper檔路徑
     */
    private String jasperName;

    /**
     * jrxml檔路徑
     */
    private String jrxmlName;

    /**
     * JasperReport參數
     */
    private Map<String, Object> parameters;

    /**
     * JasperReport Data Source
     */
    private JRDataSource dataSource;

    /**
     * Sheet Name，如果格式為xls、ods可指定
     */
    private String sheetName;

    /**
     * 額外須設定之內容
     */
    private ExporterConfiguration configuration;


    /**
     * 設定參數
     *
     * @param key   鍵
     * @param value 值
     */
    public void putParameter(String key, Object value) {
        if (null == parameters) {
            parameters = new HashMap<>();
        }
        parameters.put(key, value);
    }

    public String getFilename() {
        String extension = "." + format.getCode();
        var basename = "";
        if (!StringUtils.isEmpty(sheetName)) {
            basename = sheetName;
        } else if (!StringUtils.isEmpty(jasperName)) {
            basename = FilenameUtils.getBaseName(jasperName);
        } else if (!StringUtils.isEmpty(jrxmlName)) {
            basename = FilenameUtils.getBaseName(jrxmlName);
        }
        return basename + extension;
    }

    public Map<String, Object> getParameters() {
        if (Objects.nonNull(parameters)) {
            parameters.computeIfAbsent(JRParameter.REPORT_LOCALE, key -> Locale.TAIWAN);
        }
        return parameters;
    }
}
