//package com.example.eventfinder;
//
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class Api {
//    private static Retrofit.Builder retrofitBuilder= new Retrofit.Builder().baseUrl("https://api.ipify.org/?format=json").addConverterFactory(GsonConverterFactory.create());
//    private static Retrofit retrofit=retrofitBuilder.build();
//    private static ipApi ipapi=retrofit.create(ipApi.class);
//
//    public static ipApi getIpapi() {
//        return ipapi;
//    }
//}
package com.example.eventfinder;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
/**
 * Created by MG on 04-03-2018.
 */
public class Api {
    private static Api mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    public Api(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized Api getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Api(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public static void get_ip(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://api.ipify.org/?format=json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void getip_loc(Context ctx, String ticker, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://ipinfo.io/"+ticker+"?token=69d7c33c0cc0d1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void getgeohash(Context ctx, String ticker, String ticker2, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = "https://mywork8.wn.r.appspot.com/geohash/"+ticker+"/"+ticker2;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
   // app.get("/getall/:key/:cata/:dis/:geo", async function (req, res) {
   public static void getevents(Context ctx, String key, String cata, String dis, String geo, Response.Listener<String>
           listener, Response.ErrorListener errorListener) {
        if(cata=="All"){
            cata="Default";
        }
       String url = "https://mywork8.wn.r.appspot.com/getall/"+key+"/"+cata+"/"+dis+"/"+geo;
       Log.d(url, "getevents: ");
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               listener, errorListener);
       Api.getInstance(ctx).addToRequestQueue(stringRequest);
   }
    public static void loc_hash(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        String url = "https://mywork8.wn.r.appspot.com/loc/"+key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void detail(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        String url = "https://mywork8.wn.r.appspot.com/getdetail/"+key;
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void spotify(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        String url = "https://mywork8.wn.r.appspot.com/callspotify/"+key;
      //  Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void venue(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        String url = "https://mywork8.wn.r.appspot.com/callvenue/"+key;
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void album(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {

        String url = "https://mywork8.wn.r.appspot.com/callalbumspotify/"+key;
        Log.d("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
    }
    public static void auto_sug(Context ctx, String key, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String apikey = "SW0g1E4NAR9An8lRglSfhZzZSHubGIEJ";
        String url = "https://app.ticketmaster.com/discovery/v2/suggest?apikey=SW0g1E4NAR9An8lRglSfhZzZSHubGIEJ&keyword="+key;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        Api.getInstance(ctx).addToRequestQueue(stringRequest);
      //  rl = `https://app.ticketmaster.com/discovery/v2/suggest?apikey=${apikey}&keyword=${STS}`;
//        if (result["_embedded"]) {
//            let emd = result["_embedded"];
//            if (emd["attractions"]) {
//                let att = emd["attractions"];
//                for (const events of att) {
//                    ans.push(events["name"]);
//                }
//            }
//        }
    }
}