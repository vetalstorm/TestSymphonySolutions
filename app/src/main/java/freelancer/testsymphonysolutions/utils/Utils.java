package freelancer.testsymphonysolutions.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.Map;

import freelancer.testsymphonysolutions.api.model.Error;
import retrofit2.Call;



public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    public static void cancelCall(Call call) {
        if (call != null)
            call.cancel();
    }


    public static String getErrorMessage(Error errors) {
        String errorMessage = "";
        //Error is not critical so popup is not need
        try {
            for (Map.Entry<String, String> entry : errors.getErrors().entrySet()) {
                if (TextUtils.isEmpty(errorMessage))
                    errorMessage += entry.getValue();
                else
                    errorMessage += "\n" + entry.getValue();
            }
            return errorMessage;
        } catch (NullPointerException e) {
            return "Неизвестная ошибка";
        }
    }



}
