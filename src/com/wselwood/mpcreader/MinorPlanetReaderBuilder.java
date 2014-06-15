package com.wselwood.mpcreader;

import com.wselwood.mpcreader.columns.*;
import com.wselwood.mpcreader.modifiers.ArcLengthModifier;
import com.wselwood.mpcreader.modifiers.Modifier;
import com.wselwood.mpcreader.modifiers.RadianModifier;
import com.wselwood.mpcreader.modifiers.YearOfObservationModifier;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Simple builder pattern to create a minor planet reader.
 *
 * Mainly done this way so we can add more options in later with out having to make breaking changes to the api.
 *
 * Created by wselwood on 27/04/14.
 */
public class MinorPlanetReaderBuilder {

    enum FileType {
        NUMBERED,
        UNNUMBERED,
        DETECT
    }



    private File target = null;
    private FileType fileType = null;

    private Boolean compressed = null;

    private boolean convertToRaidians = false;

    private final List<Column> columns = new ArrayList<>();
    private final List<Modifier> modifiers = new ArrayList<>();
    private final Map<String, Container> values = new HashMap<>();


    /**
     * Create a new builder. Builders are one shot and should not be reused.
     */
    public MinorPlanetReaderBuilder() {

    }

    /**
     * select the file for the reader to open.
     * @param f the file to open.
     */
    public MinorPlanetReaderBuilder open(File f) {
        target = f;
        return this;
    }

    /**
     * Setup the reader to read the numbered file format.
     *
     * The first column of the file will be a packed int.
     */
    public MinorPlanetReaderBuilder numberedFile() {
        fileType = FileType.NUMBERED;
        return this;
    }

    /**
     * Setup the reader to read the unnumbered file format.
     *
     * The first column of the file will be a packed identifier.
     */
    public MinorPlanetReaderBuilder unNumberedFile() {
        fileType = FileType.UNNUMBERED;
        return this;
    }

    public MinorPlanetReaderBuilder detectNumbering() {
        fileType = FileType.DETECT;
        return this;
    }

    public MinorPlanetReaderBuilder compressed() {
        compressed = true;
        return this;
    }

    public MinorPlanetReaderBuilder unCompressed() {
        compressed = false;
        return this;
    }

    /**
     * Should angles in the file be converted to radians.
     */
    public MinorPlanetReaderBuilder convertAngles() {
        convertToRaidians = true;
        return this;
    }

    /**
     * Construct the final MinorPlanetReader.
     *
     * Once this has been called this class should not be reused.
     *
     * @return the reader.
     * @throws IOException if there is any problem opening the file.
     */
    public MinorPlanetReader build() throws IOException {

        if(compressed == null) {
            detectCompression();
        }

        if(fileType == FileType.DETECT || fileType == null) {
            detectFileType();
        }
        buildColumns();
        buildModifiers();
        return new MinorPlanetReader(target, compressed, columns, modifiers, values);
    }

    private void detectCompression() throws IOException {
        try(FileInputStream bufferedReader = new FileInputStream(target)) {
            byte[] buffer = new byte[3];
            int bytesRead = bufferedReader.read(buffer);
            if(bytesRead != 3) {
                throw new IOException("File appears to be empty");
            }
            // gzip flag on the front.
            compressed = buffer[0] == 31 && buffer[1] == -117;
        }
    }

    /**
     * try and work out what the incoming file is. Look at the first 7 bytes of the file and if any of them
     * are not numbers then it is probably a unNumbered file.
     * @throws IOException
     */
    private void detectFileType() throws IOException {

        try(BufferedReader bufferedReader = buildReader()) {
            char[] buffer = new char[7];
            bufferedReader.read(buffer);
            for (int i = 1; i < 6; i++) {
                if (buffer[i] != ' ' && (buffer[i] < '0' || buffer[i] > '9')) { // entry is not a number
                    fileType = FileType.UNNUMBERED;
                    return;
                }
            }
            fileType = FileType.NUMBERED;
        }

    }

    private BufferedReader buildReader() throws IOException {
        return buildReader(target, compressed);
    }

    public static BufferedReader buildReader(File target, boolean compressed) throws IOException {
        if( compressed) {
            return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(target))));
        }
        else {
            return new BufferedReader(new FileReader(target));
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

        if(fileType == FileType.NUMBERED) {
            Container<Integer> container = new Container<>();
            values.put(ColumnNames.MPC_NUMBER, container);
            columns.add(new PackedIntColumn(0, 5, container));
        }
        else if(fileType == FileType.UNNUMBERED) {
            Container<String> container = new Container<>();
            values.put(ColumnNames.MPC_NUMBER, container);
            columns.add(new PackedIdentifierColumn(0, 6, container));
        }

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

    private void buildModifiers() {
        // add the two modifiers needed to take care of the opposition information.
        Container<Integer> firstYear = new Container<>();
        Container<Integer> lastYear = new Container<>();
        Modifier year = new YearOfObservationModifier(
                values.get(ColumnNames.MPC_NUM_OPPS),
                values.get(ColumnNames.MPC_OPPOSITION),
                firstYear,
                lastYear
                );

        values.put(ColumnNames.MPC_FIRST_YEAR, firstYear);
        values.put(ColumnNames.MPC_LAST_YEAR, lastYear);
        modifiers.add(year);

        Container<Integer> arcLength = new Container<>();
        Modifier arcLengthMod = new ArcLengthModifier(
                values.get(ColumnNames.MPC_NUM_OPPS),
                values.get(ColumnNames.MPC_OPPOSITION),
                arcLength
        );
        values.put(ColumnNames.MPC_ARC_LENGTH, arcLength);
        modifiers.add(arcLengthMod);

        // now if they are needed add the modifiers for converting to radians
        if(convertToRaidians) {
            modifiers.add(new RadianModifier(values.get(ColumnNames.MPC_MEAN_ANOMALY_EPOCH)));
            modifiers.add(new RadianModifier(values.get(ColumnNames.MPC_ARGUMENT_OF_PERIHELION)));
            modifiers.add(new RadianModifier(values.get(ColumnNames.MPC_LONGITUDE)));
            modifiers.add(new RadianModifier(values.get(ColumnNames.MPC_INCLINATION)));
            modifiers.add(new RadianModifier(values.get(ColumnNames.MPC_MOTION)));
        }
    }

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

}
