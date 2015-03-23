package com.ganatragmail.sagar.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;




public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    Handler mHandler;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());
    Handler mResponseHandler;
    Listener<Token> mListener;

    public interface Listener<Token> {
        void onThumbnailDownloaded(Token token, Bitmap thumbnail);
    }

    public void setListener(Listener<Token> listener){
        mListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @SuppressWarnings("HandlerLeak")
    @Override
    public void onLooperPrepared(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                      if (msg.what ==  MESSAGE_DOWNLOAD) {
                          @SuppressWarnings("unchecked")
                          Token token =(Token)msg.obj;
                          Log.i(TAG, "Got a request for a url:" + requestMap.get(token));
                          handleRequest(token);
                      }

            }

        };
    }



    public void queueThumbnail(Token token, String url){
        Log.i(TAG, "Got an URL:" + url);
        requestMap.put(token, url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD,token)
                .sendToTarget();
    }

    private void handleRequest(final Token token){
        try{
            final String url = requestMap.get(token);
            if (url == null)
                return;

            byte[] bitMapBytes = new FlickrFetcher().getURLBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitMapBytes, 0 , bitMapBytes.length);
            Log.i(TAG, "Bitmap Created");

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(requestMap.get(token) != url){
                        return;
                    }

                    requestMap.remove(token);
                    mListener.onThumbnailDownloaded(token, bitmap );
                }
            });

        }catch(IOException ioe) {
            Log.e(TAG, "Error Downloading Image", ioe);
        }

    }

    public void clearQueue() {

        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

}
