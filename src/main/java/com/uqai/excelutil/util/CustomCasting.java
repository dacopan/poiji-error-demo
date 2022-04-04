package com.uqai.excelutil.util;

import com.poiji.config.DefaultCasting;
import com.poiji.config.DefaultCastingError;
import com.poiji.option.PoijiOptions;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dacopan on 19/3/22
 */
@Log4j2
public class CustomCasting extends DefaultCasting {

    private final boolean errorLoggingEnabled;

    private final List<DefaultCastingError> errors = new ArrayList<>();

    public CustomCasting() {
        this(false);
    }

    public CustomCasting(boolean errorLoggingEnabled) {
        this.errorLoggingEnabled = errorLoggingEnabled;
    }

    @Override
    protected Object getValueObject(Field field, int row, int col, PoijiOptions options, String rawValue, Class<?> fieldType) {
        String sheetName = options.getSheetName();
        String value = options.trimCellValue() ? rawValue.trim() : rawValue;

        if (fieldType == ZonedDateTime.class) {
            return zonedDateTimeValue(value, sheetName, row, col, options);
        }

        if (fieldType == BigDecimal.class) {
            return bigDecimalValue(value, sheetName, row, col, options);
        }

        return super.getValueObject(field, row, col, options, rawValue, fieldType);
    }

    private ZonedDateTime zonedDateTimeValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            // todo
            return null;
        } catch (NumberFormatException nfe) {
            return onErrorCustom(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : ZonedDateTime.now());
        }
    }

    private BigDecimal bigDecimalValue(String value, String sheetName, int row, int col, PoijiOptions options) {
        try {
            // todo
            return null;
        } catch (NumberFormatException nfe) {
            return onErrorCustom(value, sheetName, row, col, nfe, options.preferNullOverDefault() ? null : BigDecimal.ZERO);
        }
    }

    private <T> T onErrorCustom(String value, String sheetName, int row, int col, Exception exception, T defaultValue) {
        logErrorCustom(value, defaultValue, sheetName, row, col, exception);
        return defaultValue;
    }

    private void logErrorCustom(String value, Object defaultValue, String sheetName, int row, int col, Exception exception) {
        if (errorLoggingEnabled) {
            //errors.add(new DefaultCastingError(value, defaultValue, sheetName, row, col, exception));
            log.error("row {} col {} err: {}", row, col, exception.getMessage());
        }
    }
}
