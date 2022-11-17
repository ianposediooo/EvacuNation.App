package com.example.evacunation;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

/* loaded from: classes.dex */
public class disasterfragment extends Fragment implements View.OnClickListener {
    public CardView card1;
    public CardView card2;
    public CardView card3;
    public CardView card4;
    public CardView card5;
    public CardView card6;
    public CardView card7;
    public CardView card8;
    public TextView tv;

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.disaster_2_layout, viewGroup, false);
        this.card1 = (CardView) inflate.findViewById(R.id.card1);
        this.card2 = (CardView) inflate.findViewById(R.id.card2);
        this.card3 = (CardView) inflate.findViewById(R.id.card3);
        this.card4 = (CardView) inflate.findViewById(R.id.card4);
        this.card5 = (CardView) inflate.findViewById(R.id.card5);
        this.card6 = (CardView) inflate.findViewById(R.id.card6);
        this.card7 = (CardView) inflate.findViewById(R.id.card7);
        this.card8 = (CardView) inflate.findViewById(R.id.card8);

        this.tv = inflate.findViewById(R.id.txtdisaster);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f);

        this.card1.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), card1.class));
            }
        });
        this.card2.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), earthquake.class));
            }
        });
        this.card3.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), gobag.class));
            }
        });
        this.card4.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), flood.class));
            }
        });
        this.card5.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), landslide.class));
            }
        });
        this.card6.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), tsunami.class));
            }
        });
        this.card7.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), CovidActivity.class));
            }
        });
        this.card8.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                disasterfragment.this.getActivity().startActivity(new Intent(disasterfragment.this.getActivity(), safestepsph.class));
            }
        });
        return inflate;
    }
}
