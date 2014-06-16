package com.wselwood.mpcreader;

import java.io.File;
import java.io.IOException;

/**
 * A very simple example program using the minor planet reader.
 *
 * Expects the first command line argument to be the path to a numbered minor planet file.
 *
 * Records the time taken to read the file in milliseconds, using the System.currentTimeMillis().
 * This is a rather daft micro benchmark don't take any thing from it.
 *
 * Created by wselwood on 18/04/14.
 */
public class Example {

    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Please pass a file name as the first argument");
            return;
        }

        MinorPlanetReader reader = null;
        try {
            // create the reader. Given the file to be loaded.
            reader = new MinorPlanetReaderBuilder()
                    .open(new File(args[0]))
                    .build();

            long start = System.currentTimeMillis(); // record how long this takes.

            int lineNumber = 1;
            while(reader.hasNext()) {   // check to see if we have more data in the file. If so loop.
                MinorPlanet mp = reader.next(); // get the next record.

                // do some thing with the result.
                System.out.println(lineNumber + " > " + mp.getNumber() + " : " + mp.getReadableDesignation());

                lineNumber = lineNumber + 1;
            }

            // now work out how long this actually took
            long end = System.currentTimeMillis();
            System.out.println("processed " + lineNumber + " records in " + (end - start) + " milliseconds");

        } catch (IOException | InvalidDataException e) {
            e.printStackTrace();
        }
        finally {
            if(reader != null) {
                try {
                    reader.close(); // close down the file reader now we are done with it.
                } catch (IOException e) {
                    e.printStackTrace(); // nothing else we can do with this.
                }
            }
        }

    }
}
