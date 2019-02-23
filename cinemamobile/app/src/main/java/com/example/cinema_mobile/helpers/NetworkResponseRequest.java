package com.example.cinema_mobile.helpers;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONObject;

/**
 * got from
 *
 * https://stackoverflow.com/questions/25271686/how-to-get-status-code-in-successful-response-volley-android
 */

public class NetworkResponseRequest extends JsonRequest<NetworkResponse> {

    /**
     * Constructor
     *
     * @see #NetworkResponseRequest(int, String, JSONObject, Response.Listener, Response.ErrorListener)
     */
    public NetworkResponseRequest(
            int method, String url,
            @Nullable JSONObject jsonRequest,
            Response.Listener<NetworkResponse> listener,
            Response.ErrorListener errorListener) {

        super(method,
                url,
                (jsonRequest == null) ? null : jsonRequest.toString(),
                listener,
                errorListener);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }
}
