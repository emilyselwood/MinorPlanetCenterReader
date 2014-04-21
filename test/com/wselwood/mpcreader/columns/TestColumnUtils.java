package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wselwood on 20/04/14.
 */
public class TestColumnUtils {

    @Test
    public void testDecodePackedInt() throws InvalidDataException {

        assertEquals(1995, ColumnUtils.decodePackedInt("J95xxxx".toCharArray(), 0,3));
        assertEquals(12, ColumnUtils.decodePackedInt("C".toCharArray(), 0,1));
        assertEquals(390514, ColumnUtils.decodePackedInt("d0514".toCharArray(), 0,5));
        assertEquals(1, ColumnUtils.decodePackedInt("00001".toCharArray(), 0,5));
    }

    @Test
    public void testOverrun() throws InvalidDataException {
        assertEquals(1995, ColumnUtils.decodePackedInt("J95 ".toCharArray(), 0,4));
    }
}
