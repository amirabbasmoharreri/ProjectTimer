package com.abbasmoharreri.projecttimer.ui.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.abbasmoharreri.projecttimer.R;
import com.abbasmoharreri.projecttimer.dataBase.DataBaseController;
import com.abbasmoharreri.projecttimer.model.Project;

public class DialogAddProject extends Dialog implements View.OnClickListener {

    private Button add, cancel;
    private EditText name;
    private Context context;

    public DialogAddProject(@NonNull Context context) {
        super( context );
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.dialog_add_project );

        add = findViewById( R.id.dialog_add_project_button_add );
        add.setOnClickListener( this );
        cancel = findViewById( R.id.dialog_add_project_button_cancel );
        cancel.setOnClickListener( this );
        name = findViewById( R.id.dialog_add_project_name );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_add_project_button_add:

                Project project = new Project();
                project.setName( name.getText().toString() );
                project.setTime( 0L );

                DataBaseController dataBaseController = new DataBaseController( context );
                dataBaseController.insertProject( project );
                dismiss();

                break;
            case R.id.dialog_add_project_button_cancel:
                dismiss();
                break;
        }
    }
}
