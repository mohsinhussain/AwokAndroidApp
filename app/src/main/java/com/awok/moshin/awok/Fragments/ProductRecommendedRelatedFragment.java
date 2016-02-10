package com.awok.moshin.awok.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.MoviesAdapter;
import com.awok.moshin.awok.Adapters.OffersCategoryFilterAdapter;
import com.awok.moshin.awok.Adapters.ProductRecommendedRelatedAdapter;
import com.awok.moshin.awok.Models.OffersCatModel;
import com.awok.moshin.awok.Models.OffersSubCategory;
import com.awok.moshin.awok.Models.ProductRecommendedRelatedModel;
import com.awok.moshin.awok.Models.SortOffersModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 2/3/2016.
 */
public class ProductRecommendedRelatedFragment extends Fragment {
    private List<ProductRecommendedRelatedModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductRecommendedRelatedAdapter mAdapter;
    ArrayList<OffersSubCategory>   offersSubData= new ArrayList<>();
    private String title="";
    private String key="";
    ArrayList<String > spinnerPopData=new ArrayList<>();
    private int current_page = 1;



    private  final int TYPE_FOOTER = 2;
    private final int TYPE_ITEM = 1;





    private LinearLayout progressLayout;


    private Boolean loadSpinner=false;
    OffersCategoryFilterAdapter filterAdapter;
    private boolean loading = true;
    //List<String,String,String> spinnerData=new HashMap<>();
    private String value="";
    ArrayList<String > filterData=new ArrayList<>();
    int check=0;
    LinearLayout loader;
    //this counts how many Gallery's are on the UI
    private int mGalleryCount=0;

    //this counts how many Gallery's have been initialized
    private int mGalleryInitializedCount=0;

    private int previousTotal = 0;





    private int totalPages=0;
    private String subData="";
    private String dataArrayAll="";
    private String data="";
    GridLayoutManager mLayoutManager;
    private String uri="";
    String uriMain="";
    String catId="";
    String type="";
    public ProductRecommendedRelatedFragment() {
        // Required empty public constructor
    }





    public ProductRecommendedRelatedFragment(String uriMain,String catId, String type,String dataArrayAll)
    {
     this.uriMain=uriMain;
        this.catId=catId;
        this.type=type;

        this.dataArrayAll=dataArrayAll;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_product_recommended, container, false);


        loader=(LinearLayout)mView.findViewById(R.id.loader);

        progressLayout=(LinearLayout)mView.findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);



System.out.println("URIMAIN" + uriMain);
        System.out.println("catid" + catId);
        System.out.println("type"+type);
        System.out.println("dataArrayAll"+dataArrayAll);



        System.out.println("TITLE: " + title);
        System.out.println("VALUE: " + value);
        System.out.println("KEY: "+key);
        System.out.println("SUB: "+subData);







        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        mAdapter = new ProductRecommendedRelatedAdapter(getActivity(),movieList);

        recyclerView.setHasFixedSize(false);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        mLayoutManager = new GridLayoutManager(getContext(),2);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(mAdapter.getItemViewType(position)){
                    case MoviesAdapter.TYPE_FOOTER:
                        return 2;
                    case MoviesAdapter.TYPE_ITEM:
                        return 1; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });



        recyclerView.setLayoutManager(mLayoutManager);
        //  recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                    StaggeredGridLayoutManager layoutManager = ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager());
                int visibleItemCount, totalItemCount, firstVisibleItem;
//
                visibleItemCount = recyclerView.getChildCount();
                //      totalItemCount = recyclerView.getLayoutManager().getItemCount();
                totalItemCount = mLayoutManager.getItemCount();

                firstVisibleItem=mLayoutManager.findFirstCompletelyVisibleItemPosition();
                // firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                System.out.println("visibleItemCount" + visibleItemCount + " totalItemCount" + totalItemCount+"firstVisibleItem"+firstVisibleItem);
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + 10)) {
                    // End has been reached

                    // Do something
                   /*     for (int j=0;j<20;j++)
                        {
                            Model mm=new Model();


                            mm.setText("I am "+j);
                            mm.setColor("Color is" + j);


                            model.add(mm);

                        }*/
                    System.out.println("TotalPages" + totalPages + " Current" + current_page);

                    current_page++;
                    if(totalPages>=current_page) {
                        data = uri + "&" + key + "=" + value + "&page=" + current_page;
                        onLoadMore();
                        System.out.println("TotalPages" + totalPages + " Current" + current_page);
//                        mRecAdapter.notifyDataSetChanged();


                        loading = true;
                    }

                }
            }
        });




        prepareMovieData();
        mGalleryCount=1;
        return mView;
    }



    private void prepareMovieData() {
        if(!dataArrayAll.equals(""))
        {
            try {
                progressLayout.setVisibility(View.VISIBLE);
                JSONArray jd=new JSONArray(dataArrayAll);
                for(int i=0;i<jd.length();i++)
                {
                    JSONObject  dataObj=jd.getJSONObject(i);
                    if((dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)&&(dataObj.getJSONObject("IMAGE").length()==0))
                    {
                        ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                "",
                                "",
                                "",
                                "", "",false);
                        movieList.add(movie);
                    }
                    else
                    if(dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)
                    {
                        ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                "",
                                "",
                                "",
                                "", dataObj.getJSONObject("IMAGE").getString("SRC"),false);
                        movieList.add(movie);
                    }
                    else if(!dataObj.getJSONObject("IMAGE").has("SRC"))
                    {
                        ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), "",true);
                        movieList.add(movie);
                    }
                    else {
                        ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), dataObj.getJSONObject("IMAGE").getString("SRC"),true);
                        movieList.add(movie);
                    }
                }
                progressLayout.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //new APIClient(getActivity(), getContext(),  new GetCategoriesCallback()).getOffersMainCatData(data.toString());
                if(catId.equals(""))
                {
                    new APIClient(getActivity(), getActivity(), new GetCategoriesCallback()).getProductDetailsRecommendedRelatedCategory(uriMain.toString(), current_page);
                }
                else {
                    new APIClient(getActivity(), getActivity(), new GetCategoriesCallback()).getProductDetailsRecommendedRelated(catId, current_page);
                }
            } else {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }

        mAdapter.notifyDataSetChanged();
    }





    public class GetCategoriesCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                if(loadSpinner) {
                    loadSpinner=false;
                    movieList.remove((movieList.size() - 1));
                }



                loader.setVisibility(View.GONE);

                JSONObject mMembersJSON;
                System.out.println("CATE" + response);

                mMembersJSON = new JSONObject(response);

                //JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
                if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==200) {
                    totalPages=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getInt("TOTAL");
                    JSONArray jsonArray = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS");
                    int length = jsonArray.length();

                    for (int i = 0; i < length; i++) {
                        JSONObject  dataObj=jsonArray.getJSONObject(i);
                        System.out.println("vbfmnvbmfnbv xcn" + dataObj.getString("NAME"));
                        if((dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)&&(dataObj.getJSONObject("IMAGE").length()==0))
                        {
                            ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    "",
                                    "",
                                    "",
                                    "", "",false);
                            movieList.add(movie);
                        }
                        else  if(dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)
                        {
                            ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    "",
                                    "",
                                    "",
                                    "", dataObj.getJSONObject("IMAGE").getString("SRC"),false);

                            movieList.add(movie);
                        }
                        else if(!dataObj.getJSONObject("IMAGE").has("SRC"))
                        {
                            ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), "",true);
                            movieList.add(movie);
                        }
                        else {
                            ProductRecommendedRelatedModel movie = new ProductRecommendedRelatedModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), dataObj.getJSONObject("IMAGE").getString("SRC"),true);

                            movieList.add(movie);
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                }
                //   setupViewPager(viewPager);

                mAdapter.notifyDataSetChanged();
                progressLayout.setVisibility(View.GONE);

                //    tabLayout.setupWithViewPager(viewPager);
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Categories could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        @Override
        public void onTaskCancelled() {
            progressLayout.setVisibility(View.GONE);
        }
        @Override
        public void onPreExecute() {
            if(!loadSpinner) {
                progressLayout.setVisibility(View.VISIBLE);
            }
            // TODO Auto-generated method stub








        }
    }












    private void onLoadMore() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo != null && networkInfo.isConnected()) {
                ///////////////////////////////////////////////////////// loader.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                if(movieList.size()>0) {
                    if (!movieList.get(movieList.size() - 1).isLoader()) {
                        movieList.add(new ProductRecommendedRelatedModel(true, "Loading More..."));

                        mAdapter.notifyItemInserted((movieList.size() - 1));
                        loadSpinner = true;
                    } else {
                        //rating.remove(new ProductRatingPageModel(true, "Loading More..."));
                        //mAdapter.notifyItemRemoved((rating.size() - 1));
                    }
                }
                if(catId.equals(""))
                {
                    new APIClient(getActivity(), getActivity(), new GetCategoriesCallback()).getProductDetailsRecommendedRelatedCategory(uriMain.toString(),current_page);
                }
                else {
                    new APIClient(getActivity(), getActivity(), new GetCategoriesCallback()).getProductDetailsRecommendedRelated(catId, current_page);
                }

            } else {

            /*Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/


                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }


    }

























}
