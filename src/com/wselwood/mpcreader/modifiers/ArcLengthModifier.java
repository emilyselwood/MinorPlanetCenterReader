package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;

/**
 * Created by wselwood on 03/05/14.
 */
public class ArcLengthModifier implements Modifier {

    public ArcLengthModifier(Container<Integer> num, Container<String> input, Container<Integer> arcLength) {
        this.num = num;
        this.input = input;
        this.arcLength = arcLength;
    }

    @Override
    public void process() throws InvalidDataException {
        if(num.get() <= 1) {
            arcLength.set(Integer.parseInt(input.get().substring(0, 4)));
        }
        else {
            arcLength.set(-1);
        }
    }

    private final Container<Integer> num;
    private final Container<String> input;
    private final Container<Integer> arcLength;

}
