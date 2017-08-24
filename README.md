# Overpass API Interface
[ ![Download](https://api.bintray.com/packages/carcme/overpass/overpasslib/images/download.svg) ]
(https://bintray.com/carcme/overpass/overpasslib/0.0.1)

Modified from [OverpasseR](https://github.com/zsoltk/overpasser). Was having problems with the original so adjusted what was already available



## Gradle

Add this in your project gradle
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://dl.bintray.com/carcme/overpass' }
    }
}
```

and add this in your dependencies block of the app gradle
```groovy
compile 'carcme:overpasslib:0.0.1'
```


### Usage Example
```
        // Separate Bounding Boxes
        OverpassQuery query = new OverpassQuery()
                .format(JSON)
                .timeout(10);

        OverpassFilterQuery filter = new OverpassFilterQuery(query);

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            filter.node();
            filter.custom(entry.getValue(), entry.getKey());  //  NOTICE: the HashMap is switched from normal use
            filter.boundingBox(
                    box.getLatSouth(),
                    box.getLonWest(),
                    box.getLatNorth(),
                    box.getLonEast());
            filter.prepareNext();
        }
        filter.end().output(100);

        return interpret(query.build());
```
or
```
        // Global Bounding Box
        OverpassQuery query = new OverpassQuery()
                .format(JSON)
                .timeout(10)
                .boundingBox(
                        box.getLatSouth(),
                        box.getLonWest(),
                        box.getLatNorth(),
                        box.getLonEast());

        OverpassFilterQuery filter = new OverpassFilterQuery(query);

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            filter.node();
            filter.custom(entry.getValue(), entry.getKey());  //  NOTICE: the HashMap is switched from normal use
            filter.prepareNext();
        }
        filter.end().output(100);

        return interpret(query.build());
```


### License
None - use it how you like

