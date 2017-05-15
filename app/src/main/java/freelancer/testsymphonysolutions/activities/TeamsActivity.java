package freelancer.testsymphonysolutions.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import freelancer.testsymphonysolutions.ErrorDialog;
import freelancer.testsymphonysolutions.R;

import freelancer.testsymphonysolutions.adapters.TeamsAdapter;
import freelancer.testsymphonysolutions.api.ApiModel;
import freelancer.testsymphonysolutions.api.model.Error;
import freelancer.testsymphonysolutions.api.model.Team;
import freelancer.testsymphonysolutions.api.model.TeamsResponse;
import freelancer.testsymphonysolutions.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeamsActivity extends AppCompatActivity implements ErrorDialog.OnResultActionForActivities{
    public final static String TEAMS_BUNDLE ="teams_bundle";
    private RecyclerView teamsRecyclerView;
    private Call<TeamsResponse> requestTeams;
    private long seasonId;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        initUI();
        Bundle bundle = getIntent().getBundleExtra(TEAMS_BUNDLE);
        seasonId = bundle.getLong("id");
        requestSeasonTeams();
    }
    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView header = (TextView)  findViewById(R.id.toolbarTitle);
        header.setText(getString(R.string.teams_title));
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSeasonTeams();
                swipeRefresh.setRefreshing(false);
            }
        });
        teamsRecyclerView = (RecyclerView) findViewById(R.id.rvSeasons);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        teamsRecyclerView.setLayoutManager(layoutManager);
    }

    private void initAdapter(ArrayList<Team> teamsList) {
        TeamsAdapter adapter = new TeamsAdapter(teamsList);
        teamsRecyclerView.setAdapter(adapter);
    }

    private void requestSeasonTeams() {

        requestTeams = ApiModel.getClient().getSoccerSeasonTeams(getString(R.string.api_token), getString(R.string.api_response_control), seasonId);
        requestTeams.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(Call<TeamsResponse> call, Response<TeamsResponse> response) {
                if (response.isSuccessful()) {
                    initAdapter(response.body().getTeams());
                } else {
                    Error errors = ApiModel.ErrorUtils.parseError(response);
                    showErrorDialog(
                            ErrorDialog.ACTIVITY_TEAMS_SERVER_ERROR, Utils.getErrorMessage(errors));
                }
            }

            @Override
            public void onFailure(Call<TeamsResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    showErrorDialog(
                            ErrorDialog.ACTIVITY_TEAMS_CONNECTION_ERROR, "");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.cancelCall(requestTeams);
    }
    public void showErrorDialog(int errorType, String errorMessage) {
        Log.d("showErrorDialog", errorType +" errorType");
        ErrorDialog connectionErrorDialog = ErrorDialog.newInstance(errorType, errorMessage);
        connectionErrorDialog.setCancelable(false);
        connectionErrorDialog.show(getSupportFragmentManager(), "");
        Log.d("final step", errorType +" errorType");
    }

    @Override
    public void actionOne(int errorType) {
        finish();
    }

    @Override
    public void actionTwo(int errorType) {
        requestSeasonTeams();
    }
}
