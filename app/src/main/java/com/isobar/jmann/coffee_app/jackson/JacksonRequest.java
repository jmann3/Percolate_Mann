package com.isobar.jmann.coffee_app.jackson;

import com.android.volley.toolbox.JsonRequest;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.isobar.jmann.coffee_app.models.SpecificCoffee;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by jmann on 1/24/15.
 */
public class JacksonRequest<T> extends Request<T> {

    private final Class<T> responseType;
    private final Map<String, String> headers;
    //Object requestData;
    private final Response.Listener<T> listener;

    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public JacksonRequest(int method, String url, Class<T> responseType, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        //super(method, url, (requestData == null) ? null: Mapper.string(requestData), listener, errorListener);
        super(method, url, errorListener);
        this.responseType = responseType;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
        //return (headers == null) ? null : Mapper.string(headers);
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try
        {
            Log.d("SplashActivity", "response is " + response.data);
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return  Response.success(Mapper.objectOrThrow(jsonString, responseType), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            Log.e("jacksontest", "error parsing" + e.toString(), e);
        }

        return null;
    }


}
