package me.carc.overpasslib.overpass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bamptonm on 21/08/2017.
 */

public interface OverpassApi {
    @GET("/api/interpreter")
    Call<OverpassQueryResult> interpreter(@Query("data") String data);
}

