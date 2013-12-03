package com.koushikdutta.ion.loader;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Loader;
import com.koushikdutta.ion.bitmap.BitmapInfo;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by koush on 11/3/13.
 */
public class PackageIconLoader implements Loader {
    @Override
    public Future<InputStream> load(Ion ion, AsyncHttpRequest request) {
        return null;
    }

    @Override
    public Future<DataEmitter> load(Ion ion, AsyncHttpRequest request, FutureCallback<LoaderEmitter> callback) {
        return null;
    }

    @Override
    public Future<BitmapInfo> loadBitmap(final Ion ion, final String uri) {
        final URI request = URI.create(uri);
        if (request == null || request.getScheme() == null || !request.getScheme().startsWith("package"))
            return null;

        final SimpleFuture<BitmapInfo> ret = new SimpleFuture<BitmapInfo>();
        ion.getBitmapLoadExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String pkg = request.getHost();
                    PackageManager pm = ion.getContext().getPackageManager();
                    Bitmap bmp = ((BitmapDrawable)pm.getPackageInfo(pkg, 0).applicationInfo.loadIcon(pm)).getBitmap();
                    BitmapInfo info = new BitmapInfo();
                    info.bitmaps = new Bitmap[] { bmp };
                    info.loadedFrom =  Loader.LoaderEmitter.LOADED_FROM_CACHE;
                    ret.setComplete(info);
                }
                catch (Exception e) {
                    ret.setComplete(e);
                }
            }
        });

        return ret;
    }
}
