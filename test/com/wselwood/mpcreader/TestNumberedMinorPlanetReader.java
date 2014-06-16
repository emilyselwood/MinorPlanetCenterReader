package com.wselwood.mpcreader;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Simple test the the load works.
 *
 * Created by wselwood on 15/04/14.
 */
public class TestNumberedMinorPlanetReader {

    @Test
    public void testBasicLoad() throws IOException, InvalidDataException {

        String filePath = this.getClass().getResource("test1.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        assert(reader.hasNext());
        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());

        reader.close();
        verifyCeres(result);
    }

    @Test
    public void testMultiRecords() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("test10.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        List<MinorPlanet> result = new ArrayList<>();

        while(reader.hasNext()) {
            result.add(reader.next());
        }

        assertEquals(10, result.size());
        verifyCeres(result.get(0));
    }

    @Test
    public void testCompressedMultiRecords() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("test10compressed.txt.gz").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).build();

        List<MinorPlanet> result = new ArrayList<>();

        while(reader.hasNext()) {
            result.add(reader.next());
        }

        assertEquals(10, result.size());
        verifyCeres(result.get(0));
    }

    @Test
    public void testToldCompressedMultiRecords() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("test10compressed.txt.gz").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).compressed().build();

        List<MinorPlanet> result = new ArrayList<>();

        while(reader.hasNext()) {
            result.add(reader.next());
        }

        assertEquals(10, result.size());
        verifyCeres(result.get(0));
    }

    @Test
    public void testMissingUncertaintyParameter() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("MissingUncertaintyParameter.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).unCompressed().build();

        assert(reader.hasNext());

        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());
        assertEquals("", result.getUncertaintyParameter());

    }

    @Test
    public void testHeadersAndBlankLines() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("HeaderAndBlankLines.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder().open(new File(filePath)).unCompressed().build();

        List<MinorPlanet> result = new ArrayList<>();

        while(reader.hasNext()) {
            result.add(reader.next());
        }

        assertEquals(10, result.size());
        verifyCeres(result.get(0));

    }

    private void verifyCeres(MinorPlanet result) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        assertEquals("1", result.getNumber());
        assertEquals(3.34, result.getAbsoluteMagnitude(), 0.0);
        assertEquals(0.12, result.getSlope(), 0.0);

        assertEquals("2013-11-04", sdf.format(result.getEpoch()));

        assertEquals(10.55761, result.getMeanAnomalyEpoch(), 0.0);
        assertEquals(72.29213, result.getArgumentOfPerihelion(), 0.0);
        assertEquals(80.32762, result.getLongitudeOfTheAscendingNode(), 0.0);
        assertEquals(10.59398, result.getInclinationToTheEcliptic(), 0.0);
        assertEquals(0.0757973, result.getOrbitalEccentricity(), 0.0);
        assertEquals(0.21415869, result.getMeanDailyMotion(), 0.0);
        assertEquals(2.7668073, result.getSemimajorAxis(), 0.0);

        assertEquals("0", result.getUncertaintyParameter());
        assertEquals("MPO286777", result.getReference());

        assertEquals(6502, result.getNumberOfObservations().intValue());
        assertEquals(105, result.getNumberOfOppositions().intValue());

        assertEquals(1802, result.getYearOfFirstObservation());
        assertEquals(2014, result.getYearOfLastObservation());

        assertEquals(0.82, result.getrMSResidual(), 0.0);
        assertEquals("M-v", result.getCoarseIndicatorOfPerturbers());
        assertEquals("30h", result.getPreciseIndicatorOfPerturbers());

        assertEquals("MPCLINUX", result.getComputerName());

        // test the hex flags (being all blank every thing should be false.)
        assertEquals(0, result.getHexDigitFlags());
        assertNull(result.getOrbitType());
        assertFalse(result.isNearEarthObject());
        assertFalse(result.isLargeNearEarthObject());
        assertFalse(result.isOneOppositionObjectSeenAtEarlierOpposition());
        assertFalse(result.isCriticalListNumberedObject());
        assertFalse(result.isPotentiallyHazardousAsteroid());


        assertEquals("(1) Ceres", result.getReadableDesignation());

        assertEquals("2014-03-07", sdf.format(result.getDateOfLastObservation()));

    }

}
