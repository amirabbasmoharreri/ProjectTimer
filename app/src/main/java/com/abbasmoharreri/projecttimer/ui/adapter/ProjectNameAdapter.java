package com.abbasmoharreri.projecttimer.ui.adapter;

import android.content.Context;
import android.net.sip.SipSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abbasmoharreri.projecttimer.R;
import com.abbasmoharreri.projecttimer.model.Project;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class ProjectNameAdapter extends RecyclerView.Adapter<ProjectNameAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private Context context;
    private List<Project> projects;
    private View.OnClickListener listener;
    private int position;

    public ProjectNameAdapter(Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View contentView = inflater.inflate( R.layout.project_card, parent, false );
        viewHolder = new ViewHolder( contentView );

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView cardView = holder.cardView;
        TextView projectName = holder.projectName;
        TextView time = holder.time;
        this.position = position;

        cardView.setOnClickListener( listener );

        Project project = projects.get( position );

        projectName.setText( project.getName() );
        time.setText( project.getStringTime() );

    }

    public String getName() {
        return projects.get( position ).getName();
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView projectName, time;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            projectName = itemView.findViewById( R.id.card_project_name );
            time = itemView.findViewById( R.id.card_time );
            cardView = itemView.findViewById( R.id.card_project );
        }
    }
}
