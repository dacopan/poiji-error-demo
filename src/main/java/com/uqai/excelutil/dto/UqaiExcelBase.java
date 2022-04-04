package com.uqai.excelutil.dto;

import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dacopan on 19/3/22
 */
public abstract class UqaiExcelBase {

    @ExcelRow
    @Getter
    @Setter
    private int rowIndex;

}
