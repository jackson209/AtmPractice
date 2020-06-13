package com.example.atm003;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN = 100;
    private boolean login = false;
    public String[] funcs;
    private List<Function> functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(!login){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        funcs = getResources().getStringArray(R.array.functions);
        functions = new ArrayList<>();
        functions.add(new Function(funcs[0],R.drawable.func_transaction));
        functions.add(new Function(funcs[1],R.drawable.func_balance));
        functions.add(new Function(funcs[2],R.drawable.func_finance));
        functions.add(new Function(funcs[3],R.drawable.func_contacts));
        functions.add(new Function(funcs[4],R.drawable.func_exit));

        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        TextAdapter adapter = new TextAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_LOGIN){
            if(resultCode != RESULT_OK){
                finish();
            }
        }
    }

    public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {
        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.recycler,viewGroup,false);
            return new TextViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final TextViewHolder textViewHolder, final int i) {
            final Function function = functions.get(i);
            textViewHolder.textView.setText(function.getName());
            textViewHolder.imageView.setImageResource(function.getIcon());
            textViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(function.getName()){
                        case "交易記錄":
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(function.getName())
                                    .setPositiveButton("OK",null)
                                    .show();
                            break;
                        case "餘額查詢":
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(function.getName())
                                    .setPositiveButton("OK",null)
                                    .show();
                            break;
                        case "投資理財":
                            Intent finance = new Intent(MainActivity.this,FinanceActivity.class);
                            startActivity(finance);
                            break;
                        case "聯絡人管理":
                            Intent contacts = new Intent(MainActivity.this,ContactActivity.class);
                            startActivity(contacts);
                            break;
                        case "離開":
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(function.getName())
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .show();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return funcs.length;
        }

        public class TextViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;
            public TextViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.item_name);
                imageView = itemView.findViewById(R.id.icon_image);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
