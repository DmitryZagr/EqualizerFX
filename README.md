# EqualizerFX
---

Equalizer based on [JavaFX 8](http://docs.oracle.com/javase/8/javase-clienttechnologies.htm).


# New Features!
---
  - Added maven assembly.
  - Added Effects: Delay and Overdrive.
 
### Installing and Running
---

#### Requirements

EqualizerFX  runs on any system equipped with the Java Virtual Machine (1.8 or newer), which can be downloaded at no cost from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html).
[Maven](https://maven.apache.org/index.html) to assembly.

#### Running

```sh
$ java -jar <path to jar>
```

### Building EqualizerFX From Source
---

To compile EqualizerFX from source, you need a Java compiler supporting Java 1.8 and JAVA_HOME pointing to this JDK.
You can compile EqualizerFX with [Maven](https://maven.apache.org/index.html).

```sh
$ git clone https://github.com/DmitryZagr/EqualizerFX.git 
$ cd EqualizerFX
$ mvn clean jfx:jar
$ java -jar target/jfx/app/EqualizerFX-1.0-SNAPSHOT-jfx.jar
```

### References
   - Java FX [tutorials](http://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm).
   - Maven [tutorial](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).
   - Editor for creating [README.md](http://dillinger.io).