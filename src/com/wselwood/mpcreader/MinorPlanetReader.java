package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.*;

import java.io.*;
import java.util.*;

/**
 * The basic part of a reader for the minor planet center catalogues.
 *
 * The only reason this does not implement Iterator is so we can throw IO exceptions on next and hasNext.
 *
 * Created by wselwood on 14/04/14.
 */
public abstract class MinorPlanetReader {

    public MinorPlanetReader(File input) throws IOException {

        if(!input.exists()) {
            throw new FileNotFoundException("Minor Planet file could not be found");
        }
        else if(!input.isFile()) {
            throw new FileNotFoundException("Minor Planet file is not a file");
        }
        else if(!input.canRead()) {
            throw new IOException("Minor Planet file is not readable");
        }

        columns = new ArrayList<>();
        values = new HashMap<>();
        buildColumns();


        buffer = new char[203];

        bufferedReader = new BufferedReader(new FileReader(input));

    }

    /**
     * Is there any more data in the file to be processed?
     * @return true if there is at least one more record.
     * @throws IOException if the file reader is in a bad state.
     */
    public boolean hasNext() throws IOException {
        return bufferedReader.ready();
    }

    /**
     * Get the next record out of the minor planet file.
     *
     * This will read the line from the file and decode it.
     *
     * @return a Minor Planet object representing the record from the file
     * @throws IOException if the file reading fails for some reason.
     * @throws InvalidDataException if the record read from the file is invalid.
     */
    public MinorPlanet next() throws IOException, InvalidDataException {

        resetColumns();

        int length = bufferedReader.read(buffer);

        if(length != 203) {
            throw new InvalidDataException("Row of incorrect length");
        }

        if(buffer[202] != '\n') {
            throw new InvalidDataException("Row does not end with new line");
        }

        for(Column c : columns) {
            c.process(buffer);
        }

        return constructMinorPlanet();
    }

    /**
     * close down this read and clean up the file handle.
     * @throws IOException if closing the file reader fails for some reason.
     */
    public void close() throws IOException {
        bufferedReader.close();
    }

    private MinorPlanet constructMinorPlanet() {
        // casting due to the compiler not being able to understand each map entry having different generic types.
        return new MinorPlanet(
                (String) values.get(ColumnNames.MPC_NUMBER).get().toString(),
                (double) values.get(ColumnNames.MPC_MAGNITUDE).get(),
                (double) values.get(ColumnNames.MPC_SLOPE).get(),
                (Date)   values.get(ColumnNames.MPC_EPOCH).get(),
                (double) values.get(ColumnNames.MPC_MEAN_ANOMALY_EPOCH).get(),
                (double) values.get(ColumnNames.MPC_ARGUMENT_OF_PERIHELION).get(),
                (double) values.get(ColumnNames.MPC_LONGITUDE).get(),
                (double) values.get(ColumnNames.MPC_INCLINATION).get(),
                (double) values.get(ColumnNames.MPC_ECCENTRICITY).get(),
                (double) values.get(ColumnNames.MPC_MOTION).get(),
                (double) values.get(ColumnNames.MPC_SEMIMAJOR_AXIS).get(),
                (String) values.get(ColumnNames.MPC_UNCERTAINTY).get(),
                (String) values.get(ColumnNames.MPC_REFERENCE).get(),
                (int)    values.get(ColumnNames.MPC_NUM_OBS).get(),
                (int)    values.get(ColumnNames.MPC_NUM_OPPS).get(),
                (String) values.get(ColumnNames.MPC_OPPOSITION).get(),
                (double) values.get(ColumnNames.MPC_RESIDUAL).get(),
                (String) values.get(ColumnNames.MPC_COARSE_PERTURBERS).get(),
                (String) values.get(ColumnNames.MPC_PRECISE_PERTURBERS).get(),
                (String) values.get(ColumnNames.MPC_COMPUTER_NAME).get(),
                (int)    values.get(ColumnNames.MPC_FLAGS).get(),
                (String) values.get(ColumnNames.MPC_DESIGNATION).get(),
                (Date)   values.get(ColumnNames.MPC_LAST_OBS).get()
           );
    }


    private void resetColumns() {
        for(Container c : values.values()) {
           c.reset();
        }
    }

    // see http://www.minorplanetcenter.net/iau/info/MPOrbitFormat.html
    // column list indexes from 1. need to index from zero for java buffer access.
    // fortran F77 definitions
    // a7 ascii seven characters.
    // f5.2 floating point number five digits long (including the point) two decimal places.
    // f9.5 floating point number nine digits long, five decimal places.
    // i4 integer four digits long.
    private void buildColumns() {

        addFirstColumn();  // delegate out to the sub classes as this is the bit that changes.

        addDouble(ColumnNames.MPC_MAGNITUDE,                8, 13);
        addDouble(ColumnNames.MPC_SLOPE,                    14, 19);
        addPackedDate(ColumnNames.MPC_EPOCH,                20, 25);
        addDouble(ColumnNames.MPC_MEAN_ANOMALY_EPOCH,       26, 35);
        addDouble(ColumnNames.MPC_ARGUMENT_OF_PERIHELION,   37, 46);
        addDouble(ColumnNames.MPC_LONGITUDE,                48, 57);
        addDouble(ColumnNames.MPC_INCLINATION,              59, 68);
        addDouble(ColumnNames.MPC_ECCENTRICITY,             70, 79);
        addDouble(ColumnNames.MPC_MOTION,                   80, 91);
        addDouble(ColumnNames.MPC_SEMIMAJOR_AXIS,           92, 103);
        addString(ColumnNames.MPC_UNCERTAINTY,              105, 106);
        addString(ColumnNames.MPC_REFERENCE,                107, 116);
        addInt   (ColumnNames.MPC_NUM_OBS,                  117, 122, false);
        addInt   (ColumnNames.MPC_NUM_OPPS,                 123, 126, false);
        addString(ColumnNames.MPC_OPPOSITION,               127, 136);  // process this later it depends on the parameter above.
        addDouble(ColumnNames.MPC_RESIDUAL,                 137, 141);
        addString(ColumnNames.MPC_COARSE_PERTURBERS,        142, 145);
        addString(ColumnNames.MPC_PRECISE_PERTURBERS,       146, 149);
        addString(ColumnNames.MPC_COMPUTER_NAME,            150, 160);
        addInt   (ColumnNames.MPC_FLAGS,                    161, 165, true);
        addString(ColumnNames.MPC_DESIGNATION,              166, 194);
        addDate  (ColumnNames.MPC_LAST_OBS,                 194, 202, "yyyyMMdd");

    }

    protected abstract void addFirstColumn();

    private void addString(String name, int start, int end) {
        Container<String> container = new Container<>();
        values.put(name, container);
        columns.add(new TextColumn(start, end, container));
    }

    private void addDouble(String name, int start, int end) {
        Container<Double> container = new Container<>();
        values.put(name, container);
        columns.add(new FloatColumn(start, end, container));
    }

    private void addInt(String name, int start, int end, boolean hex) {
        Container<Integer> container = new Container<>();
        values.put(name, container);
        if(hex) {
            columns.add(new HexColumn(start, end, container));
        }
        else {
            columns.add(new IntColumn(start, end, container));
        }
    }

    private void addDate(String name, int start, int end, String format) {
        Container<Date> container = new Container<>();
        values.put(name, container);
        columns.add(new DateColumn(start, end, format, container));
    }

    private void addPackedDate(String name, int start, int end) {
        Container<Date> container = new Container<>();
        values.put(name, container);
        columns.add(new PackedDateColumn(start, end, container));
    }

    private final char[] buffer;
    private final BufferedReader bufferedReader;

    protected final List<Column> columns;
    protected final Map<String, Container> values;

}
