package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestYearOfObservationModifier {

    @Test
    public void happyPath() throws InvalidDataException {
        Container<Integer> num = new Container<>();
        Container<String> input = new Container<>();
        Container<Integer> firstYear = new Container<>();
        Container<Integer> lastYear = new Container<>();

        Modifier mod = new YearOfObservationModifier(num, input, firstYear, lastYear);

        num.set(3);
        input.set("1998-2009");
        firstYear.reset();
        lastYear.reset();

        mod.process();

        assertEquals(1998l, firstYear.get().longValue());
        assertEquals(2009l, lastYear.get().longValue());

    }

    @Test
    public void doNothing() throws InvalidDataException {
        Container<Integer> num = new Container<>();
        Container<String> input = new Container<>();
        Container<Integer> firstYear = new Container<>();
        Container<Integer> lastYear = new Container<>();

        Modifier mod = new YearOfObservationModifier(num, input, firstYear, lastYear);

        num.set(1);
        input.set("1998-2009");
        firstYear.reset();
        lastYear.reset();

        mod.process();

        assertEquals(-1, firstYear.get().longValue());
        assertEquals(-1, lastYear.get().longValue());
    }


}
