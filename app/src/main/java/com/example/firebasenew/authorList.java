package com.example.firebasenew;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class authorList extends ArrayAdapter<Author> {
    private Activity context;
    List<Author> authors;

    public authorList(Activity context, List<Author> authors){
        super(context,R.layout.author_list, authors);
        this.context = context;
        this.authors = authors;
    }
@Override
    public View getView(int position, View convertView, ViewGroup parent){
    LayoutInflater inflater = context.getLayoutInflater();
    View ListViewItem = inflater.inflate(R.layout.author_list,null,true);
    TextView textViewAuthorName = (TextView) ListViewItem.findViewById(R.id.textViewAuthorName);
    TextView textSubject = (TextView) ListViewItem.findViewById(R.id.textViewSubject);
    Author author = authors.get(position);
    textViewAuthorName.setText(author.getAuthorName());
    textSubject.setText(author.getAuthorSubject());

    return ListViewItem;

}
}
