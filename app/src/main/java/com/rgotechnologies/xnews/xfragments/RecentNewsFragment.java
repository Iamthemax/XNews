package com.rgotechnologies.xnews.xfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.NetworkUtils;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xadapters.GenPostAdapter;
import com.rgotechnologies.xnews.xconfig.XConfig;
import com.rgotechnologies.xnews.xlibs.CustomProgressbar;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;
import com.rgotechnologies.xnews.xretro.ApiClient;
import com.rgotechnologies.xnews.xretro.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentNewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecentNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    GenPostAdapter adapter;
    List<GenPostResponse> responseList;
    SwipeRefreshLayout swipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public RecentNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentNewsFragment newInstance(String param1, String param2) {
        RecentNewsFragment fragment = new RecentNewsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mView= inflater.inflate(R.layout.fragment_recent_news, container, false);
        initializeAll(mView);
        return mView;
    }

    private void initializeAll(View mView) {
        recyclerView=(RecyclerView)mView.findViewById(R.id.recyclerView);
        responseList=new ArrayList<>();
        adapter=new GenPostAdapter(responseList,getActivity());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(false);
        getRecentPostsFromServer();

    }

    private void getRecentPostsFromServer() {
        Log.d(XConfig.TAG, "onResponse : getRecentPostsFromServer");
        CustomProgressbar.showProgressBar(getActivity(),false);
        ApiService apiService= ApiClient.getApiClient().create(ApiService.class);
        Call<List<GenPostResponse>> responseCall=apiService.getRecentPosts();
        responseCall.enqueue(new Callback<List<GenPostResponse>>() {
            @Override
            public void onResponse(Call<List<GenPostResponse>> call, Response<List<GenPostResponse>> response) {
                CustomProgressbar.hideProgressBar();
                if(response.isSuccessful()) {

                    if (response.body().size() > 0) {
                        responseList=response.body();
                        adapter=new GenPostAdapter(responseList,getActivity());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    } else {
                        Log.d(XConfig.TAG, "onResponse : No records found ");
                    }
                }else{
                    Log.d(XConfig.TAG, "onResponse : unsuccessful response");
                }
            }

            @Override
            public void onFailure(Call<List<GenPostResponse>> call, Throwable t) {
                CustomProgressbar.hideProgressBar();
                Log.d(XConfig.TAG,"onFailure : "+t.getMessage());
                Log.d(XConfig.TAG,"onFailure : "+t.getCause());
                Log.d(XConfig.TAG,"onFailure : "+t.getLocalizedMessage());
                t.printStackTrace();
            }
        });

    }
    @Override
    public void onRefresh() {
        if (NetworkUtils.isConnected()) {
            getRecentPostsFromServer();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
