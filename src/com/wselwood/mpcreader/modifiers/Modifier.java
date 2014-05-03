package com.wselwood.mpcreader.modifiers;

import com.wselwood.mpcreader.InvalidDataException;
import com.wselwood.mpcreader.columns.Container;

import java.util.Map;

/**
 * Interface to classes that can modify values in the map before a Minor Planet is constructed.
 * Created by wselwood on 03/05/14.
 */
public interface Modifier {

    /**
     * Apply the modification to the containers collected during construction.
     *
     * The containers should be collected during construction.
     *
     * @throws InvalidDataException if there is a problem converting the data
     */
    public void process() throws InvalidDataException;

}
