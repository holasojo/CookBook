package com.cs3714.sojo.proj;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ChatFragment extends Fragment {
    private ListView entries;
    private ArrayAdapter<String> adapter;
    private List<String> ingList;
    private ActivityInTheRFFragmentInterface talkToActivity;
    //String[] dataArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chatfragment, container, false);
        entries = (ListView) view.findViewById(R.id.ingListview);
        ingList = talkToActivity.getList();

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.recipelistentrylaout, ingList);
        entries.setAdapter(adapter);

        entries.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(getActivity().getApplicationContext(), adapter.getItem(arg2) + " removed", Toast.LENGTH_SHORT).show();
                System.out.println(arg2 + " --postion");
                ingList.remove(adapter.getItem(arg2));

                System.out.println("list = ");

                adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.recipelistentrylaout, ingList);
                entries.setAdapter(adapter);
                talkToActivity.updateList(ingList);
            }

        });

        return view;
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            talkToActivity = (ActivityInTheRFFragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ProgressInteractionListener");
        }

        //updating gui

    }
}

