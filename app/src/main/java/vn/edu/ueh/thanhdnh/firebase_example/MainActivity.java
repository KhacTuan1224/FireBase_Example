package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  FirebaseFirestore db;
  Button btAdd, btShow;
  EditText etId, etTitle, etImage, etDescription;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();
    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    etId = findViewById(R.id.etId);
    etTitle = findViewById(R.id.etTitle);
    etImage = findViewById(R.id.etImage);
    etDescription = findViewById(R.id.etDescription);
    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      saveArticle();
    } else if (view.getId() == R.id.btShow) {
      Intent intent = new Intent(getBaseContext(), ShowDataActivity.class);
      startActivity(intent);
    }
  }

  private void saveArticle() {
    String id = etId.getText().toString().trim();
    String title = etTitle.getText().toString().trim();
    String image = etImage.getText().toString().trim();
    String description = etDescription.getText().toString().trim();

    if (id.isEmpty() || title.isEmpty() || image.isEmpty() || description.isEmpty()) {
      Toast.makeText(this, R.string.error_required_fields, Toast.LENGTH_SHORT).show();
      return;
    }
    if (id.contains("/")) {
      etId.setError(getString(R.string.error_invalid_id));
      return;
    }
    if (!image.startsWith("https://")) {
      etImage.setError(getString(R.string.error_invalid_url));
      return;
    }

    btAdd.setEnabled(false);
    Article article = new Article(id, title, image, description);
    db.collection("articles").document(id).set(article)
            .addOnSuccessListener(unused -> {
              Toast.makeText(this, R.string.article_saved, Toast.LENGTH_SHORT).show();
              etId.setText("");
              etTitle.setText("");
              etImage.setText("");
              etDescription.setText("");
            })
            .addOnFailureListener(error ->
                    Toast.makeText(this, getString(R.string.save_failed, error.getMessage()), Toast.LENGTH_LONG).show())
            .addOnCompleteListener(task -> btAdd.setEnabled(true));
  }
}