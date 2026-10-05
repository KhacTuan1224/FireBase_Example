package vn.edu.ueh.thanhdnh.firebase_example;

public class Article {
  private String id;
  private String title;
  private String image;
  private String description;
  private long view = 0L;

  public Article() {
    // Firestore needs an empty constructor when converting a document to an object.
  }

  public Article(String id, String title, String image, String description) {
    this.id = id;
    this.title = title;
    this.image = image;
    this.description = description;
    this.view = 0L;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getImage() { return image; }
  public void setImage(String image) { this.image = image; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public long getView() { return view; }
  public void setView(long view) { this.view = view; }
}