package freelancer.testsymphonysolutions.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import freelancer.testsymphonysolutions.ErrorDialog;
import freelancer.testsymphonysolutions.R;
import freelancer.testsymphonysolutions.adapters.SeasonsAdapter;
import freelancer.testsymphonysolutions.api.ApiModel;
import freelancer.testsymphonysolutions.api.model.Error;
import freelancer.testsymphonysolutions.api.model.Season;
import freelancer.testsymphonysolutions.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ErrorDialog.OnResultActionForActivities{
    private RecyclerView seasonsRecyclerView;
    private Call<ArrayList<Season>> requestSeasons;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        initUI();
        requestSeasonsCall();
    }
    private void initUI() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSeasonsCall();
                swipeRefresh.setRefreshing(false);
            }
        });
        seasonsRecyclerView = (RecyclerView) findViewById(R.id.rvSeasons);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        seasonsRecyclerView.setLayoutManager(layoutManager);
    }

    private void initAdapter(ArrayList<Season> seasonsList) {
        SeasonsAdapter adapter = new SeasonsAdapter(this, seasonsList);
        seasonsRecyclerView.setAdapter(adapter);
    }

    private void requestSeasonsCall() {

        requestSeasons = ApiModel.getClient().getSoccerSeasons(getString(R.string.api_token), getString(R.string.api_response_control));
        requestSeasons.enqueue(new Callback<ArrayList<Season>>() {
            @Override
            public void onResponse(Call<ArrayList<Season>> call, Response<ArrayList<Season>> response) {
                if (response.isSuccessful()) {
                    initAdapter(response.body());
                } else {
                    Error errors = ApiModel.ErrorUtils.parseError(response);
                    showErrorDialog(
                            ErrorDialog.ACTIVITY_MAIN_SERVER_ERROR, Utils.getErrorMessage(errors));


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Season>> call, Throwable t) {

                if (!call.isCanceled()) {
                    showErrorDialog(
                            ErrorDialog.ACTIVITY_MAIN_CONNECTION_ERROR, "");
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.cancelCall(requestSeasons);
    }
    public void showErrorDialog(int errorType, String errorMessage) {
        Log.d("showErrorDialog", errorType +" errorType");
        ErrorDialog connectionErrorDialog = ErrorDialog.newInstance(errorType, errorMessage);
        connectionErrorDialog.setCancelable(false);
        connectionErrorDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void actionOne(int errorType) {
        finish();
    }

    @Override
    public void actionTwo(int errorType) {
        requestSeasonsCall();
    }
}
