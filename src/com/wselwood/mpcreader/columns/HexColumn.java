package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

/**
 * Created by wselwood on 18/04/14.
 */
public class HexColumn implements Column<Integer> {

    public HexColumn(int start, int end, Container<Integer> row) {
        this.start = start;
        this.count = end - start;
        this.row = row;
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {
        String value = new String(buffer, start, count).trim();

        row.set(Integer.parseInt(value, 16));
    }

    private final int start;
    private final int count;
    private final Container<Integer> row;
}