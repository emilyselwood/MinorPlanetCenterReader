package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArcLengthModifier {

    @Test
    public void happyPath() throws InvalidDataException {
        Container<Integer> num = new Container<>();
        Container<String> input = new Container<>();
        Container<Integer> arcLength = new Container<>();

        Modifier mod = new ArcLengthModifier(num, input, arcLength);

        num.set(1);
        input.set("1234");
        arcLength.reset();

        mod.process();

        assertEquals(1234, arcLength.get().longValue());
    }

    @Test
    public void doNothingPath() throws InvalidDataException {
        Container<Integer> num = new Container<>();
        Container<String> input = new Container<>();
        Container<Integer> arcLength = new Container<>();

        Modifier mod = new ArcLengthModifier(num, input, arcLength);

        num.set(2);
        input.set("1234");
        arcLength.reset();

        mod.process();

        assertEquals(-1, arcLength.get().longValue());
    }
}
