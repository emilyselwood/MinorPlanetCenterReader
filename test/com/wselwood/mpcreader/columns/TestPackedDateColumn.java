package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Created by wselwood on 17/04/14.
 */
public class TestPackedDateColumn {

    @Test
    public void test19thCenturyDate() throws InvalidDataException {
        Container<Date> container = new Container<>();
        PackedDateColumn col = new PackedDateColumn(3,7,container);

        col.process("   I23AP    ".toCharArray());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // dates suck
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        assertEquals("1823-10-25", sdf.format(container.get()));
    }

    @Test
    public void test20thCenturyDate() throws InvalidDataException {
        Container<Date> container = new Container<>();
        PackedDateColumn col = new PackedDateColumn(3,7,container);

        col.process("   J2319    ".toCharArray());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        assertEquals("1923-01-09", sdf.format(container.get()));
    }

    @Test
    public void test21stCenturyDate() throws InvalidDataException {
        Container<Date> container = new Container<>();
        PackedDateColumn col = new PackedDateColumn(3,7,container);

        col.process("   K09CA    ".toCharArray());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        assertEquals("2009-12-10", sdf.format(container.get()));
    }
}
