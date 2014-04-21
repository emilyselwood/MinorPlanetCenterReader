package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wselwood on 15/04/14.
 */
public class DateColumn implements Column<Date> {

    public DateColumn(int start, int end, String format, Container<Date> row) {
        this.start = start;
        this.count = end - start;
        this.row = row;
        formatter = new SimpleDateFormat(format);
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {
        String value = new String(buffer, start, count).trim();

        try {
            row.set(formatter.parse(value));
        } catch (ParseException e) {
            throw new InvalidDataException("Could not parse date " + value, e);
        }
    }

    private final int start;
    private final int count;
    private final Container<Date> row;
    private final SimpleDateFormat formatter;

}