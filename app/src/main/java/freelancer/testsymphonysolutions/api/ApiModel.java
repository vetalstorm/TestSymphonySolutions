package freelancer.testsymphonysolutions.api;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import freelancer.testsymphonysolutions.api.model.Error;
import freelancer.testsymphonysolutions.api.model.Season;
import freelancer.testsymphonysolutions.api.model.TeamsResponse;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;



public class ApiModel {
    private static String ENDPOINT = "http://api.football-data.org/v1/";
    private static Retrofit retrofit;

    public static ApiService getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient
                    //TODO remove this
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS);
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!


            retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @GET("soccerseasons")
        Call<ArrayList<Season>> getSoccerSeasons(
                @Header("X-Auth-Token") String token,
                @Header("X-Response-Control") String xResponseControl);

        @GET("soccerseasons/{seasonId}/teams")
        Call<TeamsResponse> getSoccerSeasonTeams(
                @Header("X-Auth-Token") String token,
                @Header("X-Response-Control") String xResponseControl,
                @Path("seasonId") long seasonId);
    }

    public static class ErrorUtils {

        public static Error parseError(Response<?> response) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …
            // add logging as last interceptor
            httpClient.addInterceptor(logging);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Converter<ResponseBody, Error> converter =
                    retrofit.responseBodyConverter(Error.class, new Annotation[0]);
            Error error;
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new Error();
            }
            return error;
        }
    }
}
