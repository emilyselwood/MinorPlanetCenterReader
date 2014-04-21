package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

/**
 * Created by wselwood on 21/04/14.
 */
public class PackedIntColumn implements Column<Integer> {

    public PackedIntColumn(int start, int end, Container<Integer> row) {
        this.start = start;
        this.count = end - start;
        this.row = row;
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {
        int value = ColumnUtils.decodePackedInt(buffer, start, count);
        row.set(value);
    }

    private final int start;
    private final int count;
    private final Container<Integer> row;

}
