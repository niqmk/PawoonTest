package com.example.michael.pawoontest;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class URLController {
	public interface URLCallback {
		void didURLResponse(final String response);

		void didURLFailed(final int status_code, final String response);
	}

	public interface JSONURLCallback {
		void didURLResponse(final JSONObject response);

		void didURLFailed(final int status_code, final String response);
	}

	public static int default_timeout = 30000;
	private static RequestQueue queue;

	public static void init(final Context context) {
		queue = Volley.newRequestQueue(context);
	}

	public static void request(
			final int type,
			final String url,
			final URLCallback url_callback) {
		URLController.request(type, url, Collections.EMPTY_MAP, Collections.EMPTY_MAP, url_callback);
	}

	public static void requestWithHeaders(
			final int type,
			final String url,
			final Map<String, String> headers,
			final URLCallback url_callback) {
		URLController.request(type, url, headers, Collections.EMPTY_MAP, url_callback);
	}

	public static void requestWithParameters(
			final int type,
			final String url,
			final Map<String, String> parameters,
			final URLCallback url_callback) {
		URLController.request(type, url, Collections.EMPTY_MAP, parameters, url_callback);
	}

	public static void request(
			final int type,
			final String url,
			final Map<String, String> headers,
			final Map<String, String> parameters,
			final URLCallback url_callback) {
		StringRequest request = new StringRequest(type, url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (url_callback != null) {
					url_callback.didURLResponse(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int status_code = 0;
				String response = "";
				if (error.networkResponse != null) {
					status_code = error.networkResponse.statusCode;
					try {
						final NetworkResponse network_response = error.networkResponse;
						response = new String(network_response.data, "UTF-8");
					} catch (UnsupportedEncodingException ex) {
					}
				}
				if (url_callback != null) {
					url_callback.didURLFailed(status_code, response);
				}
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				return headers;
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return parameters;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(
				default_timeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}

	public static void requestRawJSON(
			final int type,
			final String url,
			final JSONObject json,
			final Map<String, String> headers,
			final Map<String, String> parameters,
			final JSONURLCallback url_callback) {
		final JsonObjectRequest request = new JsonObjectRequest(type, url, json, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (url_callback != null) {
					url_callback.didURLResponse(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int status_code = 0;
				String response = "";
				if (error.networkResponse != null) {
					status_code = error.networkResponse.statusCode;
					try {
						final NetworkResponse network_response = error.networkResponse;
						response = new String(network_response.data, "UTF-8");
					} catch (UnsupportedEncodingException ex) {
					}
				}
				if (url_callback != null) {
					url_callback.didURLFailed(status_code, response);
				}
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				return headers;
			}

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return parameters;
			}
		};
		request.setRetryPolicy(new DefaultRetryPolicy(
				default_timeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(request);
	}
}