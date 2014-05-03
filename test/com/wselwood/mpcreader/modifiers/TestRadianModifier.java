package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRadianModifier {

    @Test
    public void normalPath() throws InvalidDataException {

        Container<Double> value = new Container<>();

        Modifier mod = new RadianModifier(value);

        value.set(10.55761);

        mod.process();

        assertEquals(0.18426505564147855, value.get(), 0.000);

    }
}
