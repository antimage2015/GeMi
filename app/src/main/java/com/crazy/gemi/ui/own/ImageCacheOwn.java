package com.crazy.gemi.ui.own;


import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCacheOwn {

    private LruCache<String, Bitmap> mMemoryCache;

    // 获取应用程序最大可用内存
    int maxMemory = (int)Runtime.getRuntime().maxMemory();

    // 设置图片缓存大小为程序最大可用内存的1/8
    int cacheSize = maxMemory / 8;

    public ImageCacheOwn(){

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @param bitmap
     *            LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }


}
