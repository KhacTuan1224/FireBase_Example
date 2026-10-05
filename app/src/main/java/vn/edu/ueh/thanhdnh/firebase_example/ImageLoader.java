package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public final class ImageLoader {
    private static volatile Picasso picasso;

    private ImageLoader() {
    }

    public static void load(String imageUrl, ImageView imageView) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            getPicasso(imageView.getContext()).cancelRequest(imageView);
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            return;
        }

        getPicasso(imageView.getContext())
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    private static Picasso getPicasso(Context context) {
        Picasso instance = picasso;
        if (instance == null) {
            synchronized (ImageLoader.class) {
                instance = picasso;
                if (instance == null) {
                    Context appContext = context.getApplicationContext();
                    instance = new Picasso.Builder(appContext)
                            .downloader(new OkHttp3Downloader(appContext))
                            .build();
                    picasso = instance;
                }
            }
        }
        return instance;
    }
}