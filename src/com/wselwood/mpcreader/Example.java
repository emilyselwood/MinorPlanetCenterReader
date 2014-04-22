package com.wselwood.mpcreader;

import java.io.File;
import java.io.IOException;

/**
 * Created by wselwood on 18/04/14.
 */
public class Example {


    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Please pass a file name as the first argument");
            return;
        }

        try {
            // create the reader. Given the file to be loaded.
            MinorPlanetReader reader = new NumberedMinorPlanetReader(new File(args[0]));

            long start = System.currentTimeMillis(); // record how long this takes.

            int lineNumber = 1;
            while(reader.hasNext()) {   // check to see if we have more data in the file. If so loop.
                MinorPlanet mp = reader.next(); // get the next record.

                // do some thing with the result.
                System.out.println(lineNumber + " > " + mp.getNumber() + " : " + mp.getReadableDesignation());

                lineNumber = lineNumber + 1;
            }
            reader.close(); // close down the file reader now we are done with it.
            long end = System.currentTimeMillis();
            System.out.println("processed " + lineNumber + " records in " + (end - start) + " milliseconds");

        } catch (IOException | InvalidDataException e) {
            e.printStackTrace();
        }

    }
}
