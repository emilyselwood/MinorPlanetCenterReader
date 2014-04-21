package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wselwood on 18/04/14.
 */
public class TestPackedIdentifierColumn {

    @Test
    public void testPackedIdentifier() throws InvalidDataException {
        Container<String> container = new Container<>();
        PackedIdentifierColumn column = new PackedIdentifierColumn(0,7, container);

        column.process("J95X00A".toCharArray());
        assertEquals("1995 XA", container.get());

        column.process("J95X01L".toCharArray());
        assertEquals("1995 XL1", container.get());

        column.process("J95F13B".toCharArray());
        assertEquals("1995 FB13", container.get());

        column.process("J98SA8Q".toCharArray());
        assertEquals("1998 SQ108", container.get());

        column.process("J98SA8Q".toCharArray());
        assertEquals("1998 SQ108", container.get());

        column.process("J98SC7V".toCharArray());
        assertEquals("1998 SV127", container.get());

        column.process("J98SG2S".toCharArray());
        assertEquals("1998 SS162", container.get());

        column.process("K99AJ3Z".toCharArray());
        assertEquals("2099 AZ193", container.get());

        column.process("K08Aa0A".toCharArray());
        assertEquals("2008 AA360", container.get());

        column.process("K07Tf8A".toCharArray());
        assertEquals("2007 TA418", container.get());

    }

    @Test
    public void testSurveyDesignation() throws InvalidDataException {
        Container<String> container = new Container<>();
        PackedIdentifierColumn column = new PackedIdentifierColumn(0,7, container);

        column.process("PLS2040".toCharArray());
        assertEquals("2040 P-L", container.get());

        column.process("T1S3138".toCharArray());
        assertEquals("3138 T-1", container.get());

        column.process("T2S1010".toCharArray());
        assertEquals("1010 T-2", container.get());

        column.process("T3S4101".toCharArray());
        assertEquals("4101 T-3", container.get());

    }

}
