package com.cs3714.sojo.proj;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cs3714.sojo.proj.Objects.RecipeUrl;
import com.cs3714.sojo.proj.Objects.Result;
import com.cs3714.sojo.proj.Objects.SummaryInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
// this fragment is looking for the recipe from the list of the ingredients

public class FindFragment extends Fragment {

    private TextView result_title;
    private TextView result_summary;
    public volatile RecipeUrl info;
    public SummaryInfo sumInfo;
    public List<Result> curators;
    private ListView entries;
    private ResultEntryAdapter adapter;
    private ImageView imgView;
    SharedPreferences pref;
    SharedPreferences.Editor prefEdit;
    ActivityInTheRFFragmentInterface activityInterface;
    Typeface typeface;
    TextView tv;


    //when this fragments get intitiate it calls all of the list of recipe that we parse with
    //selected ingredients
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.findfragment, container, false);
        Log.d("Create View", "");
        entries = (ListView) view.findViewById(R.id.list);
        adapter = new ResultEntryAdapter(getActivity().getApplicationContext(), new ArrayList<Result>());
        prefEdit = getActivity().getSharedPreferences("recipe", Context.MODE_PRIVATE).edit();
        entries.setAdapter(adapter);
        tv = (TextView) view.findViewById(R.id.textView4);

        entries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {
                Log.d("position", Integer.toString(position));
                Result data = curators.get(position);

                prefEdit.putString("recipeUrl", data.getRecipeUrl());
                prefEdit.putString("imageUrl", data.getImg_url());
                prefEdit.apply();
                ((ActivityInTheRFFragmentInterface) getActivity()).showDetailRecipe();


            }

        });

        typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/RoundedElegance.ttf");
        return view;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityInterface = (ActivityInTheRFFragmentInterface) activity;


    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);


    }

    public void updateContent(List<Result> list) {
        Log.d("update", "yea");
        if(adapter != null){
        adapter.clear();}
        else{
            tv.setVisibility(View.VISIBLE);
        }
        adapter.addAll(list);
        entries.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    // recipe entries
    private class ResultEntryAdapter extends ArrayAdapter<Result> {

        private final Context context;
        private final List<Result> results;

        public ResultEntryAdapter(Context context, ArrayList<Result> results) {
            super(context, R.layout.result_entry_layout, results);
            this.context = context;
            this.results = results;
            curators = results;
            Log.d("FF","I am in entry adapter");

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = null;

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            rowView = inflater.inflate(R.layout.result_entry_layout,
                    parent, false);
            tv.setVisibility(View.GONE);

            Log.d("results", results.get(position).getTitle());

            result_title = (TextView) rowView.findViewById(R.id.textView2);
            result_summary = (TextView) rowView.findViewById(R.id.textView3);
            result_title.setTypeface(typeface);

            imgView = (ImageView) rowView.findViewById(R.id.img_result);
            result_title.setText(results.get(position).getTitle());
            result_summary.setText(results.get(position).getSummary());

            Picasso.with(context).load(results.get(position).getImg_url()).into(imgView);


            return rowView;

        }


    }
}

