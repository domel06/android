package com.example.firebasenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextAuthorName;
    TextView textView;
    Spinner spinnerSubject;
    Button buttonAddAuthor;
    ListView listViewAuthor;
    ArrayList<Author> authors;
    DatabaseReference databaseAuthor;

    public static final String AUTHOR_NAME="com.example.firebasenew.authorname";
    public static final String AUTHOR_ID="com.example.firebasenew.authorid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get refference for authors node
        databaseAuthor = FirebaseDatabase.getInstance().getReference("authors");
        editTextAuthorName = (EditText) findViewById(R.id.editTextAuthorName);
        textView = (TextView) findViewById(R.id.textView);
        spinnerSubject = (Spinner) findViewById(R.id.spinnerSubject);
        buttonAddAuthor = (Button) findViewById(R.id.buttonAddAuthor);
        listViewAuthor = (ListView) findViewById(R.id.listViewAuthor);
        //list to store authors
        authors = new ArrayList<Author>();

        //add onclick listener
        //method to add new author to the firabase database
        buttonAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aname = editTextAuthorName.getText().toString().trim();
                String asubject = spinnerSubject.getSelectedItem().toString();

                if (!TextUtils.isEmpty(aname)) {
                    //get the unique id
                    String aid = databaseAuthor.push().getKey();
                    Author author = new Author(aid, aname, asubject);
                    //saving author
                    databaseAuthor.child(aid).setValue(author);
                    //set edit text to blank again
                    editTextAuthorName.setText("");
                    Toast.makeText(getApplicationContext(), "author saved succesfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Enter author name", Toast.LENGTH_LONG).show();
                }
            }
        });
        listViewAuthor.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Author author = authors.get(position);
                showUpdateDeleteDialg(author.getAuthorId(),author.getAuthorName());

                return true;
            }
        });

    }
    private boolean updateAuthor(String aid,String aname,String asubject){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("authors").child(aid);

        //updating artist
        Author author = new Author(aid, aname, asubject);
        dR.setValue(author);
        Toast.makeText(getApplicationContext(), "Author Updated", Toast.LENGTH_LONG).show();
        return true;
    }
    private void showUpdateDeleteDialg(final String aid, String aname){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerSubject = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateAuthor);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteAuthor);

        dialogBuilder.setTitle(aname);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String asubject = spinnerSubject.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)){
                    updateAuthor(aid,name,asubject);
                    b.dismiss();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAuthor(aid);
                b.dismiss();
            }
        });
    }
    private boolean deleteAuthor(String aid){
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("authors").child(aid);
        dr.removeValue();
        Toast.makeText(getApplicationContext(),"author deleted",Toast.LENGTH_SHORT).show();
        return  true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseAuthor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //clearing the availabel authors
                authors.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    //getting author
                    Author author = postSnapshot.getValue(Author.class);
                    //adding author to the list
                    authors.add(author);
                }
                //creating adapter
                authorList authorAdapter=new authorList(MainActivity.this,authors);
                //attaching adapter to the list view
                listViewAuthor.setAdapter(authorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//error
            }
        });

    }

}