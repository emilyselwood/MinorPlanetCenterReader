package com.wselwood.mpcreader;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Created by wselwood on 18/04/14.
 */
public class TestUnnumberedMinorPlanetReader {


    @Test
    public void testBasicLoad() throws IOException, InvalidDataException {

        String filePath = this.getClass().getResource("unnumbered_test1.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        assert(reader.hasNext());
        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());

        reader.close();
        verify1960SW(result);

    }


    @Test
    public void testLetterUncertaintyParameter() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("LetterUncertaintyParameter.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        assert(reader.hasNext());

        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());
        assertEquals("E", result.getUncertaintyParameter());

    }

    @Test
    public void testMissingMagnitude() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("MissingMagnitude.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        assert(reader.hasNext());

        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());
        assertEquals(null, result.getAbsoluteMagnitude());
        assertEquals("2010 AV83", result.getNumber());

    }

    @Test
    public void testMissingRMS() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("MissingRMSResidual.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        assert(reader.hasNext());

        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());
        assertEquals(null, result.getrMSResidual());
        assertEquals("1927 LA", result.getNumber());

    }

    @Test
    public void testMissingNumberOfObservations() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("MissingNumberOfObservations.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        assert(reader.hasNext());

        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());
        assertEquals(null, result.getNumberOfObservations());
        assertEquals("1994 EJ", result.getNumber());

    }

    private void verify1960SW(MinorPlanet result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals("1960 SW", result.getNumber());
        assertEquals(17.0, result.getAbsoluteMagnitude(), 0.0);
        assertEquals(0.15, result.getSlope(), 0.0);

        assertEquals("2014-05-23", sdf.format(result.getEpoch()));

        assertEquals(255.86615, result.getMeanAnomalyEpoch(), 0.0);
        assertEquals(114.94248, result.getArgumentOfPerihelion(), 0.0);
        assertEquals(267.69489, result.getLongitudeOfTheAscendingNode(), 0.0);
        assertEquals(1.06877, result.getInclinationToTheEcliptic(), 0.0);
        assertEquals(0.2005979, result.getOrbitalEccentricity(), 0.0);
        assertEquals(0.23425776, result.getMeanDailyMotion(), 0.0);
        assertEquals(2.6061939, result.getSemimajorAxis(), 0.0);

        assertEquals("1", result.getUncertaintyParameter());
        assertEquals("MPO157334", result.getReference());

        assertEquals(75, result.getNumberOfObservations().intValue());
        assertEquals(5L, result.getNumberOfOppositions().intValue());

        assertEquals(1960, result.getYearOfFirstObservation());
        assertEquals(2009, result.getYearOfLastObservation());

        assertEquals(0.43, result.getrMSResidual(), 0.0);
        assertEquals("M-v", result.getCoarseIndicatorOfPerturbers());
        assertEquals("38h", result.getPreciseIndicatorOfPerturbers());

        assertEquals("MPCADO", result.getComputerName());

        // test the hex flags (400)
        assertEquals(1024, result.getHexDigitFlags());
        assertNull(result.getOrbitType());
        assertFalse(result.isNearEarthObject());
        assertFalse(result.isLargeNearEarthObject());
        assertFalse(result.isOneOppositionObjectSeenAtEarlierOpposition());
        assertFalse(result.isCriticalListNumberedObject());
        assertFalse(result.isPotentiallyHazardousAsteroid());


        assertEquals("1960 SW", result.getReadableDesignation());

        assertEquals("2009-04-21", sdf.format(result.getDateOfLastObservation()));

    }
}
