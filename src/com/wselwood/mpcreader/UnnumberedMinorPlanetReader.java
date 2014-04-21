package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.ColumnNames;
import com.wselwood.mpcreader.columns.Container;
import com.wselwood.mpcreader.columns.PackedIdentifierColumn;

import java.io.File;
import java.io.IOException;

/**
 * Created by wselwood on 18/04/14.
 */
public class UnnumberedMinorPlanetReader extends MinorPlanetReader {

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
