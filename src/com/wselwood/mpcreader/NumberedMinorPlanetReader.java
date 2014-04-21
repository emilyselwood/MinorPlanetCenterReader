package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.ColumnNames;
import com.wselwood.mpcreader.columns.Container;
import com.wselwood.mpcreader.columns.PackedIntColumn;
import com.wselwood.mpcreader.columns.TextColumn;

import java.io.File;
import java.io.IOException;

/**
 * Created by wselwood on 17/04/14.
 */
public class NumberedMinorPlanetReader extends MinorPlanetReader {


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
