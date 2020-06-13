package com.example.atm003;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FinanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExpenseHelper helper;
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(FinanceActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new ExpenseHelper(this);
        Cursor cursor = helper.getReadableDatabase().query("expense",
                null,null,null,
                null,null,null);
        adapter = new ExpenseAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = helper.getReadableDatabase().query("expense",
                null,null,null,
                null,null,null);
        adapter = new ExpenseAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
        Cursor cursor;
        public ExpenseAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @NonNull
        @Override
        public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.expense_item,viewGroup,false);
            return new ExpenseHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseHolder expenseHolder, int i) {
            cursor.moveToPosition(i);
            String date = cursor.getString(cursor.getColumnIndex("cdate"));
            String info = cursor.getString(cursor.getColumnIndex("info"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            expenseHolder.dateText.setText(date);
            expenseHolder.infoText.setText(info);
            expenseHolder.amountText.setText(String.valueOf(amount));
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        public class ExpenseHolder extends RecyclerView.ViewHolder {
            EditText dateText;
            EditText infoText;
            EditText amountText;
            public ExpenseHolder(@NonNull View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.item_date);
                infoText = itemView.findViewById(R.id.item_info);
                amountText = itemView.findViewById(R.id.item_amount);
            }
        }
    }
}
