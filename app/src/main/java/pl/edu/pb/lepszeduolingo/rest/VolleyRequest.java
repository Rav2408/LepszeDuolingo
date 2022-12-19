package pl.edu.pb.lepszeduolingo.rest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {
    private static VolleyRequest instance;
    private RequestQueue requestQueue;
    private Context context;
    private ImageLoader imageLoader;
    private IVolley iVolley;

    private VolleyRequest(Context context, IVolley iVolley) {
        this.context = context;
        this.iVolley = iVolley;
        requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(10);

            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private VolleyRequest(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(10);

            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized VolleyRequest getInstance(Context context) {
        instance = new VolleyRequest(context);
        return instance;
    }

    public static synchronized VolleyRequest getInstance(Context context, IVolley iVolley) {
        instance = new VolleyRequest(context, iVolley);
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    //GET Method
    public void getRequest(String url) {
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) { //TODO jeśli uderza się na endpoint który zwraca liste obiektów to działa
                        iVolley.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //iVolley.onResponse(error.getMessage());
                error.printStackTrace();
            }

        });
        addToRequestQueue(getRequest);
    }

    //POST Method with Params
    public void postRequest(String url,JSONObject jsonObject){
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        })
        {
            @Override
            public byte[] getBody(){
                return jsonObject.toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        addToRequestQueue(postRequest);
    }


    // //PUT Method with Params
    public void putRequest(String url, JSONObject jsonObject){
        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iVolley.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iVolley.onResponse(error.getMessage());
            }
        }){
            @Override
            public byte[] getBody(){
                return jsonObject.toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        addToRequestQueue(putRequest);
    }

//    {
//        @Nullable
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//        Map<String, String> params = new HashMap<>();
//        params.put("id","1");
//        params.put("name","Ala");
//        return params;
//    }


//
//    //PATCH Method with Params
//    public void patchRequest(String url){
//        StringRequest patchRequest = new StringRequest(Request.Method.PATCH,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        iVolley.onResponse(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                iVolley.onResponse(error.getMessage());
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("name","Ala");
//                params.put("value","Siema");
//                return params;
//            }
//        };
//        addToRequestQueue(patchRequest);
//    }
//
    //DELETE Method
    public void deleteRequest(String url){
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iVolley.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        addToRequestQueue(deleteRequest);
    }
}
