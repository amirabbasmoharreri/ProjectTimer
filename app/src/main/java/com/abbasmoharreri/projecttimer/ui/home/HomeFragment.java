package com.abbasmoharreri.projecttimer.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abbasmoharreri.projecttimer.R;
import com.abbasmoharreri.projecttimer.dataBase.DataBaseController;
import com.abbasmoharreri.projecttimer.dataBase.FetchProject;
import com.abbasmoharreri.projecttimer.model.CountUpTimer;
import com.abbasmoharreri.projecttimer.model.Project;
import com.abbasmoharreri.projecttimer.model.Time;
import com.abbasmoharreri.projecttimer.ui.adapter.ProjectNameAdapter;
import com.abbasmoharreri.projecttimer.ui.popup.DialogAddProject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private TextView projectName;
    private Button button;
    private Boolean startTimer = false;
    private Chronometer chronometer;
    private RecyclerView recyclerView;
    private List<Project> projects;
    private int position;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProjectNameAdapter projectNameAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate( R.layout.fragment_home, container, false );
        projectName = root.findViewById( R.id.text_project_name );
        try {
            assert getArguments() != null;
            projectName.setText( getArguments().getString( "ProjectName" ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = root.findViewById( R.id.home_recycle_view );
        chronometer = root.findViewById( R.id.timer_project );

        button = root.findViewById( R.id.button_timer );
        button.setOnClickListener( this );

        swipeRefreshLayout = root.findViewById( R.id.home_recycle_view_container );
        swipeRefreshLayout.setOnRefreshListener( this );

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        projects = new ArrayList<>();
        setRecyclerView();
    }

    private void setRecyclerView() {
        try {
            FetchProject fetchProject = new FetchProject( getContext() );
            projectNameAdapter = new ProjectNameAdapter( getContext(), fetchProject.getList() );
            projects = fetchProject.getList();
            projectNameAdapter.setListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewGroup parent = (ViewGroup) v.getParent();
                    ViewGroup parent1 = (ViewGroup) parent.getParent();
                    int index = parent1.indexOfChild( parent );
                    position = index;
                    projectName.setText( projects.get( index ).getName() );
                }
            } );
            recyclerView.setAdapter( projectNameAdapter );
            recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_timer) {

            if (!startTimer) {

                chronometer.setBase( SystemClock.elapsedRealtime() );
                chronometer.start();
                startTimer = true;

            } else {

                Long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                String st = convertLongToTime( time );
                chronometer.setBase( SystemClock.elapsedRealtime() );
                chronometer.stop();

                Toast.makeText( getContext(), st, Toast.LENGTH_SHORT ).show();
                DataBaseController dataBaseController = new DataBaseController( getContext() );
                Time time1 = new Time();
                Project project = projects.get( position );
                time1.setProjectId( project.getId() );
                time1.setTime( time );
                dataBaseController.insertTime( time1 );
                project.setTime( dataBaseController.fetchTime( projects.get( position ) ) );
                dataBaseController.updateProject( project );
                startTimer = false;
                setRecyclerView();
            }

        }


    }


    private String convertLongToTime(Long time1) {

        Long time = time1;
        int h = (int) (time / 3600000);
        int m = (int) (time - h * 3600000) / 60000;
        int s = (int) (time - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);


        return t;
    }

    @Override
    public void onRefresh() {
        setRecyclerView();
        swipeRefreshLayout.setRefreshing( false );
    }
}
