package com.example.evacunation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    private List<String> mData;
    private List<String> mTitles;


    public MyAdapter(List<String> data, List<String> titles) {
        mData = data;
        mTitles = titles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = mData.get(position);
        String title = mTitles.get(position);
        holder.textView.setText(item);
        holder.titleView.setText(title);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView textView;
        public CardView cardView1;
        public CardView cardView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.item_title);
            textView = itemView.findViewById(R.id.item_text_view);
            cardView1 = itemView.findViewById(R.id.phonecardview);
            cardView2 = itemView.findViewById(R.id.itemscardview);

            cardView2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String text = textView.getText().toString().replaceAll("[^0-9]", "");
                    String title = titleView.getText().toString();
                    //clipboard
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("titletext",title + " - " +text);
                    clipboard.setPrimaryClip(clip);
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(),R.style.CustomDialogTheme);
                    builder.setTitle("Hotline Copied to Clipboard!");
                    builder.setMessage(title + " - " +text);
                    builder.setIcon(R.drawable.copy);
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                    return true;
                }
            });

            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = textView.getText().toString();
                    String title = titleView.getText().toString();

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext(),R.style.CustomDialogTheme);
                    builder.setTitle("Call " + title+"?");
                    builder.setMessage("Hotline No. "+text);
                    builder.setIcon(R.drawable.callemergency);
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.2.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String phoneNum = textView.getText().toString().replaceAll("[^0-9]", "");
                            if (!phoneNum.isEmpty()) {
                                Intent dialIntent = new Intent(Intent.ACTION_CALL);
                                dialIntent.setData(Uri.parse("tel:" + phoneNum));
                                view.getContext().startActivity(dialIntent);
                            }
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}



