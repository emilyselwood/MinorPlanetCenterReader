package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

/**
 * Created by wselwood on 15/04/14.
 */
public class FloatColumn implements Column<Double> {

    public FloatColumn(int start, int end, Container<Double> row) {
        this.start = start;
        this.count = end - start;
        this.row = row;
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {
        String value = new String(buffer, start, count).trim();
        if(!value.isEmpty()) {
            row.set(Double.parseDouble(value));
        }
    }

    private final int start;
    private final int count;
    private final Container<Double> row;
}
