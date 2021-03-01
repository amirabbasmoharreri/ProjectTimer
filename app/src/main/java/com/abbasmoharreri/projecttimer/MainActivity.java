package com.abbasmoharreri.projecttimer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.abbasmoharreri.projecttimer.dataBase.FetchProject;
import com.abbasmoharreri.projecttimer.ui.adapter.ProjectNameAdapter;
import com.abbasmoharreri.projecttimer.ui.home.HomeFragment;
import com.abbasmoharreri.projecttimer.ui.popup.DialogAddProject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        final Context context = this;
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddProject dialogAddProject = new DialogAddProject( context );
                dialogAddProject.setOnDismissListener( new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        setRecyclerView();
                    }
                } );
                dialogAddProject.show();
            }
        } );

       /* recyclerView = findViewById( R.id.nav_recycle_projects );
        drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home )
                .setDrawerLayout( drawer )
                .build();
        navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        NavigationUI.setupActionBarWithNavController( this, navController, mAppBarConfiguration );
        NavigationUI.setupWithNavController( navigationView, navController );*/

        setRecyclerView();
    }


    private void setRecyclerView() {
        try {
            FetchProject fetchProject = new FetchProject( this );
            final ProjectNameAdapter projectNameAdapter = new ProjectNameAdapter( this, fetchProject.getList() );
            projectNameAdapter.setListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString( "ProjectName", projectNameAdapter.getName() );
                    // set Fragmentclass Arguments
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments( bundle );

                    Toast.makeText( getApplicationContext(), projectNameAdapter.getName(), Toast.LENGTH_SHORT ).show();
                }
            } );
            recyclerView.setAdapter( projectNameAdapter );
            recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController( this, R.id.nav_host_fragment );
        return NavigationUI.navigateUp( navController, mAppBarConfiguration )
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            /*if (!item.isChecked()) {
                item.setChecked( true );
                DialogAddProject dialogAddProject = new DialogAddProject( this );
                dialogAddProject.show();
            }*/
        }

        NavigationUI.onNavDestinationSelected( item, navController );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

}
