package com.awok.moshin.awok.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.awok.moshin.awok.R;

public class VisaPaymentFragment extends Fragment {

    View mView;
    EditText fullNameEditText;
    EditText carNumberEditText;
    Button nextButton;
    private String TAG = "Visa Fragment";
    public VisaPaymentFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mView = inflater.inflate(R.layout.fragment_visa_payment, container, false);
        fullNameEditText = (EditText) mView.findViewById(R.id.fullNameOnCardEditText);
        carNumberEditText = (EditText) mView.findViewById(R.id.cardNumberEditText);
        nextButton = (Button) mView.findViewById(R.id.nextButton);

//        ConnectivityManager connMgr = (ConnectivityManager)
//                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).productsAPICall();
//        } else {
//            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                    .setActionTextColor(Color.RED)
//                    .show();
//        }
        return mView;
    }


//    public class GetProductsCallback extends AsyncCallback {
//        public void onTaskComplete(String response) {
//            try {
//                JSONObject mMembersJSON;
//                mMembersJSON = new JSONObject(response);
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
//                int length = jsonArray.length();
//
//                for(int i=0;i<length;i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Products item = new Products();
//                    item.setId(jsonObject.getString("ID"));
//                    item.setName(jsonObject.getString("NAME"));
//                    item.setImage(jsonObject.getString("IMAGE"));
//                    JSONObject priceObject = jsonObject.getJSONObject("PRICE");
//                    item.setPriceNew(priceObject.getInt("PRICE_NEW"));
//                    item.setPriceOld(priceObject.getInt("PRICE_OLD"));
//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscount(priceObject.getInt("DISCOUNT"));
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                        item.setY(priceObject.getJSONObject("TIMER").getString("Y"));
//                        item.setM(priceObject.getJSONObject("TIMER").getString("M"));
//                        item.setD(priceObject.getJSONObject("TIMER").getString("D"));
//                        item.setH(priceObject.getJSONObject("TIMER").getString("H"));
//                        item.setI(priceObject.getJSONObject("TIMER").getString("I"));
//                        item.setS(priceObject.getJSONObject("TIMER").getString("S"));
//                        item.setInDays(priceObject.getJSONObject("TIMER").getInt("IN_DAYS"));
//                        item.setEndTime(priceObject.getInt("END_TIME"));
//                    }
//                    productsArrayList.add(item);
//                }
//
//                if(getActivity()!=null){
//                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
//                    progressBar.startAnimation(animation);
//                }
//                progressBar.setVisibility(View.GONE);
//                initializeData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }
//        }
//        @Override
//        public void onTaskCancelled() {
//        }
//        @Override
//        public void onPreExecute() {
//            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
//        }
//    }



//    private void initializeData(){
//        mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
//        mRecyclerView.setAdapter(mAdapter);
//    }

}
