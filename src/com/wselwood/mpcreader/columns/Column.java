package com.wselwood.mpcreader.columns;

import com.wselwood.mpcreader.InvalidDataException;

import java.util.Map;

/**
 * A column in the file used for during processing.
 *
 * Created by wselwood on 14/04/14.
 */
public interface Column<T> {

    public void process(char[] buffer) throws InvalidDataException;

}
