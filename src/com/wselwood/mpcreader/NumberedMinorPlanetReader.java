package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.ColumnNames;
import com.wselwood.mpcreader.columns.Container;
import com.wselwood.mpcreader.columns.PackedIntColumn;
import com.wselwood.mpcreader.columns.TextColumn;

import java.io.File;
import java.io.IOException;

/**
 * A MinorPlanetReader for reading the numbered version of the minor planet center catalogue.
 *
 * This version of the file has a packed integer identifier as the first column.
 *
 * Created by wselwood on 17/04/14.
 */
public class NumberedMinorPlanetReader extends MinorPlanetReader {

    /**
     * set up a new number minor planet reader
     * @param input a file object pointing at the file to read.
     * @throws IOException if there is a problem opening the file.
     */
    public NumberedMinorPlanetReader(File input) throws IOException {
        super(input);
    }

    @Override
    protected void addFirstColumn() {

        Container<Integer> container = new Container<>();
        values.put(ColumnNames.MPC_NUMBER, container);
        columns.add(new PackedIntColumn(0, 5, container));

    }


}
