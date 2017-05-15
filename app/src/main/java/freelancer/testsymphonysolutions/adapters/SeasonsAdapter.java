package freelancer.testsymphonysolutions.adapters;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;


import freelancer.testsymphonysolutions.R;
import freelancer.testsymphonysolutions.activities.MainActivity;
import freelancer.testsymphonysolutions.activities.TeamsActivity;
import freelancer.testsymphonysolutions.api.model.Season;



public class SeasonsAdapter extends  RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {

    private MainActivity mainActivity;
    private ArrayList<Season> seasonsList = new ArrayList<>();

    public SeasonsAdapter(MainActivity activity, ArrayList<Season> seasonsList) {
        this.mainActivity = activity;
        this.seasonsList.clear();
        this.seasonsList.addAll(seasonsList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rvSeason;
        private TextView tvCaption, tvLeague, tvYear, tvMatchDay, tvMatchDays, tvTeams, tvGames;
        public ViewHolder(View itemView) {
            super(itemView);
            rvSeason = (RelativeLayout) itemView.findViewById(R.id.rvSeason);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvLeague = (TextView) itemView.findViewById(R.id.tvLeague);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvMatchDay = (TextView) itemView.findViewById(R.id.tvMatchDay);
            tvMatchDays = (TextView) itemView.findViewById(R.id.tvMatchDays);
            tvTeams = (TextView) itemView.findViewById(R.id.tvTeams);
            tvGames = (TextView) itemView.findViewById(R.id.tvGames);

        }
    }

    @Override
    public SeasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_season, parent, false);

        return new SeasonsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Season currentSeason = seasonsList.get(position);
        holder.tvCaption.setText(currentSeason.getCaption());
        holder.tvLeague.setText((" ").concat(currentSeason.getLeague()));
        holder.tvYear.setText((" ").concat(currentSeason.getYear()));
        holder.tvMatchDay.setText((" ").concat(String.valueOf(currentSeason.getCurrentMatchday())));
        holder.tvMatchDays.setText((" ").concat(String.valueOf(currentSeason.getNumberOfMatchdays())));
        holder.tvTeams.setText((" ").concat(String.valueOf(currentSeason.getNumberOfTeams())));
        holder.tvGames.setText((" ").concat(String.valueOf(currentSeason.getNumberOfGames())));
        holder.rvSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, TeamsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putLong("id", currentSeason.getId());
                intent.putExtra(TeamsActivity.TEAMS_BUNDLE, mBundle);
                mainActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

}
