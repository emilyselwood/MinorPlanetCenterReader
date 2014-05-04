package com.wselwood.mpcreader;


import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestRadianReader {

    @Test
    public void checkNumberedReaderWithRadians() throws IOException, InvalidDataException {

        String filePath = this.getClass().getResource("test1.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder()
                .open(new File(filePath))
                .numberedFile()
                .convertAngles()
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        assert(reader.hasNext());
        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());

        reader.close();

        assertEquals("1", result.getNumber());
        assertEquals(3.34, result.getAbsoluteMagnitude(), 0.0);
        assertEquals(0.12, result.getSlope(), 0.0);

        assertEquals("2013-11-04", sdf.format(result.getEpoch()));

        assertEquals(0.18426505564147855, result.getMeanAnomalyEpoch(), 0.0);
        assertEquals(1.2617356917797684, result.getArgumentOfPerihelion(), 0.0);
        assertEquals(1.4019814492908473, result.getLongitudeOfTheAscendingNode(), 0.0);
        assertEquals(0.18489983189042888, result.getInclinationToTheEcliptic(), 0.0);
        assertEquals(0.0757973, result.getOrbitalEccentricity(), 0.0);
        assertEquals(0.0037377742622578553, result.getMeanDailyMotion(), 0.0);
        assertEquals(2.7668073, result.getSemimajorAxis(), 0.0);

    }

    @Test
    public void checkUnNumberedReaderWithRadians() throws IOException, InvalidDataException {
        String filePath = this.getClass().getResource("unnumbered_test1.txt").getFile();
        MinorPlanetReader reader = new MinorPlanetReaderBuilder()
                .open(new File(filePath))
                .unNumberedFile()
                .convertAngles()
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        assert(reader.hasNext());
        MinorPlanet result = reader.next();

        assertFalse(reader.hasNext());

        reader.close();

        assertEquals("1960 SW", result.getNumber());
        assertEquals(17.0, result.getAbsoluteMagnitude(), 0.0);
        assertEquals(0.15, result.getSlope(), 0.0);

        assertEquals("2014-05-23", sdf.format(result.getEpoch()));

        assertEquals(4.465706761901689, result.getMeanAnomalyEpoch(), 0.0);
        assertEquals(2.006124726407732, result.getArgumentOfPerihelion(), 0.0);
        assertEquals(4.672157221264043, result.getLongitudeOfTheAscendingNode(), 0.0);
        assertEquals(0.018653555446539797, result.getInclinationToTheEcliptic(), 0.0);
        assertEquals(0.2005979, result.getOrbitalEccentricity(), 0.0);
        assertEquals(0.004088569210346672, result.getMeanDailyMotion(), 0.0);
        assertEquals(2.6061939, result.getSemimajorAxis(), 0.0);
    }
}
