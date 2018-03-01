package com.lst.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lst.news.R;
import com.lst.news.WebViewActivity;
import com.lst.news.utils.Const;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lisongtao on 2018/2/26.
 */

public class HomeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<ListModel> mData = new ArrayList<ListModel>();
    private final OkHttpClient client = new OkHttpClient();
    private String nextPage = "1";
    private String mCateID;
    private RefreshLayout mRl;
    private ClassicsHeader mClassicsHeader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View inflate = inflater.inflate(R.layout.fragment_list, container, false);
        setup(inflate);

        return inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCateID = (String) getArguments().get("cate_id");

        loadData(nextPage, true);
    }

    private void setup(View inflate) {
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rc_main);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerAdapter = new RecyclerAdapter(mData);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            //此处实现onItemClick的接口
            @Override
            public void onItemClick(final View view, int position) {
                ListModel model = mData.get(position);
                String url = "http://api.m123.me/contents/show?"+"cid="+model.getCid();
                //跳转到webview
                Intent intent = new Intent();
                intent.setClass(getContext(), WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        mRl = (RefreshLayout)inflate.findViewById(R.id.refreshLayout);

        mRl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                Log.v("lst", "onLoadmore");
                loadData(nextPage, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Log.v("lst", "onRefresh");
                nextPage = "1";
                loadData(nextPage, true);
            }
        });

        mClassicsHeader = (ClassicsHeader)mRl.getRefreshHeader();
        mClassicsHeader.setSpinnerStyle(SpinnerStyle.Translate);

    }

    private void loadData(String page, final boolean isPullDown) {
        Request request = new Request.Builder().url(Const.LIST_URL+"?cate_id="+mCateID+"&page="+page).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("lst", "onFailure"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Log.v("lst", "onSuccess");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    String responseStr = responseBody.string();// 只能调用一次
//                    System.out.println(responseStr);

                    JSONObject obj = new JSONObject(responseStr);
                    JSONObject data = obj.getJSONObject("data");
                    nextPage = data.getString("nextPage");

                    System.out.println("mData="+mData);
                    if (isPullDown) {
                        mRl.finishRefresh();
                        mData.clear();
                    }else {
                        if (nextPage.equals("0")) {
                            mRl.finishLoadMoreWithNoMoreData();
                        }else {
                            mRl.finishLoadMore();
                        }
                    }

                    System.out.println("mData="+mData);
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject listObj = list.getJSONObject(i);
                        ListModel model = new ListModel();
                        model.setCid(listObj.getString("cid"));
                        model.setTitle(listObj.getString("title"));
                        model.setThumb(listObj.getString("thumb"));
                        model.setCate_id(listObj.getString("cate_id"));
                        model.setCate_name(listObj.getString("cate_name"));
                        model.setText(listObj.getString("text"));
                        mData.add(model);
                    }



                    mRecyclerView.post(new Runnable() {
                        public void run() {
                            mRecyclerAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
