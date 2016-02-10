package com.awok.moshin.awok.NetworkLayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.Utilities;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohsin on 9/9/2015.
 */
public class APIClient {
    private String TAG = "APIClient";
    private AsyncTaskWithDialog mTask;
    private Context mContext;
    private Activity mActivity;
    private AsyncCallback mCallback;
    private int CONTEXT_INDEX = 0;
    private int URI_INDEX = 1;
    private int METHOD_INDEX = 2;
    private int PARAMS_INDEX = 3;
    private String timeUnique;

    public APIClient(Activity activity, Context context, AsyncCallback callback) {
        mContext = context;
        mActivity = activity;
        mCallback = callback;

    }

    public void productsAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, Constants.API_SERVER_URL+"product/", "GET", null);
    }

  /*  public void allProductsAPICall(int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/products/index?page=" + pageCount, "GET", null);  //--------------V1 api
//        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/index/"+pageCount, "GET", null);  ----------old api
//        mTask.execute(mContext, "http://192.168.1.9/api/webapi/public/products/", "GET", null);
    }*/
 //   http://mp.alifca.com/api/products/?category_id=ALL&page=1&count=40


    public void allProductsAPICall(int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/products/?category_id=ALL&page=" + pageCount, "GET", null);  //--------------V1 api
//        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/index/"+pageCount, "GET", null);  ----------old api
//        mTask.execute(mContext, "http://192.168.1.9/api/webapi/public/products/", "GET", null);
    }


    public void productsFromSearchAPICall(String searchFilter, int pageCount) {
        mTask = new AsyncTaskWithDialog();
//        mTask.execute(mContext, "http://market1.awok/v1/search?"+searchFilter+"&pages="+pageCount, "GET", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/products/?"+searchFilter+"&pages="+pageCount, "GET", null);
    }

    /*public void productsFromCategoryAPICall(String categoryId, int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/sections/"+categoryId+"/show?page="+pageCount, "GET", null);
//        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/getproductsbycategory/"+categoryId+"/"+pageCount, "GET", null);
    }*/
    public void productsFromCategoryAPICall(String categoryId, int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/products/?category_id="+categoryId+"&page="+pageCount, "GET", null);
//        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/getproductsbycategory/"+categoryId+"/"+pageCount, "GET", null);
    }
    //http://mp.alifca.com/api/products/?category_id=582&page=1&count=40

    public void cartItemsCallBack(String userId,String locationId) {
        mTask = new AsyncTaskWithDialog();
       // mTask.execute(mContext, "http://market1.awok/v1/cart/"+userId+"/summary/?location_id=560a8eddf26f2e024b8b4690", "GET", null);
//        mTask.execute(mContext, "http://192.168.1.9/api/webapi/public/products/getProductsByCategory/"+categoryId, "GET", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/cart/?location="+locationId, "GET", null);


    }

    public void getPrimaryAddressAPICall(String userId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/primaryaddress/", "GET", null);
    }

    public void orderSummaryItemsCallBack(String userId,String locationId) {
        mTask = new AsyncTaskWithDialog();
        if(locationId.equals(""))
        {
            mTask.execute(mContext, "http://mp.alifca.com/api/checkout/?action=get-summary", "GET", null);
        }
        else {
            mTask.execute(mContext, "http://mp.alifca.com/api/checkout/?address_id=" + locationId + "&action=get-summary", "GET", null);
        }
    }



    public void removeProductFromCartCall(String cartId,int cid) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/v1/cart/?id="+cartId+"&location_id=560a8eddf26f2e024b8b4690", "DELETE", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/cart/?user_id="+cartId+"&ACTION=REMOVE&CID="+cid, "DELETE", null);
    }

    /*public void categoriesAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/sections", "GET", null);
    }*/

    //http://mp.alifca.com/api/categories/?level=1
    public void getOffersCatrgory(String data) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/offers/?"+data, "GET", null);
    }




    public void getOffersMainCatData(String data) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/offers/?"+data, "GET", null);
    }





    public void getProductDetailsRecommendedRelatedCategory(String data,int page) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/offers/?"+data+"&page="+page, "GET", null);
    }


    public void getProductDetailsRecommendedRelated(String catId,int page) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/products/?category_id="+catId+"&page="+page, "GET", null);
    }

    public void categoriesAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/categories/?level=1", "GET", null);
    }

/*    public void productDetailsAPICall(String productId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/products/"+productId+"/show", "GET", null);
        //mTask.execute(mContext, "http://192.168.1.205/mpapi/Products/"+productId+"/show", "GET", null);

    }*/
public void getProfileDetails(String userId) {
    mTask = new AsyncTaskWithDialog();
    // mTask.execute(mContext, "http://mp.alifca.com/api/products/?product_id="+productId, "GET", null);
    mTask.execute(mContext, "http://mp.alifca.com/api/profile/", "GET", null);

    //mTask.execute(mContext, "http://192.168.1.205/mpapi/Products/"+productId+"/show", "GET", null);

}


public void productDetailsAPICall(String productId) {
    mTask = new AsyncTaskWithDialog();
   // mTask.execute(mContext, "http://mp.alifca.com/api/products/?product_id="+productId, "GET", null);
    mTask.execute(mContext, "http://mp.alifca.com/api/products/?product_id="+productId, "GET", null);

    //mTask.execute(mContext, "http://192.168.1.205/mpapi/Products/"+productId+"/show", "GET", null);

}



    public void addToCartAPICall(String userId,String sku,String uid,String ssid) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/v1/cart/", "POST", dataToSend);
        mTask.execute(mContext, "http://mp.alifca.com/api/cart/?SKU="+sku+"&UID="+uid+"&SSID="+ssid, "GET", null);

    }
    public void updateCart(String dataToSend,String updateId) {
        mTask = new AsyncTaskWithDialog();
       // mTask.execute(mContext, "http://market1.awok/v1/cart/?id="+updateId+"&location_id=560a8eddf26f2e024b8b4690", "PUT", dataToSend);
        mTask.execute(mContext, "http://mp.alifca.com/api/cart/", "PUT", dataToSend);

    }
    public void userCheckAPICall(String email) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/bengalua/check/" + phoneNumber, "GET", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=validate&email="+email, "GET", null);
//        mTask.execute(mContext, "http://market1.awok/v1/users/" + phoneNumber+"/check", "GET", null);
    }

    public void userForgotPassword(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/forgot_password/", "POST", dataToSend);
    }

    public void userLoginAPICall(String userId,String password) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/bengalua/login/", "POST", dataToSend);
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=login&email="+userId+"&password="+password, "GET", null);
    }

    public void useRegisterAPICall(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/bengalua/register/", "POST", dataToSend);
             mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=register", "POST", dataToSend);

    }

    public void userLogoutAPICall(String userId) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/bengalua/logout/" + phoneNumber, "GET", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=logout", "GET", null);

    }

    public void OrderCheckOutCallBack(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/order/", "POST", dataToSend);
    }


    public void reSendCallBack(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=send-verify-sms", "POST", dataToSend);
    }


    public void reSendSms(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=resend-verify-sms", "POST", dataToSend);
    }


    public void verifySms(String code) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=verify-sms-code&code="+code, "GET", null);
    }


   /* public void OrderHistoryItemsCallBack(String userId,String id,String from,String to) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/order/"+userId+"/list/?page="+"&status="+id+"&date_from="+from+"&date_to="+to, "GET", null);
    }*/

    public void paymentVerifySms(String code,String orderId) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=verify-sms-code&code="+code+"&order_id="+orderId, "GET", null);
    }


    public void paymentReSendSms(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/checkout/?action=resend-verify-sms", "POST", dataToSend);
    }



    public void paymentReSendCallBack(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/checkout/?action=send-verify-sms", "POST", dataToSend);
    }

    public void OrderHistoryItemsCallBack(String userId,String id,String from,String to,int currentPage) {
        mTask = new AsyncTaskWithDialog();
        if(from.equals("") || to.equals(""))
        {
            mTask.execute(mContext, "http://mp.alifca.com/api/orders/?page=" + currentPage, "GET", null);
        }
        else if(id.equals(""))
        {
            mTask.execute(mContext, "http://mp.alifca.com/api/orders/?page=" + currentPage+ "&filter_date_from=" + from + "&filter_date_to=" + to, "GET", null);
        }
        else {
            mTask.execute(mContext, "http://mp.alifca.com/api/orders/?page=" + currentPage + "&filter_status=" + id + "&filter_date_from=" + from + "&filter_date_to=" + to, "GET", null);
        }
    }


    public void OrderHistoryDetailsItemsCallBack(String orderId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/order/?id="+orderId, "GET", null);

    }

    public void ProductSpecsAPICall(String productId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/products/5616592d3b90a7740b000080/getproductspecs", "GET", null);

    }


 /*   public void ShippingsAPICall(String productId, String quantity, String locationId, String variantId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/shippings/"+productId+"/product-shippings?quantity="+quantity+"&location_id="+locationId, "GET", null);

    }*/
 public void ShippingsAPICall(String productId, String quantity, String locationId, String variantId) {
     mTask = new AsyncTaskWithDialog();
     //mTask.execute(mContext, "http://mp.alifca.com/api/shippings/?product_id="+productId+"&quantity="+quantity+"&location_id="+locationId, "GET", null);
     mTask.execute(mContext, "http://mp.alifca.com/api/shippings/?product_id="+productId+"&quantity="+quantity+"&location_id=", "GET", null);

 }

    public void dynamicFiltersAPICall(String catId) {
        mTask = new AsyncTaskWithDialog();
        if (catId!=null){
           // mTask.execute(mContext, "http://market1.awok/setti/api/search/filterscreen/"+catId, "GET", null);
            mTask.execute(mContext, "http://mp.alifca.com/api/filters/?category_id="+catId, "GET", null);

        }
        else{
           //  mTask.execute(mContext, "http://market1.awok/setti/api/search/filterscreen/", "GET", null);

            mTask.execute(mContext, "http://mp.alifca.com/api/filters/?category_id=", "GET", null);
        }
    }
    public void addressCallBack(String user_id) {

        mTask = new AsyncTaskWithDialog();
       // mTask.execute(mContext, "http://market1.awok/setti/api/addresses/index/"+user_id, "GET", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/addresses/?user_id="+user_id+"&page=1", "GET", null);

    }

    public void setPrimaryAddressAPICall(String addressId,String userId) {

        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/setti/api/addresses/selectedaddress/"+addressId, "PUT", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/addresses/?id="+addressId+"&action=mark-primary", "PUT", null);


    }

    public void removeAddressAPICall(String addressId,String userId) {

        mTask = new AsyncTaskWithDialog();
       // mTask.execute(mContext, "http://market1.awok/setti/api/addresses/remove/"+addressId, "DELETE", null);
        mTask.execute(mContext, "http://mp.alifca.com/api/addresses/?id="+addressId, "DELETE", null);

    }

    public void editAddressAPICall(String addressId, String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        //mTask.execute(mContext, "http://market1.awok/setti/api/addresses/edit/"+addressId, "PUT", dataToSend);
        mTask.execute(mContext, "http://mp.alifca.com/api/addresses/?&id="+addressId, "PUT", dataToSend);

    }

    public void countryCallBack() {
        mTask = new AsyncTaskWithDialog();
       // mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/", "GET", null);

        mTask.execute(mContext, "http://mp.alifca.com/api/locations/", "GET", null);

    }

    public void StateCallBack(String country_id) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/"+country_id, "GET", null);
    }

    public void CityCallBack(String state_id) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/"+state_id, "GET", null);
    }

   /* public void addAddressAPICall(String userId, String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/index/"+userId, "POST", dataToSend);
    }*/





    public void addAddressAPICall(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/addresses/?", "POST", dataToSend);
    }


   /* public void orderStatusCallBack() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/khalid/api/order-status/", "GET", null);
    }*/






    public void orderStatusCallBack() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/order-statuses/", "GET", null);
    }




    public void disputeOpenCallBack(String cartId,String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/disputes/"+cartId+"/open", "POST", dataToSend);
    }

    public void disputeDetailsCallBack(String cart_id) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/disputes/"+cart_id+"/get", "GET", null);
    }

    public void disputeModifyCallBack(String cartId, String s) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/disputes/"+cartId+"/edit", "PUT", s);
    }

    public void disputeCancelCallBack(String disputeId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/disputes/"+disputeId+"/canceldispute", "PUT", null);
    }

    public void DisputeListCallBack(String s) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/disputes/"+s+"/disputeList", "GET", null);
    }

    /*public void productReviewCommentsCallBack(String s,int page) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/products/"+s+"/getproductcomments?page="+page, "GET", null);
    }*/
    public void productReviewCommentsCallBack(String s,int page) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/reviews/?id="+s+"&mode=product&page="+page, "GET", null);
    }

    public void productStoreCommentsCallBack(String id,int current_page) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/stores/"+id+"/getstorecomments?page="+current_page, "GET", null);
    }

    public void getUpdateProfile(String userId,String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/profile/", "POST", dataToSend);
    }



    public void getUpdateProfilePicture(String userId,String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user-profile-pic/", "POST", dataToSend);
    }

    public void getDashboardDetails(String userId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user-dashboard/", "GET", null);

    }

    public void PlaceOrderCallBack(String dataToSend) {


        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/checkout/", "POST", dataToSend);

    }

    public void PayCallBackCallBack(String orderId,String userId,String  payId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/checkout/?order_id="+orderId+"&pay_method_id="+payId+"&action=update-pay-method&os=android", "PUT", null);
    }



    public void getOffers() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/offers-list/", "GET", null);
    }

    public void productBundleDetailsAPICall(String productId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/bundles/?id="+productId, "GET", null);

    }

    public void loginFb(String token) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://mp.alifca.com/api/user/?mode=fb-login&token="+token,"GET", null);
    }


    public class AsyncTaskWithDialog extends AsyncTask<Object, Void, String> {

        @Override
        protected void onPreExecute() {
            mCallback.onPreExecute();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Object... parameters) {
            String postResponse = "";
            try {
                Context context = (Context) parameters[CONTEXT_INDEX];
                String url = (String) parameters[URI_INDEX];
//                url = URLEncoder.encode(url);
                HTTPClient client = new HTTPClient(
                        context.getSharedPreferences(Constants.PREFS_NAME, 0));

                String params = (String) parameters[PARAMS_INDEX];
                System.out.println("URL: "+url);

                if (parameters[METHOD_INDEX] == "POST") {
                        postResponse = client.post(url, params);
                    return postResponse;

                } else if (parameters[METHOD_INDEX] == "GET")
                {
                    String resp = client.get(url, params);
                    Log.v(TAG, "resp: "+resp);
                    return resp;
                }
                else if (parameters[METHOD_INDEX] == "PATCH")
                {
                    String resp = client.patch(url, params);
                    Log.v(TAG, "resp: "+resp);
                    return resp;
                }

                else if (parameters[METHOD_INDEX] == "PUT")
                    return client.put(url, params);
                else if (parameters[METHOD_INDEX] == "DELETE") {
                    System.out.println("DELETE");
                    return client.delete(url, params);
                }
                return Constants.Success.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "EXCEPTION: " + e.toString());
            }
            return Constants.Exception.toString();
        }

        @Override
        protected void onPostExecute(final String response) {
            mTask = null;
            mCallback.onTaskComplete(response);
        }

        @Override
        protected void onCancelled() {
            mTask = null;
            mCallback.onTaskCancelled();
        }
    }


    public String currentTime()
    {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        timeUnique=c.getTime().toString();
        return timeUnique;
    }

}
