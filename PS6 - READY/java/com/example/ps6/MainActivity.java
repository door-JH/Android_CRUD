package com.example.ps6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.example.ps6.Adapter.SpinnerAdapter;
import com.example.ps6.Adapter.StudentAdapter;
import com.example.ps6.App.AppCamera;
import com.example.ps6.App.AppConfig;
import com.example.ps6.App.AppListViewHeight;
import com.example.ps6.DBHelper;
import com.example.ps6.Model.Category;
import com.example.ps6.Model.Student;
import com.example.ps6.PhotographyActivity;
import com.example.ps6.R;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;
    public static final int BITMAP_SAMPLE_SIZE = 8;

    private EditText num, name, phone;
    private TextView message;
    private Button btn_select, btn_save, btn_clear, btn_delete;
    private Spinner dept;
    private AlertDialog deleteAlert;
    private ImageView imgPreview;
    private VideoView videoPreview;
    private ListView listView;
    SpinnerAdapter spinnerAdapter;

    List<Category> cgs = new ArrayList<Category>();
    List<String> deptno;
    List<String> deptKor;

    Student s;
    List<Student> ss = new ArrayList<Student>();
    Category c;
    DBHelper db;
    Bundle args;
    String imageStoragePath;

    AppConfig ac;
    StudentAdapter stuAdapter;
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
        args = getIntent().getExtras() != null ?  getIntent().getExtras() : new Bundle();
        ac = args.getSerializable("mVar") != null ?
                (AppConfig) args.getSerializable("mVar") : new AppConfig();

        imgPreview = findViewById(R.id.imgPreview);
        videoPreview = findViewById(R.id.videoPreview);


        //함수 인자 수정
        listView = findViewById(R.id.#);
        stuAdapter = new StudentAdapter(#);
        listView.setAdapter(#);
        //


        imgPreview.setOnClickListener(new PhotographyListener());
        videoPreview.setOnClickListener(new PhotographyListener());

        ViewGroup.LayoutParams select_params = btn_select.getLayoutParams();
        select_params.height = 120;
        btn_select.setLayoutParams(select_params);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(num.getText().toString().trim())){
                    s = db.selectStudent(num.getText().toString().trim());

                    if(s != null){
                        setCurrentMarkListViewItemNewAddedStudent(s.getNum(),stuAdapter);
                        setCurrentMarkListViewItem();
                        ONSETUI(s);
                    } else {
                        ONSETNULL();
                    }
                } else {
                    ONSETNULL();
                }
                notifyListItems();
                db.CloseDB();

            }
        });

        ViewGroup.LayoutParams save_params = btn_save.getLayoutParams();
        save_params.height = 120;
        btn_save.setLayoutParams(save_params);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s != null){



                    s = db.insertUpdate(num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            s.getImageName(),
                            deptno.get(dept.getSelectedItemPosition()));

                } else {
                    s = db.insertUpdate(num.getText().toString().trim(),
                            name.getText().toString().trim(),
                            phone.getText().toString().trim(),
                            deptno.get(dept.getSelectedItemPosition()));
                }
                db.CloseDB();
                ONSETNULL();
                if (s != null) {
                    ONSETUI(s);
                    notifyListItems();
                    setCurrentMarkListViewItemNewAddedStudent(s.getNum(),stuAdapter);
                    if (D) Log.i(TAG, "ac.getListViewItemPositionStudentAdapter(): " + ac.getListViewItemPositionStudentAdapter());ac.toString();
                    setCurrentMarkListViewItem();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")을 저장하였습니다.");
                } else {
                    ONSETNULL();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")은 저장되지 않았습니다.");
                }
            }
        });

        ViewGroup.LayoutParams clear_params = btn_clear.getLayoutParams();
        clear_params.height = 120;
        btn_clear.setLayoutParams(clear_params);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ONSETNULL();
                notifyListItems();
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
                    notifyListItems();
                }
            }
        });
        //STEP4
        makeDeptList();

        //STEP6
        //if문 수정
        if (# > 0) {
            setCurrentMarkListViewItem();
            if (D) Log.i(TAG, "ac.getListViewItemPositionStudentAdapter(): " + ac.getListViewItemPositionStudentAdapter());
            ONSETUI(#);
            message.setText(ss.size() + "명이 조회되었습니다.");
        //
        }

        try {

            listView.setOnItemClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //////////////////////////
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
        onSetImage(s.getImageName());
        dept.setSelection(deptno.indexOf(s.getDept()));
    }

    public void ONSETNULL(){
        num.setText("");
        name.setText("");
        phone.setText("");
        onSetImageNameNull();
    }

    //STEP 4
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

    //STEP5
    private void previewCapturedImage(String imageName) {

        try { //빈칸
            imgPreview.setVisibility(View.VISIBLE);
            videoPreview.setVisibility(View.GONE);

            Bitmap bitmap = AppCamera.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageName);

            imgPreview.setImageBitmap(bitmap);



        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void previewVideo(String imageName) {

        try { //빈칸
            imgPreview.setVisibility(View.GONE);
            videoPreview.setVisibility(View.VISIBLE);

            videoPreview.setVideoPath(imageName);
            videoPreview.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        if (requestCode == 1000) {

            if (resultCode == RESULT_OK) {//빈칸

                imageStoragePath = intent.getStringExtra("imageStoragePath");

                s = db.saveImageName(num.getText().toString().trim(), imageStoragePath);

                db.CloseDB();



                if (s != null) {
                    onSetImage(s.getImageName());
                    if (D) Log.i(TAG, "s.getImageName(): " + s.getImageName());
                    if (D) Log.i(TAG, "s.getHakbun(): " + s.getNum());
                    message.setText("요청한 학번("+num.getText().toString().trim()+")의 이미지를 저장하였습니다!");
                } else {
                    //setUINull();
                    message.setText("요청한 학번("+num.getText().toString().trim()+")의 이미지가 저장되지 않았습니다!");
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    private class PhotographyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) { //빈칸
            Intent i = new Intent(MainActivity.this, PhotographyActivity.class);
            args.putSerializable("Student", s);
            i.putExtras(args);

            startActivityForResult(i, 1000);
        }
    }

    private void onSetImageNameNull() {
        imgPreview.setVisibility(View.VISIBLE);
        videoPreview.setVisibility(View.GONE);
        imgPreview.setImageResource(R.drawable.ic_android);
    }

    public static String getExtension(String fileStr){
        String fileExtension =
                fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length());
        return TextUtils.isEmpty(fileExtension) ? null : fileExtension;
    }

    private void onSetImage(String imageName) {

        if (imageName != null) {

            if (getExtension(imageName).
                    equalsIgnoreCase("jpg")) {
                previewCapturedImage(imageName);
            } else if (getExtension(imageName).
                    equalsIgnoreCase("mp4")) {
                previewVideo(imageName);
            }
        }
        else {

            imgPreview.setVisibility(View.VISIBLE);
            videoPreview.setVisibility(View.GONE);
            imgPreview.setImageResource(R.drawable.ic_android);
        }
    }


    @Override
    public void onBackPressed() {

        Intent i = this.getIntent();
        args = new Bundle();
        if (s != null) {
            args.putSerializable("Student", s);
        }
        i.putExtras(args);

        this.setResult(RESULT_OK, i);
        if (D) Log.i(TAG, "args: " + args.toString());

        finish();
    }

    /// STEP 6
    public void setCurrentMarkListViewItem() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                stuAdapter.setClickMode(false);
                stuAdapter.setSellectedPosition(
                        ac.getListViewItemPositionStudentAdapter()
                );

                listView.setItemChecked(
                        ac.getListViewItemPositionStudentAdapter(), true);
                listView.smoothScrollToPosition(
                        ac.getListViewItemPositionStudentAdapter());

            }
        };

        handler.postDelayed(runnable, 500);
        stuAdapter.notifyDataSetChanged();
    }

    //수정
    public int notifyListItems() {

        stuAdapter.#;
        ss = db.#;
        if(ss != null) {

            for (#) {
                stuAdapter.#;
                if (D) Log.i(TAG, s.toString());
            }

            listView.postDelayed(new Runnable() {
                public void run() {
                    AppListViewHeight.setListViewHeightBasedOnChildren(listView);
                }
            }, 400);

            stuAdapter.#;
        }

        return #;
    }
    //
    public void setCurrentMarkListViewItemNewAddedStudent(String hakbun, StudentAdapter studentAdapter){

        int pos = 0;
        for (final Student s : studentAdapter.getStudents()) {
            if(s.getNum().equals(hakbun))
                break;
            else
                pos += 1;
        }
        ac.setListViewItemPositionStudentAdapter(pos);
        if (D) Log.i(TAG, "setListViewItemPositionStudentAdapter : " + pos);
    }

    private void ONSETUI(int position) {

        if(!stuAdapter.isEmpty()) {
            s = db.selectStudent(stuAdapter.getItem(position).getNum());
            ONSETUI(s);
        }
        else
            ONSETNULL();

    }


    public void onItemClick(AdapterView<?> adapterView, View view,
                            int position, long id) {
        //수정
        ac.setListViewItemPositionStudentAdapter(#);
        args.putSerializable("ac", ac);

        s = stuAdapter.getStudent(#);
        if(D) Log.i(TAG,"onItemClick(): "+s.toString());
        args.putSerializable("student", s);
        stuAdapter.#;

        stuAdapter.setClickMode(false);
        stuAdapter.setSellectedPosition(position);
        view.setBackgroundColor(0x9934B5E4);
        view.invalidate();

        s = db.selectStudent(#);
        db.CloseDB();
        ONSETUI(s);
        message.setText("리스트에서 학번( "+s.getNum()+" )을 선택합니다.");
        //
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (notifyListItems() > 0) {
            setCurrentMarkListViewItem();
        }
    }
}