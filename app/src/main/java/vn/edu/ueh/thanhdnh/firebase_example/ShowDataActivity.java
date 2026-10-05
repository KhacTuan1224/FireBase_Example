package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    TextView emptyView;
    List<Article> articles = new ArrayList<>();
    ListenerRegistration articleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.reclyclerview);
        emptyView = findViewById(R.id.tvEmpty);
        ArticleAdapter adapter = new ArticleAdapter(this, articles);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        articleListener = db.collection("articles").addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    emptyView.setText(getString(R.string.load_failed, error.getMessage()));
                    emptyView.setVisibility(View.VISIBLE);
                    return;
                }
                if (snapshots != null) {
                    articles.clear();
                    for (QueryDocumentSnapshot q : snapshots) {
                        Article article = q.toObject(Article.class);
                        if (article.getId() == null || article.getId().isEmpty()) article.setId(q.getId());
                        articles.add(article);
                    }
                    adapter.update(articles);
                    if (articles.isEmpty() && snapshots.getMetadata().isFromCache()) {
                        emptyView.setText(R.string.waiting_for_firestore);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setText(R.string.no_articles);
                        emptyView.setVisibility(articles.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (articleListener != null) articleListener.remove();
        super.onDestroy();
    }
}