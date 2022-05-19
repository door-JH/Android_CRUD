package com.example.ps4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ps4.Adapter.SpinnerAdapter;
import com.example.ps4.Model.Category;
import com.example.ps4.Model.Student;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    private EditText num, name, phone;
    private TextView message;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private Spinner dept;
    private AlertDialog deleteAlert;

    SpinnerAdapter spinnerAdapter;

    List<Category> cgs = new ArrayList<Category>();
    List<String> deptno;
    List<String> deptKor;

    Student s;
    Category c;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = findViewById(R.id.num);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_select = findViewById(R.id.btn_select);
        btn_save = findViewById(R.id.btn_save);
        btn_delete = findViewById(R.id.btn_delete);
        btn_clear = findViewById(R.id.btn_clear);

        message = findViewById(R.id.message);

        dept = findViewById(R.id.dept);

        db = new DBHelper(getApplicationContext());

        ViewGroup.LayoutParams select_params = btn_select.getLayoutParams();
        select_params.height = 120;
        btn_select.setLayoutParams(select_params);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText().toString().trim())){
                    s = db.selectStudent(num.getText().toString().trim());

                    if(s != null){
                        ONSETUI(s);
                    } else {
                        ONSETNULL();
                    }
                } else {
                    ONSETNULL();
                }
                db.CloseDB();
            }
        });

        ViewGroup.LayoutParams save_params = btn_save.getLayoutParams();
        save_params.height = 120;
        btn_save.setLayoutParams(save_params);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText().toString().trim())){

                    s = db.insertUpdate(num.getText().toString().trim(),
                                        name.getText().toString().trim(),
                                        phone.getText().toString().trim(),
                                        deptno.get(dept.getSelectedItemPosition()));
                    db.CloseDB();
                }
                ONSETNULL();
            }
        });

        ViewGroup.LayoutParams clear_params = btn_clear.getLayoutParams();
        clear_params.height = 120;
        btn_clear.setLayoutParams(clear_params);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ONSETNULL();
            }
        });

        ViewGroup.LayoutParams delete_params = btn_delete.getLayoutParams();
        delete_params.height = 120;
        btn_delete.setLayoutParams(delete_params);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText().toString().trim())){
                    deleteAlert = deleteconfirm();
                    deleteAlert.show();
                }
            }
        });

        makeDeptList();
    }


    public AlertDialog deleteconfirm(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final View innerview =
                getLayoutInflater().inflate(R.layout.dialog_delete_confirm, null);
        ab.setView(innerview);

        ab.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean rc = db.deleteStudent(num.getText().toString().trim());

                if(!rc){
                    message.setText("ERROR ON DELETE");
                } else {
                    message.setText("DELETED");
                }
            }
        });

        ab.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setdissmiss(deleteAlert);
            }
        });

        return ab.create();
    }

    public void setdissmiss(Dialog dialog){
        if(dialog.isShowing() && dialog != null){
            dialog.dismiss();
        }
    }

    public void ONSETUI(Student s){
        num.setText(s.getNum());
        name.setText(s.getName());
        phone.setText(s.getPhone());
        dept.setSelection(deptno.indexOf(s.getDept()));
    }

    public void ONSETNULL(){
        num.setText("");
        name.setText("");
        phone.setText("");

    }

    public void setDeptList(){

        deptno = new ArrayList<String>();
        deptKor = new ArrayList<String>();

        cgs = db.selectCategory("dept");
        db.CloseDB();

        for(int q = 0; q < cgs.size(); q++){
            deptno.add(cgs.get(q).getCode());
            deptKor.add(cgs.get(q).getCodeKor());
        }
    }

    public void makeDeptList(){
        setDeptList();

        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, deptKor);
        dept.setAdapter(spinnerAdapter);

        if(deptKor.size() > 0){
            dept.setSelection(dept.getSelectedItemPosition());
        } else {
            deptno.add("404");
            deptKor.add("DATA NOT FOUND");
            dept.setSelection(0);
        }
        spinnerAdapter.notifyDataSetChanged();
    }

}
