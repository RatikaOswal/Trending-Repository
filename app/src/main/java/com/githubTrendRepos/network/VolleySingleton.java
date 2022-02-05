package com.githubTrendRepos.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.cache.DiskLruBasedCache;
import com.android.volley.cache.SimpleImageLoader;
import com.android.volley.toolbox.ImageCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private SimpleImageLoader mImageLoader;
    private static Context mCtx;

    /**
     * Private constructor, only initialization from getInstance.
     *
     * @param context parent context
     *                <p>
     *                <p>
     *                new ImageLoader.ImageCache() {
     *                private final LruCache<String, Bitmap> cache = new DiskLruBasedCache();
     * @Override public Bitmap getBitmap(String url) {
     * return cache.get(url);
     * }
     * @Override public void putBitmap(String url, Bitmap bitmap) {
     * cache.put(url, bitmap);
     * }
     * });
     */
    private VolleySingleton(final Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new SimpleImageLoader(mRequestQueue,
                new ImageCache() {

                    private final DiskLruBasedCache.ImageCacheParams cacheParams = new DiskLruBasedCache.ImageCacheParams(context, "images");
                    private final DiskLruBasedCache cache = new DiskLruBasedCache(cacheParams);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.getBitmap(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.putBitmap(url, bitmap);
                    }

                    @Override
                    public void invalidateBitmap(String url) {

                    }

                    @Override
                    public void clear() {

                    }
                });

    }

    /**
     * Singleton construct design pattern.
     *
     * @param context parent context
     * @return single instance of VolleySingleton
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Get current request queue.
     *
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Add new request depend on type like string, json object, json array request.
     *
     * @param req new request
     * @param <T> request type
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Get image loader.
     *
     * @return ImageLoader
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
