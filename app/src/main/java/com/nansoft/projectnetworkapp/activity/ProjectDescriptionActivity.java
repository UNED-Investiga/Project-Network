package com.nansoft.projectnetworkapp.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nansoft.projectnetworkapp.R;
import com.nansoft.projectnetworkapp.model.Proyecto;

public class ProjectDescriptionActivity extends AppCompatActivity {

    Proyecto project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = new Proyecto();

        // intentamos obtener el objeto que se envió
        try
        {
            project = (Proyecto) getIntent().getParcelableExtra("project");
        }
        catch (Exception e)
        {

        }

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        TextView txtvProjectName = (TextView) findViewById(R.id.txtvProjectName);
        txtvProjectName.setText(project.nombre);


        TextView txtvProjectDescription = (TextView) findViewById(R.id.txtvProjectDescription);
        txtvProjectDescription.setText(project.descripcion);

        TextView txtvProjectArea = (TextView) findViewById(R.id.txtvProjectArea);
        txtvProjectArea.setText(project.nombreArea);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
