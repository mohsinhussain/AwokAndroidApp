package com.awok.moshin.awok.NetworkLayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;

import com.awok.moshin.awok.Util.Constants;

import org.json.JSONObject;

import java.net.URLEncoder;
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

    public APIClient(Activity activity, Context context, AsyncCallback callback) {
        mContext = context;
        mActivity = activity;
        mCallback = callback;

    }

    public void productsAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, Constants.API_SERVER_URL+"product/", "GET", null);
    }

    public void allProductsAPICall(int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/index/"+pageCount, "GET", null);
//        mTask.execute(mContext, "http://192.168.1.9/api/webapi/public/products/", "GET", null);
    }

    public void productsFromSearchAPICall(String searchFilter, int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/search/index?"+searchFilter+"&pages="+pageCount, "GET", null);
    }

    public void productsFromCategoryAPICall(String categoryId, int pageCount) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/getproductsbycategory/"+categoryId+"/"+pageCount, "GET", null);
    }

    public void cartItemsCallBack(String userId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/cart/"+userId+"/summary/?location_id=560a8eddf26f2e024b8b4690", "GET", null);
//        mTask.execute(mContext, "http://192.168.1.9/api/webapi/public/products/getProductsByCategory/"+categoryId, "GET", null);
    }

    public void getPrimaryAddressAPICall(String userId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/primaryaddress//"+userId, "GET", null);
    }

    public void removeProductFromCartCall(String cartId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/cart/"+cartId+"/", "DELETE", null);
    }

    public void categoriesAPICall() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/sections", "GET", null);
    }

    public void productDetailsAPICall(String productId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/ahmed/awokapi/products/show/" + productId, "GET", null);
    }

    public void addToCartAPICall(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/cart/", "POST", dataToSend);

    }
    public void updateCart(String dataToSend,String updateId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/cart/"+updateId+"/", "PUT", dataToSend);
    }
    public void userCheckAPICall(String phoneNumber) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/check/" + phoneNumber, "GET", null);
//        mTask.execute(mContext, "http://market1.awok/v1/users/" + phoneNumber+"/check", "GET", null);
    }

    public void userForgotPassword(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/forgot_password/", "POST", dataToSend);
    }

    public void userLoginAPICall(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/login/", "POST", dataToSend);
    }

    public void useRegisterAPICall(String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/register/", "POST", dataToSend);
    }

    public void userLogoutAPICall(String phoneNumber) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/bengalua/logout/" + phoneNumber, "GET", null);
    }

    public void OrderCheckOutCallBack(String dataToSend) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/khalid/api/order/", "POST", dataToSend);
    }

    public void OrderHistoryItemsCallBack(String userId,String id,String from,String to) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/khalid/api/order/"+userId+"/my-orders/?"+"status=&date_from="+from+"&date_to="+to, "GET", null);
    }

    public void OrderHistoryDetailsItemsCallBack(String orderId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/khalid/api/order/"+orderId+"/", "GET", null);

    }

    public void ProductSpecsAPICall(String productId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/products/5616592d3b90a7740b000080/getproductspecs", "GET", null);

    }


    public void ShippingsAPICall(String productId, String quantity, String locationId, String variantId) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/v1/shippings/"+productId+"/product-shippings?quantity="+quantity+"&location_id="+locationId, "GET", null);

    }


    public void dynamicFiltersAPICall(String catId) {
        mTask = new AsyncTaskWithDialog();
        if (catId!=null){
            mTask.execute(mContext, "http://market1.awok/setti/api/search/filterscreen/"+catId, "GET", null);
        }
        else{
            mTask.execute(mContext, "http://market1.awok/setti/api/search/filterscreen/", "GET", null);
        }
    }
    public void addressCallBack(String user_id) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/index/"+user_id, "GET", null);
    }

    public void setPrimaryAddressAPICall(String addressId) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/selectedaddress/"+addressId, "PUT", null);
    }

    public void removeAddressAPICall(String addressId) {

        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/remove/"+addressId, "DELETE", null);
    }

    public void editAddressAPICall(String addressId, String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/edit/"+addressId, "PUT", dataToSend);
    }

    public void countryCallBack() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/", "GET", null);
    }

    public void StateCallBack(String country_id) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/"+country_id, "GET", null);
    }

    public void CityCallBack(String state_id) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/locations/index/"+state_id, "GET", null);
    }

    public void addAddressAPICall(String userId, String dataToSend) {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/setti/api/addresses/index/"+userId, "POST", dataToSend);
    }

    public void orderStatusCallBack() {
        mTask = new AsyncTaskWithDialog();
        mTask.execute(mContext, "http://market1.awok/khalid/api/order-status/", "GET", null);
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

}
