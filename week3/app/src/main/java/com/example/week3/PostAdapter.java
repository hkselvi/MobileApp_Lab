package com.example.week3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//adapter class data ve view arasında köprü olur
public class PostAdapter extends BaseAdapter {
    List<Post> postList;  //constructorde generate etmeliyiz
    private LayoutInflater layoutInflater;

    public PostAdapter(Activity activity, List<Post> postList) {
        this.postList = postList;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = layoutInflater.inflate(R.layout.row, null);
        EditText txtMessage = rowView.findViewById(R.id.txtMessage);
        TextView location = rowView.findViewById(R.id.txtLocation);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        Post post = postList.get(position);
        txtMessage.setText(post.getMessage());
        if (post.getLocation() != null) {
            location.setText(post.getLocation().getLatitude()
                    + " " + post.getLocation().getLongitude());
        } //enlem ve boylam return eder
        imageView.setImageBitmap(post.getImage());

        return rowView;
    }
}
