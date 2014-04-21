package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Packed date column.
 *
 * The first two digits of the year are packed into a single character in column 1 (I = 18, J = 19, K = 20).
 * Columns 2-3 contain the last two digits of the year.
 * Column 4 contains the month and column 5 contains the day coded in 1-9A-V to cover 1 to 31
 *
 * Created by wselwood on 17/04/14.
 */
public class PackedDateColumn implements Column<Date> {

    public PackedDateColumn(int start, int end, Container<Date> row) {
        this.start = start;
        this.row = row;
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {
        //String value = new String(buffer, start, count).trim();
        int year = ColumnUtils.decodePackedInt(buffer, start, 3);
        int month = ColumnUtils.decodePackedInt(buffer, start + 3, 1);
        int day = ColumnUtils.decodePackedInt(buffer, start + 4, 1);

        Calendar result = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        result.set(year, month-1, day, 0, 0, 0);

        row.set(result.getTime());

    }

    private final int start;

    private final Container<Date> row;


}
