package me.carc.overpass;

import org.osmdroid.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;

import me.carc.overpasslib.overpass.OverpassQueryResult;
import me.carc.overpasslib.overpass.OverpassServiceProvider;
import me.carc.overpasslib.query.OverpassFilterQuery;
import me.carc.overpasslib.query.OverpassQuery;

import static me.carc.overpasslib.output.OutputFormat.JSON;


/**
 * Call to Retrofit
 * Created by bamptonm on 21/08/2017.
 */
public class MapOverpassAdapter {

    public MapOverpassAdapter() {
    }

    public OverpassQueryResult searchamenity(String amenity, final BoundingBox box) {
        OverpassQuery query = new OverpassQuery()
                .format(JSON)
                .timeout(30)
                .filterQuery()
                .node()
                .amenity(amenity)
                .tagNot("access", "private")
                .boundingBox(
                        box.getLatSouth(),
                        box.getLonWest(),
                        box.getLatNorth(),
                        box.getLonEast()
                )
                .end()
                .output(100);

        return interpret(query.build());
    }


    public OverpassQueryResult search(HashMap<String, String> tags, BoundingBox box) {

        OverpassQuery query = new OverpassQuery()
                .format(JSON)
                .timeout(10)
//                ;
                .boundingBox(
                        box.getLatSouth(),
                        box.getLonWest(),
                        box.getLatNorth(),
                        box.getLonEast());

        OverpassFilterQuery filter = new OverpassFilterQuery(query);

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            filter.node();
            filter.custom(entry.getValue(), entry.getKey());  //  NOTICE: the HashMap is switched from normal use
//            filter.boundingBox(
//                    box.getLatSouth(),
//                    box.getLonWest(),
//                    box.getLatNorth(),
//                    box.getLonEast());
            filter.prepareNext();
        }
        filter.end().output(100);

        return interpret(query.build());
    }

    private OverpassQueryResult interpret(String query) {
        try {

            return OverpassServiceProvider.get().interpreter(query).execute().body();

        } catch (Exception e) {
            e.printStackTrace();

            return new OverpassQueryResult();
        }
    }
}