package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArticleDetailActivity extends AppCompatActivity {
  public static final String EXTRA_ID = "article_id";
  public static final String EXTRA_TITLE = "article_title";
  public static final String EXTRA_IMAGE = "article_image";
  public static final String EXTRA_DESCRIPTION = "article_description";
  public static final String EXTRA_VIEW = "article_view";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_article_detail);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
      Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
      return insets;
    });

    String id = valueOrEmpty(getIntent().getStringExtra(EXTRA_ID));
    String title = valueOrEmpty(getIntent().getStringExtra(EXTRA_TITLE));
    String image = valueOrEmpty(getIntent().getStringExtra(EXTRA_IMAGE));
    String description = valueOrEmpty(getIntent().getStringExtra(EXTRA_DESCRIPTION));
    long viewCount = getIntent().getLongExtra(EXTRA_VIEW, 1);
    ((TextView) findViewById(R.id.tvDetailTitle)).setText(title);
    ((TextView) findViewById(R.id.tvDetailId)).setText(getString(R.string.article_id_value, id));
    ((TextView) findViewById(R.id.tvDetailDescription)).setText(description);
    ((TextView) findViewById(R.id.tvDetailView)).setText(getString(R.string.article_view_value, viewCount));
    ImageLoader.load(image, (ImageView) findViewById(R.id.ivDetailImage));
    ((Button) findViewById(R.id.btBack)).setOnClickListener(view -> finish());
  }

  private String valueOrEmpty(String value) { return value == null ? "" : value; }
}