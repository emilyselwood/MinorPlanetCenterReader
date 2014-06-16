package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

/**
 * The first two digits of the year are packed into a single character in column 1 (I = 18, J = 19, K = 20).
 * Columns 2-3 contain the last two digits of the year.
 * Column 4 contains the half-month letter and column 7 contains the second letter.
 * The cycle count (the number of times that the second letter has cycled through the alphabet) is coded in columns 5-6,
 * using a letter in column 5 when the cycle count is larger than 99.
 * The uppercase letters are used, followed by the lowercase letters.
 *
 * Created by wselwood on 17/04/14.
 */
public class PackedIdentifierColumn implements Column<String> {

    public PackedIdentifierColumn(int start, int end, Container<String> row) {
        this.start = start;
        this.row = row;
    }

    @Override
    public void process(char[] buffer) throws InvalidDataException {

        String result;
        if(!anyLetters(buffer)) {
            result = String.valueOf(ColumnUtils.decodePackedInt(buffer, start, 6));
        }
        else if(buffer[start+2] >= '0' && buffer[start+2] <= '9') {
            result = String.valueOf(ColumnUtils.decodePackedInt(buffer, start, 3)) + " " + buffer[start + 3] + buffer[start + 6];

            int number = ColumnUtils.decodePackedInt(buffer, 4,2);
            if (number != 0) {
                result = result + String.valueOf(number);
            }
        }
        else {
            int number = ColumnUtils.decodePackedInt(buffer, 3, 4);

            result = String.valueOf(number) + ' ' + buffer[start] + '-' + buffer[start+1];
        }
        row.set(result);
    }

    private boolean anyLetters(char[] buffer) {
        for(int i = 1; i < 6; i++) {
            if(buffer[start + i] != ' ' && (buffer[start + i] < '0' || buffer[start + i] > '9')) {
                return true;
            }
        }
        return false;
    }

    private final int start;

    private final Container<String> row;

}
