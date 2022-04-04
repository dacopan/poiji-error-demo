package com.uqai.excelutil.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author dacopan on 19/3/22
 */
@Data
public class UqaiExcelResult<T> {

    private ArrayList<T> result = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

}
