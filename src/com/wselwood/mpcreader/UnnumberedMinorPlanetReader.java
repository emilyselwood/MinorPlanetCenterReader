package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.ColumnNames;
import com.wselwood.mpcreader.columns.Container;
import com.wselwood.mpcreader.columns.PackedIdentifierColumn;

import java.io.File;
import java.io.IOException;

/**
 * A MinorPlanetReader for reading the numbered version of the minor planet center catalogue.
 *
 * This version of the file has a packed identifier as the first column.
 * See the PackedIdentifierColumn for more info.
 *
 * Created by wselwood on 18/04/14.
 */
public class UnnumberedMinorPlanetReader extends MinorPlanetReader {

    /**
     * @param input a file object pointing to the catalogue to process.
     * @throws IOException if there is a problem opening the file.
     */
    public UnnumberedMinorPlanetReader(File input) throws IOException {
        super(input);
    }

    @Override
    protected void addFirstColumn() {
        Container<String> container = new Container<>();
        values.put(ColumnNames.MPC_NUMBER, container);
        columns.add(new PackedIdentifierColumn(0, 6, container));
    }
}
