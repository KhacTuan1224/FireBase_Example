package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
  private TextView txtName, txtPhone;
  private UserViewAdapter adapter;

  public UserViewHolder(@NonNull View itemView, UserViewAdapter adapter) {
    super(itemView);
    txtName = itemView.findViewById(R.id.txt_name);
    txtPhone = itemView.findViewById(R.id.txt_phone);
    this.adapter = adapter;
  }

  public TextView getTxtName() {
    return txtName;
  }

  public void setTxtName(TextView txtName) {
    this.txtName = txtName;
  }

  public TextView getTxtPhone() {
    return txtPhone;
  }

  public void setTxtPhone(TextView txtPhone) {
    this.txtPhone = txtPhone;
  }
}
