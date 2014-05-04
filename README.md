# Minor Planet Center Reader #

## Overview ##
This java library provides a way to read the Minor Planet Catalogues provided by the Minor Planets Center.

## Setup ##

Currently we don't have a maven repo hosting this so you will need to build the jars your self.
```
$ git clone https://github.com/wselwood/MinorPlanetCenterReader.git
$ cd MinorPlanetCenterReader
$ ./gradlew build
```

The jars will end up in ./build/libs/

See the ```./graldew tasks``` for other options.
 
Place the jar in your applications class path as with any other dependency not pulled from maven.

## Usage ##

To construct a reader you first need a ```com.wselwood.mpcreader.MinorPlanetReaderBuilder``` this will allow you to set the needed options on the reader before construction.

The two required options are the file to open set with the ```open(File f)``` method and the type of file set with either ```numberedFile()``` or ```unNumberedFile()```

Other options will be added in the future. We plan to add options for automatic conversion of angle values to radians and other options we find useful.

Finally when you have your builder in the state you want call the ```build()``` method which will construct the ```com.wselwood.mpcreader.MinorPlanetReader``` Note this method can throw an IOException

The reader class provides two methods ```hasNext()``` which returns true if there are more records in the file. and ```next()``` which returns the next record.

Both these methods can throw IOExceptions and the next method can throw ```com.wselwood.mpcreader.InvalidDataException``` if the record will not parse for some reason. If you get this happen I suggest you re-download the file as it is probably corrupt.

The next method returns a ```com.wselwood.mpcreader.MinorPlanet``` object which contains the decoded details for a minor planet. This class is immutable. You are not expected to ever want to construct these your self.

Finally there is a ```close()``` method on the reader which will close down the file handle. This should always be called when you are done with the reader.

## Example ##

Also see src/com/wselwood/mpcreader/Example.java


```
import com.wselwood.mpcreader.MinorPlanetReader;
import com.wselwood.mpcreader.MinorPlanetReaderBuilder;
import com.wselwood.mpcreader.MinorPlanet;
import com.wselwood.mpcreader.InvalidDataException;

public class example {
    public static void main(string[] args) {

        MinorPlanetReader reader = null;
        try {
            MinorPlanetReaderBuilder builder = new MinorPlanetReaderBuilder();
            builder.open(new File("./mpn.txt").numberedFile();

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
