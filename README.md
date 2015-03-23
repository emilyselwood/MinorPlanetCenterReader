# Minor Planet Center Reader #

[![Build Status](https://travis-ci.org/wselwood/MinorPlanetCenterReader.svg?branch=master)](https://travis-ci.org/wselwood/MinorPlanetCenterReader)
[![Maven Central](https://img.shields.io/badge/maven--central-0.1.1-brightgreen.svg)](http://search.maven.org/#artifactdetails|com.github.wselwood|mpc-reader|0.1.1|jar)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/wselwood/MinorPlanetCenterReader/blob/master/LICENSE.md)

## Overview ##
This java library provides a way to read the Minor Planet Catalogues provided by the Minor Planets Center.

This can handle reading either the MPCORB.DAT file, compressed or otherwise and the mpX.txt type files.

## Dependencies ##

We have artifacts hosted on Maven Central. http://search.maven.org/#search|ga|1|mpc-reader 

Gradle:
```compile 'com.github.wselwood:mpc-reader:0.1.1'```

SBT:
```libraryDependencies += "com.github.wselwood" % "mpcreader" % "0.1.1"```

Maven: 
```
<dependency>
    <groupId>com.github.wselwood</groupId>
    <artifactId>mpc-reader</artifactId>
    <version>0.1.1</version>
</dependency>
```

## Example ##

Also see src/com/wselwood/mpcreader/Example.java


```java
import com.wselwood.mpcreader.MinorPlanetReader;
import com.wselwood.mpcreader.MinorPlanetReaderBuilder;
import com.wselwood.mpcreader.MinorPlanet;
import com.wselwood.mpcreader.InvalidDataException;

public class example {
    public static void main(string[] args) {

        MinorPlanetReader reader = null;
        try {
            MinorPlanetReaderBuilder builder = new MinorPlanetReaderBuilder();
            builder.open(new File("./mpn.txt"));

            reader = builder.build();
            while(reader.hasNext()) {
                MinorPlanet mp = reader.next();
                System.out.println(mp.getNumber() + " : " + mp.getReadableDesignation());
            }
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
```

## Usage ##

To construct a reader you first need a ```com.wselwood.mpcreader.MinorPlanetReaderBuilder``` this will allow you to set the needed options on the reader before construction.

The one required option is the file to open set with the ```open(File f)``` method.

If the file is gzip compressed the builder will detect this. It looks at the first two bytes of the file having the gzip header so it doesn't matter if the file does not have the .gz extension. If some how this goes wrong you can set ```compressed()``` or ```unCompressed()``` on the builder to turn on or off compressed reading.

Calling the ```convertAngles()``` method on the builder will convert all the angles in the file into radians rather than degrees. This may be useful if you are doing any orbital calculations as most of the java maths functions take radian angles. Note this will induce a small rounding error from dividing double values.

Finally when you have your builder in the state you want call the ```build()``` method which will construct the ```com.wselwood.mpcreader.MinorPlanetReader``` Note this method can throw an IOException

The reader class provides two methods ```hasNext()``` which returns true if there are more records in the file. and ```next()``` which returns the next record.

Both these methods can throw IOExceptions and the next method can throw ```com.wselwood.mpcreader.InvalidDataException``` if the record will not parse for some reason. If you get this happen I suggest you re-download the file as it is probably corrupt.

The next method returns a ```com.wselwood.mpcreader.MinorPlanet``` object which contains the decoded details for a minor planet. This class is immutable. You are not expected to ever want to construct these your self.

Finally there is a ```close()``` method on the reader which will close down the file handle. This should always be called when you are done with the reader.


## Building ##

If you want to checkout and build the project follow these steps:

```
$ git clone https://github.com/wselwood/MinorPlanetCenterReader.git
$ cd MinorPlanetCenterReader
$ ./gradlew build
```

The jars will end up in ./build/libs/

See the ```./graldew tasks``` for other options.

## Change log ##

v0.1.1 Fix timezone bug in unpacked dates.

v0.1.0 Initial release
