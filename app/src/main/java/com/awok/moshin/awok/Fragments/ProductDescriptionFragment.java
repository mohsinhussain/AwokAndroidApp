package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.DescriptionAdapter;
import com.awok.moshin.awok.Adapters.ProductOverViewAdapter;
import com.awok.moshin.awok.Adapters.ProductSpecificationAdapter;
import com.awok.moshin.awok.Models.Country;
import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ProductDescriptionFragment extends Fragment {

   //private List<DescriptionModel> description=new ArrayList<DescriptionModel>();;
private String description="";
    private int flag=0;
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
    private WebView webView;
        private RecyclerView.LayoutManager mLayoutManager;
        //private List<ProductOverview> overViewList = new ArrayList<ProductOverview>();

    TextView descriptionTextView;
  /*  public ProductDescriptionFragment(List<DescriptionModel> description) {
        this.description = description;
    }*/
  public ProductDescriptionFragment(String description) {
      this.description=description;
  }





    public ProductDescriptionFragment() {

    }









    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_product_over_view, container, false);
        //descriptionTextView = (TextView) mView.findViewById(R.id.descriptionTextView);
        //descriptionTextView.setText(description);

        webView = (WebView) mView.findViewById(R.id.webview);
        webView.loadData(description, "text/html; charset=utf-8", "UTF-8");
        if (description.equals(""))
        {
            flag=0;
        }
        else
        {
            flag=1;
        }










/*if(description.equals(null))
{*/
   // loadMore();
/*}
        else {
    webView.loadData(description, "text/html; charset=utf-8", "UTF-8");

}*/













          /*  mRecyclerView = (RecyclerView) mView.findViewById(R.id.overViewRecyclerView);

            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);


            mAdapter = new DescriptionAdapter(getActivity(),description);
            mRecyclerView.setAdapter(mAdapter);*/




       // call(description);

//
//
//overViewList.clear();
//        overViewList.add(prodOverviewData);
//           /* int i=0;
//            for(i=0;i<=4;i++)
//            {
//                ProductOverview listData=new ProductOverview();
//                listData.setOverViewText(getResources().getString(R.string.body_txt));
//                listData.setOverViewTitle(getResources().getString(R.string.head_txt));
//
//                overViewList.add(listData);
//
//            }
//            System.out.println("COOL"+overViewList.toString());*/
//            mAdapter.notifyDataSetChanged();

            return mView;
        }

  /*  private void loadMore() {
        if(this.description.equals(""))
        {
            loadMore();
        }
        else
        {
            webView.loadData(description, "text/html; charset=utf-8", "UTF-8");
        }

    }*/

    /* public void call(List<DescriptionModel> description)
     {
         this.description = description;

         System.out.println("MOHSIN/SHON HAS DONE IT");
     }*/







   public void call(String description)
   {
       this.description=description;
       if(webView!=null){
           webView.loadData(description, "text/html; charset=utf-8", "UTF-8");
       }


//       ProductDescriptionFragment desc = (ProductDescriptionFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.viewpager);
//       if (desc != null && desc.isVisible()) {
//           //DO STUFF
//           System.out.println("DESC");
//       }
//       else {
//           System.out.println("DESC NOTVISIBLE");
//           //Whatever
//       }


//this.getView().getVisibility();
/*if(flag==1)
{

}
       else
{
    webView.loadData(description, "text/html; charset=utf-8", "UTF-8");
}*/

   }
    }

