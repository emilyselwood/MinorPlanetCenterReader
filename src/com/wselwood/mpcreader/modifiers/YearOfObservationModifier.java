package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;

/**
 * Created by wselwood on 03/05/14.
 */
public class YearOfObservationModifier implements Modifier {

    public YearOfObservationModifier(Container<Integer> num, Container<String> input, Container<Integer> firstYear, Container<Integer> lastYear) {
        this.num = num;
        this.input = input;
        this.firstYear = firstYear;
        this.lastYear = lastYear;
    }

    @Override
    public void process() throws InvalidDataException {
        if(num.get() > 1) {
            firstYear.set(Integer.parseInt(input.get().substring(0, 4)));
            lastYear.set(Integer.parseInt(input.get().substring(5, 9)));
        }
        else {
            firstYear.set(-1);
            lastYear.set(-1);
        }
    }

    private final Container<Integer> num;
    private final Container<String> input;
    private final Container<Integer> firstYear;
    private final Container<Integer> lastYear;
}
