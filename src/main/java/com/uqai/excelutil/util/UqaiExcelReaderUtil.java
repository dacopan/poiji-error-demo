package com.uqai.excelutil.util;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.uqai.excelutil.dto.UqaiExcelBase;
import com.uqai.excelutil.dto.UqaiExcelResult;

import java.io.File;
import java.util.function.Consumer;

/**
 * @author dacopan on 19/3/22
 */
public class UqaiExcelReaderUtil {


    /**
     * OJO si reqiere debe pasar {@link CustomCasting} si pasa options
     * OJO se debe poner las fechas en el excel en el formato q se ponga aqui como options
     * // PoijiDataFormatter
     * // todo process Zonedatetime
     * // todo process custom decimal separator, (varios casos)
     * todo https://github.com/ozlerhakan/poiji/blob/master/src/test/java/com/poiji/util/DecimalSeparatorParseTest.java
     *
     * @param file
     * @param type
     * @param options
     * @param consumer consumer a ejecutarse x cada row, posterior a validacion de campos annotados, este consumer peude
     *                 lanzar una excepction pa informar q no se considere el registro como procesado con exito
     *                 dicho exception sera agregado como error
     * @param <T>
     * @return result process with list of erros and done items procesed
     */
    public static <T extends UqaiExcelBase> UqaiExcelResult<T> process(File file, Class<T> type, PoijiOptions options, final Consumer<? super T> consumer) {

        if (options == null) {
            options = defaultOpts(0).build();
        }

        var res = new UqaiExcelResult<T>();
        Consumer<T> validator = a -> {
            try {
                doValidate(a, res); // validaciones x default

                if (consumer != null) { // se agrego un consumer adicional posterior a las validaciones x default
                    consumer.accept(a);
                }
                // se valido correcto agrego al resultado
                res.getResult().add(a);

            } catch (Exception ex) {
                res.getErrors().add("Error " + a.getRowIndex() + " " + ex.getMessage());
            }
        };

        Poiji.fromExcel(file, type, options, validator);
        return res;
    }

    public static <T extends UqaiExcelBase> UqaiExcelResult<T> process(File file, Class<T> type) {
        PoijiOptions options = defaultOpts(0).build();
        return process(file, type, options, null);
    }

    public static <T extends UqaiExcelBase> UqaiExcelResult<T> process(File file, int sheet, Class<T> type) {
        PoijiOptions options = defaultOpts(sheet).build();
        return process(file, type, options, null);
    }

    public static <T extends UqaiExcelBase> UqaiExcelResult<T> process(File file, int sheet, Class<T> type, final Consumer<? super T> consumer) {
        PoijiOptions options = defaultOpts(sheet).build();
        return process(file, type, options, consumer);
    }

    public static PoijiOptions.PoijiOptionsBuilder defaultOpts(int sheet) {
        return PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(sheet).preferNullOverDefault(true).datePattern("dd/MM/yyyy").disableXLSXNumberCellFormat() // fixme error when numbers
                .rawData(true).withCasting(new CustomCasting());
    }


    private static <T> void doValidate(T item, UqaiExcelResult res) {
        // todo by default validate java vlidators
        // custom annotation validator

    }

}
