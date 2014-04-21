package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

import java.util.Map;

/**
 * Created by wselwood on 14/04/14.
 */
public class TextColumn implements Column<String> {

    public TextColumn(int start, int end, Container<String> row) {
        this.start = start;
        this.count = end - start;
        this.row = row;
    }
    @Override
    public void process(char[] buffer ) throws InvalidDataException {
        row.set(new String(buffer, start, count).trim());
    }


    private final int start;
    private final int count;
    private final Container<String> row;
}
