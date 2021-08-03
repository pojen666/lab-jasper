package com.demo.bean;

import lombok.Data;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.oasis.DocumentBuilder;
import net.sf.jasperreports.engine.export.oasis.StyleCache;
import net.sf.jasperreports.engine.export.oasis.WriterHelper;

import java.awt.*;
import java.util.Map;

@Data
public class BuilderConfig {
    private DocumentBuilder documentBuilder;
    private JasperPrint jasperPrint;
    private int pageFormatIndex;
    private int pageIndex;
    private WriterHelper bodyWriter;
    private WriterHelper styleWriter;
    private StyleCache styleCache;
    private Map<Integer, String> rowStyles;
    private Map<Integer, String> columnStyles;
    private String sheetName;
    private Color tabColor;
}
