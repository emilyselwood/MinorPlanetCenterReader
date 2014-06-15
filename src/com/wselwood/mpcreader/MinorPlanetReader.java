package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.*;
import com.wselwood.mpcreader.modifiers.Modifier;

import java.io.*;
import java.util.*;

/**
 * The basic part of a reader for the minor planet center catalogues.
 *
 * The only reason this does not implement Iterator is so we can throw IO exceptions on next and hasNext.
 *
 * Created by wselwood on 14/04/14.
 */
public class MinorPlanetReader {


    /**
     * Use the MinorPlanetReaderBuilder to construct this class. You are not expected to call this directly.
     */
    public MinorPlanetReader(File input, boolean compressed, List<Column> columns, List<Modifier> modifiers, Map<String, Container> containers) throws IOException {

        if(!input.exists()) {
            throw new FileNotFoundException("Minor Planet file could not be found");
        }
        else if(!input.isFile()) {
            throw new FileNotFoundException("Minor Planet file is not a file");
        }
        else if(!input.canRead()) {
            throw new IOException("Minor Planet file is not readable");
        }

        this.columns = columns;
        this.modifiers = modifiers;
        this.values = containers;

        buffer = new char[203];

        bufferedReader = MinorPlanetReaderBuilder.buildReader(input, compressed);

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

        for(Modifier m : modifiers) {
            m.process();
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
                (Double) values.get(ColumnNames.MPC_MAGNITUDE).get(),
                (Double) values.get(ColumnNames.MPC_SLOPE).get(),
                (Date)   values.get(ColumnNames.MPC_EPOCH).get(),
                (Double) values.get(ColumnNames.MPC_MEAN_ANOMALY_EPOCH).get(),
                (Double) values.get(ColumnNames.MPC_ARGUMENT_OF_PERIHELION).get(),
                (Double) values.get(ColumnNames.MPC_LONGITUDE).get(),
                (Double) values.get(ColumnNames.MPC_INCLINATION).get(),
                (Double) values.get(ColumnNames.MPC_ECCENTRICITY).get(),
                (Double) values.get(ColumnNames.MPC_MOTION).get(),
                (Double) values.get(ColumnNames.MPC_SEMIMAJOR_AXIS).get(),
                (String) values.get(ColumnNames.MPC_UNCERTAINTY).get(),
                (String) values.get(ColumnNames.MPC_REFERENCE).get(),
                (Integer)values.get(ColumnNames.MPC_NUM_OBS).get(),
                (Integer)values.get(ColumnNames.MPC_NUM_OPPS).get(),
                (Integer)values.get(ColumnNames.MPC_FIRST_YEAR).get(),
                (Integer)values.get(ColumnNames.MPC_LAST_YEAR).get(),
                (Integer)values.get(ColumnNames.MPC_ARC_LENGTH).get(),
                (Double) values.get(ColumnNames.MPC_RESIDUAL).get(),
                (String) values.get(ColumnNames.MPC_COARSE_PERTURBERS).get(),
                (String) values.get(ColumnNames.MPC_PRECISE_PERTURBERS).get(),
                (String) values.get(ColumnNames.MPC_COMPUTER_NAME).get(),
                (Integer)values.get(ColumnNames.MPC_FLAGS).get(),
                (String) values.get(ColumnNames.MPC_DESIGNATION).get(),
                (Date)   values.get(ColumnNames.MPC_LAST_OBS).get()
           );
    }


    private void resetColumns() {
        for(Container c : values.values()) {
           c.reset();
        }
    }

    private final char[] buffer;
    private final BufferedReader bufferedReader;

    protected final List<Column> columns;
    protected final List<Modifier> modifiers;
    protected final Map<String, Container> values;

}
