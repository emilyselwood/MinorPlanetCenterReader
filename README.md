# Minor Planet Center Reader #

## Overview ##
This java library provides a way to read the Minor Planet Catalogues provided by the Minor Planets Center.

## Setup ##

Currently we don't have a maven repo hosting this so you will need to build the jars your self.
```
$ git clone https://github.com/wselwood/MinorPlanetCenterReader.git
$ cd MinorPlanetCenterReader
$ ./gradlew jar
```

The jar will end up in ./build/libs/

If you want source and java doc jars you can run ```./gradlew javadocJar``` and ```./gradlew sourcesJar```

Place the jar in your applications class path as with any other dependency not pulled from maven.

## Usage ##

There are two different files that are provided by the Minor Planets Center. The numbered Minor Planets and the un-numbered minor planets.

Each of these files has a different reader class. ```com.wselwood.mpcreader.NumberedMinorPlanetReader``` and ```com.wselwood.mpcreader.UnnumberedMinorPlanetReader``` Both classes take a file object on creation and extend ```com.wselwood.mpcreader.MinorPlanetReader```

The reader class provides two methods ```hasNext()``` which returns true if there are more records in the file. and ```next()``` which returns the next record.

Both these methods can throw IOExceptions and the next method can throw ```com.wselwood.mpcreader.InvalidDataException``` if the record will not parse for some reason. If you get this happen I suggest you re-download the file as it is probably corrupt.

The next method from both classes returns a ```com.wselwood.mpcreader.MinorPlanet``` object which contains the decoded details for a minor planet. This class is immutable. You are not expected to ever want to construct these your self.

Finally there is a ```close()``` method on the reader which will close down the file handle.

## Example ##

```
import com.wselwood.mpcreader.MinorPlanetReader;
import com.wselwood.mpcreader.NumberedMinorPlanetReader;
import com.wselwood.mpcreader.MinorPlanet;
import com.wselwood.mpcreader.InvalidDataException;

public class example {
    public static void main(string[] args) {
        try {
            MinorPlanetReader reader = new NumberedMinorPlanetReader(new File("./mpn.txt"));
            while(reader.hasNext()) {
                MinorPlanet mp = reader.next();
                System.out.println(mp.getNumber() + " : " + mp.getReadableDesignation());
            }
            reader.close();
        } catch (IOException | InvalidDataException e) {
            e.printStackTrace();
        }
    }
}
```
