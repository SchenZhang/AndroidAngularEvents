package com.example.eventfinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> favor=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private RelativeLayout has;
    private String mParam2;
    private RelativeLayout nofavors;
    private  RecyclerView recyleview;

    public FavorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavorFragment newInstance(String param1, String param2) {
        FavorFragment fragment = new FavorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
public void addfa(String a,String b,String c,String d,String e,String f){
    favor.add(a);
    favor.add(b);
    favor.add(c);
    favor.add(d);
    favor.add(e);
    favor.add(f);
    Log.d("called", "add f:  add to lists");
//    ArrayList<String> f=getArguments().getStringArrayList("f");
//    if(f.isEmpty()){
//        nofavors.setVisibility(RelativeLayout.VISIBLE);
//        Log.d("called", "onCreateView: empty favor lists");
//    }else {
//
//    }
}
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            ArrayList<String> f=((MainActivity) getActivity()).readf();
            if(f.isEmpty()){

                nofavors.setVisibility(RelativeLayout.VISIBLE);
                ArrayList<favoritem> result= new ArrayList<favoritem>();
                favoradaptor Adaptor=new favoradaptor(result,getContext());
                recyleview.setAdapter(Adaptor);
                recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
                Log.d("called", "onCreateView: empty favor lists");

            }else {
                nofavors.setVisibility(RelativeLayout.INVISIBLE);

                Log.d(f.get(0), "onCreateView: has favor lists");

                ArrayList<favoritem> result= new ArrayList<favoritem>();
                int j=f.size();
                ArrayList<String> ans=f;
                for(int i=0;i<j;i+=7){
                    result.add(new favoritem(ans.get(i),ans.get(i+1),ans.get(i+2),ans.get(i+3),ans.get(i+4),ans.get(i+5),ans.get(i+6)));
                }

                favoradaptor Adaptor=new favoradaptor(result,getContext());
                recyleview.setAdapter(Adaptor);
                recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
                Log.d("1", "display: called");
            }

            //Write down your refresh code here, it will call every time user come to this fragment.
            //If you are using listview with custom adapter, just call notifyDataSetChanged().
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_favor, container, false);
        ArrayList<String> f=getArguments().getStringArrayList("f");
      recyleview=(RecyclerView) v.findViewById(R.id.favors);
      has=v.findViewById(R.id.has);
        nofavors=v.findViewById(R.id.nof);
        if(f.isEmpty()){
       //  String tmp=((MainActivity) getActivity()).gettext();

             nofavors.setVisibility(RelativeLayout.VISIBLE);
             Log.d("called", "onCreateView: empty favor lists");
             ArrayList<favoritem> result= new ArrayList<favoritem>();
             favoradaptor Adaptor=new favoradaptor(result,getContext());
             recyleview.setAdapter(Adaptor);
             recyleview.setLayoutManager(new LinearLayoutManager(getContext()));

//
//             String[] aa=tmp.split("#");
//             ArrayList<favoritem> result= new ArrayList<favoritem>();
//             ArrayList<String> xx = new ArrayList<>();
//
//             for(int i=0;i<aa.length;i+=7){
//                 result.add(new favoritem(aa[i],aa[i+1],aa[i+2],aa[i+3],aa[i+4],aa[i+5],aa[i+6]));
//             }
//             favoradaptor Adaptor=new favoradaptor(result,getContext());
//             recyleview.setAdapter(Adaptor);
//             recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
//             Log.d("222222", "display: called");


        }else {
            nofavors.setVisibility(RelativeLayout.INVISIBLE);
            Log.d(f.get(0), "onCreateView: has favor lists");

            ArrayList<favoritem> result= new ArrayList<favoritem>();
            int j=f.size();
           ArrayList<String> ans=f;
            for(int i=0;i<j;i+=7){
                result.add(new favoritem(ans.get(i),ans.get(i+1),ans.get(i+2),ans.get(i+3),ans.get(i+4),ans.get(i+5),ans.get(i+6)));
            }

            favoradaptor Adaptor=new favoradaptor(result,getContext());
            recyleview.setAdapter(Adaptor);
            recyleview.setLayoutManager(new LinearLayoutManager(getContext()));
            Log.d("1", "display: called");
        }



        return v;
    }

}