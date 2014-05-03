package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;

import java.util.Map;

/**
 * Convert a column into radians from degrees.
 *
 * Created by wselwood on 03/05/14.
 */
public class RadianModifier implements Modifier {

    public RadianModifier(Container<Double> target) {
        this.target = target;
    }

    @Override
    public void process() throws InvalidDataException {
        Double value = target.get();
        if(value != null) {
            target.set(value * conversionFactor);
        }
    }

    private static final Double conversionFactor = Math.PI / 180.0;

    private Container<Double> target;
}
