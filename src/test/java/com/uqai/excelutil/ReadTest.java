package com.uqai.excelutil;

import com.uqai.excelutil.dto.AutoImport;
import com.uqai.excelutil.util.UqaiExcelReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author dacopan on 19/3/22
 */
class ReadTest {

    private static final Logger log = LogManager.getLogger(ReadTest.class);

    @Test
    void testReadSimple() {

        var list = UqaiExcelReaderUtil.process(new File("src/test/resources/autos3.xlsx"), AutoImport.class);
        list.getResult().forEach(log::info);

    }
}
