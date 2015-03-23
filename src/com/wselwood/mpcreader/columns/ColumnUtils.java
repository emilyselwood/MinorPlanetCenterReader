package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

/**
 * Created by wselwood on 17/04/14.
 */
public class ColumnUtils {

    /**
     * Return a decoded int from the buffer using the standard packing scheme.
     * the first digit of the numbers are packed in 0-9A-Za-z
     *
     * zero to nine are themselves.
     * A is 10
     * B is 11
     * Z is 36
     * a is 37
     * b is 38
     * c is 39
     * z is 62
     *
     * the rest of the digits are as normal.
     *
     * so the year 1995 would pack to J95
     * the number 12 would pack to C
     *
     * @param buffer the buffer to pull characters from.
     * @param start the start point of the number we are interested in,
     * @param length the length in characters from the buffer we are interested in.
     * @return the decoded number.
     * @throws InvalidDataException if a character is encounterd out side the valid range
     */
    public static int decodePackedInt(char[] buffer, int start, int length) throws InvalidDataException {
        int result = 0;

        int decimal = 1;
        // loop through all the characters working back until we get to the first one.
        // doing simple integer conversion on them.
        for(int i = start + length - 1; i > start; i--) {
            char working = buffer[i];
            if(working >= '0' && working <= '9') {
                result = result + (working - '0') * decimal;
                decimal = decimal * 10;
            }
            else if(result != 0 || working != ' ') { // accept trailing spaces and just ignore them.
                throw new InvalidDataException("char outside valid range: " + working);
            }
        }

        // the first character is packed.
        char working = buffer[start];
        if(working >= 'a' && working <= 'z') {
            result = result + (working - 'a' + 36) * decimal;
        }
        else if(working >= 'A' && working <= 'Z') {
            result = result + (working - 'A' + 10) * decimal;
        }
        else if(working >= '0' && working <= '9') {
            result = result + (working - '0') * decimal;
        }
        else {
            throw new InvalidDataException("char outside valid range: " + working);
        }


        return result;
    }
}
