package com.demo.report;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 報表格式
 */
@RequiredArgsConstructor
public enum ReportFormat {

    docx("docx"), xls("xls"), pdf("pdf");

    @NonNull
    @Getter
    private final String code;
}
