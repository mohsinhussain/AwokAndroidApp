package com.awok.moshin.awok.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.OffersCategoryActivity;
import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.MoviesAdapter;
import com.awok.moshin.awok.Adapters.OffersCategoryFilterAdapter;
import com.awok.moshin.awok.Models.OffersCatModel;
import com.awok.moshin.awok.Models.OffersModel;
import com.awok.moshin.awok.Models.OffersSubCategory;
import com.awok.moshin.awok.Models.SortOffersModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OneFragment extends Fragment {
    private List<OffersCatModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    ArrayList<OffersSubCategory>   offersSubData= new ArrayList<>();
    private String title="";
    private String key="";
    ArrayList<String > spinnerPopData=new ArrayList<>();
    private int current_page = 1;
    Dialog filterDialog;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<SortOffersModel> sortModel=new ArrayList<>();
    private Spinner popup;

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
    private String dataToPost="";
    GridLayoutManager mLayoutManager;
    private String uri="";
    public OneFragment() {
        // Required empty public constructor
    }





    public OneFragment(String data,String title, String key, String value, String subData,ArrayList<SortOffersModel> dataSort, String dataArrayAll,int totalPages)
    {
        this.uri=data;
this.title=title;
        this.key=key;
        this.value=value;
        this.subData=subData;
        this.dataArrayAll=dataArrayAll;
        this.sortModel=dataSort;
        this.totalPages=totalPages;
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
        View mView=inflater.inflate(R.layout.fragment_one, container, false);

System.out.println("fgbdjkcgb" + ((OffersCategoryActivity) getActivity()).type);
         loader=(LinearLayout)mView.findViewById(R.id.loader);
        popup=(Spinner)mView.findViewById(R.id.spinner);
        progressLayout=(LinearLayout)mView.findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };



        filterAdapter=new OffersCategoryFilterAdapter(getActivity(),filterData,getActivity());

        System.out.println("TITLE: " + title);
        System.out.println("VALUE: " + value);
        System.out.println("KEY: "+key);
        System.out.println("SUB: "+subData);

        data=uri+"&"+key+"="+value+"&page="+current_page;
        dataToPost=uri+"&"+key+"="+value;
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, spinnerPopData);
        if (!subData.equals("[]")) {


            try {
              //  offersSubData.clear();
                spinnerPopData.clear();
                JSONArray subDataArray=new JSONArray();
                subDataArray = new JSONArray(subData);
                int arrayLength = subDataArray.length();
                for (int j = 0; j < arrayLength; j++) {

                    OffersSubCategory subData = new OffersSubCategory();
                    JSONObject subObject = subDataArray.getJSONObject(j);
                    spinnerPopData.add(subObject.getString("TITLE"));
                    subData.setTitle(subObject.getString("TITLE"));
                    subData.setKey(subObject.getString("KEY"));
                    subData.setValue(subObject.getString("VALUE"));


                    offersSubData.add(subData);


                }


                spinnerAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        else
        {
            popup.setVisibility(View.GONE);
        }


        popup.setAdapter(spinnerAdapter);




        popup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mGalleryInitializedCount < mGalleryCount) {
                    mGalleryInitializedCount++;
                } else {
                    current_page = 1;
                    previousTotal=0;
                    movieList.clear();


                    data = uri+"&" + key + "=" + value + "&" + offersSubData.get(position).getKey() + "=" + offersSubData.get(position).getValue() + "&page=" + current_page;






















                    dataToPost=uri+"&"+key+"="+value+"&"+offersSubData.get(position).getKey() + "=" + offersSubData.get(position).getValue();









                    Snackbar.make(getActivity().findViewById(android.R.id.content), "KEY" + offersSubData.get(position).getKey() + "value" + offersSubData.get(position).getValue(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();


                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        new APIClient(getActivity(), getContext(), new GetCategoriesCallback()).getOffersMainCatData(data.toString());
                    } else {
                        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED);

                        View snackbarView = snackbar.getView();

                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       /* for(int i=0;i<subData.size();i++)
        {
            System.out.println("SUB TITLE: "+subData.get(i).getTitle());
            System.out.println("SUB VALUE: " + subData.get(i).getValue());
            System.out.println("SUB    KEY: "+subData.get(i).getKey());
        }*/

sortFilter();

        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(getActivity(),movieList);

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

                firstVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                // firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                System.out.println("visibleItemCount" + visibleItemCount + " totalItemCount" + totalItemCount + "firstVisibleItem" + firstVisibleItem);
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
                    if (totalPages >= current_page) {
                        data = uri + "&" + key + "=" + value + "&page=" + current_page;
                        onLoadMore();
                        System.out.println("TotalPages" + totalPages + " Current" + current_page);
//                        mRecAdapter.notifyDataSetChanged();


                        loading = true;
                    }

                }
            }
        });





        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (movieList.size() > 0) {
                            ActivityOptionsCompat options =
                                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity());
                            Intent i = new Intent(getContext(), ProductDetailsActivity.class);
                            i.putExtra(Constants.PRODUCT_ID_INTENT, movieList.get(position).getId());
                            // i.putExtra(Constants.PRODUCT_ID_INTENT,"3653");
                            //      i.putExtra(Constants.PRODUCT_RATING,productsArrayList.get(position).getRating());
                            //      i.putExtra(Constants.PRODUCT_RATING_COUNT,productsArrayList.get(position).getRatingCount());
                            if(movieList.get(position).getOutOfStock().equals("Y")) {
                                i.putExtra(Constants.PRODUCT_OUT_STOCK, "N");
                            }


                          else if(movieList.get(position).getOutOfStock().equals("N")) {
                                i.putExtra(Constants.PRODUCT_OUT_STOCK, "Y");
                            }


                            i.putExtra(Constants.PRODUCT_NAME_INTENT, movieList.get(position).getName());
                            i.putExtra(Constants.PRODUCT_DISCOUNT_PERCENTAGE_INTENT, movieList.get(position).getDiscount());
                            i.putExtra(Constants.PRODUCT_IMAGE_INTENT, movieList.get(position).getImg());
                            i.putExtra(Constants.PRODUCT_PRICE_NEW_INTENT, "AED "+movieList.get(position).getNewPrice());
                            i.putExtra(Constants.PRODUCT_PRICE_OLD_INTENT, "AED "+movieList.get(position).getOldPrice());
                            i.putExtra(Constants.PRODUCT_DESCRIPTION_INTENT, "");
                            i.putExtra(Constants.CAT_ID_INTENT, movieList.get(position).getCatId());
                            i.putExtra("type", ((OffersCategoryActivity)getActivity()).type);
                            i.putExtra("typeCid", dataToPost);

                            ActivityCompat.startActivity(getActivity(), i, options.toBundle());
                        }

                    }
                })
        );





        prepareMovieData();
        mGalleryCount=1;
        return mView;
    }

    private void sortFilter() {

filterData.clear();
        for(int j=0;j<sortModel.size();j++)
        {

            filterData.add(sortModel.get(j).getTitle());
        }

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
                        System.out.println("fbkjgv" + dataObj.getString("NAME"));
                        OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                "",
                                "",
                                "",
                                "", "",false,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
                        movieList.add(movie);
                    }
                    else if (dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)
                    {
                        System.out.println("fbkjgv" + dataObj.getString("NAME"));
                        OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                "",
                                "",
                                "",
                                "", dataObj.getJSONObject("IMAGE").getString("SRC"),false,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
                        movieList.add(movie);
                    }
                    else if(dataObj.getJSONObject("IMAGE").length()==0)
                    {
                        System.out.println("fbkjgv" + dataObj.getString("NAME"));
                        OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), "",true,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
                        movieList.add(movie);
                    }
                    else {
                        System.out.println("fbkjgv" + dataObj.getString("NAME"));
                        OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), dataObj.getJSONObject("IMAGE").getString("SRC"),true,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
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
                new APIClient(getActivity(), getContext(),  new GetCategoriesCallback()).getOffersMainCatData(data.toString());
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
                        if((dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)&&(dataObj.getJSONObject("IMAGE").length()==0))
                        {
                            System.out.println("fbkjgv" + dataObj.getString("NAME"));
                            OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    "",
                                    "",
                                    "",
                                    "", "",false,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
                            movieList.add(movie);
                        }
                        else
                        if(dataObj.getJSONObject("PRICES").getJSONObject("TIMER").length()==0)

                        {
                            OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    "",
                                    "",
                                    "",
                                    "", dataObj.getJSONObject("IMAGE").getString("SRC"),false,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));

                            movieList.add(movie);
                        }
                        else if(!dataObj.getJSONObject("IMAGE").has("SRC"))
                        {
                            OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), "",true,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));
                            movieList.add(movie);
                        }
                        else {
                            OffersCatModel movie = new OffersCatModel(dataObj.getString("ID"), dataObj.getString("NAME"), dataObj.getJSONObject("PRICES").getString("PRICE_NEW"),
                                    dataObj.getJSONObject("PRICES").getString("PRICE_OLD"), dataObj.getJSONObject("PRICES").getString("DISCOUNT"), dataObj.getJSONObject("PRICES").getString("PERCENT"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("D"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("H"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("M"),
                                    dataObj.getJSONObject("PRICES").getJSONObject("TIMER").getString("S"), dataObj.getJSONObject("IMAGE").getString("SRC"),true,dataObj.getString("STOCK"),dataObj.getString("CATEGORY_ID"));

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
                        movieList.add(new OffersCatModel(true, "Loading More..."));

                        mAdapter.notifyItemInserted((movieList.size() - 1));
                        loadSpinner = true;
                    } else {
                        //rating.remove(new ProductRatingPageModel(true, "Loading More..."));
                        //mAdapter.notifyItemRemoved((rating.size() - 1));
                    }
                }
                new APIClient(getActivity(), getActivity(), new GetCategoriesCallback()).getOffersMainCatData(data.toString());

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





    private void filterPopUp() {
        filterDialog = new Dialog(getActivity());
        filterDialog.setCancelable(true);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.dialog_cat_filter);

        final ListView offersFilter = (ListView) filterDialog.findViewById(R.id.countryList);
        offersFilter.setAdapter(filterAdapter);





        offersFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {












                current_page=1;
                previousTotal=0;
                movieList.clear();





data=data+"&"+sortModel.get(position).getKey()+"="+sortModel.get(position).getValue();
                dataToPost=dataToPost+"&"+sortModel.get(position).getKey()+"="+sortModel.get(position).getValue();



                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new APIClient(getActivity(), getContext(),  new GetCategoriesCallback()).getOffersMainCatData(data.toString());
                } else {
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED);

                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }



filterDialog.dismiss();

            }

        });







        filterDialog.show();
        filterDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_cart:
            {
              /*  Snackbar.make(getActivity().findViewById(android.R.id.content), "Categories could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                filterPopUp();
            }
        }
        return super.onOptionsItemSelected(item);
    }






}
