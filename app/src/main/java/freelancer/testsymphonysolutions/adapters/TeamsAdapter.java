package freelancer.testsymphonysolutions.adapters;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.TextView;
import java.util.ArrayList;
import freelancer.testsymphonysolutions.R;
import freelancer.testsymphonysolutions.api.model.Team;


public class TeamsAdapter extends  RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private ArrayList<Team> teamsList = new ArrayList<>();

    public TeamsAdapter( ArrayList<Team> teamsList) {

        this.teamsList.clear();
        this.teamsList.addAll(teamsList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private WebView wvTeamPicture;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            wvTeamPicture = (WebView) itemView.findViewById(R.id.wvTeamPicture);
            wvTeamPicture.getSettings().setJavaScriptEnabled(true);
            wvTeamPicture.getSettings().setBuiltInZoomControls(true);
        }
    }

    @Override
    public TeamsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);

        return new TeamsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Team currentTeam = teamsList.get(position);
        holder.tvName.setText(currentTeam.getName());
        if(currentTeam.getCrestUrl()!=null) {
            String data = "<html><body ><img id=\"resizeImage\" src=\""
                    +currentTeam.getCrestUrl()+"\" width=\"100%\"  height=\"80%\" alt=\"\" align=\"middle\" /></body></html>";
            holder.wvTeamPicture.loadData(data, "text/html; charset=UTF-8", null);
        }
        else{
            holder.wvTeamPicture.loadUrl("file:///android_res/mipmap/no_image.png");
        }
      }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }


}
