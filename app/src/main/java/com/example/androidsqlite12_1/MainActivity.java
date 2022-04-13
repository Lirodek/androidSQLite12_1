package com.example.androidsqlite12_1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private myDBHelper dbHelper;
    TextView tvNames, tvTitles;
    EditText edtName, edtTitle;
    Button btnInset, btnInit, btnSelect;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new myDBHelper(this);


        edtName = (EditText) findViewById(R.id.edtName);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        tvNames = (TextView) findViewById(R.id.tvNames);
        tvTitles = (TextView) findViewById(R.id.tvTitle);

        btnInset = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnView);
        btnInit = (Button) findViewById(R.id.btnReset);

        btnInit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sqlDB = dbHelper.getWritableDatabase();
                //데이터베이스 파일을 생성하기 위하여 쓰기 모드 설정
                dbHelper.onUpgrade(sqlDB, 1, 2);
                // 기존 테이블을 삭제하고 새로운테이블 생성
                sqlDB.close();
                // 쓰기모드로 연 데이터베이스를 닫아준다.
            }
        });
        btnInset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(edtTitle.getText().toString());
                sqlDB = dbHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO charTable VALUES('"
                        + edtTitle.getText().toString() + "' , "
                        + edtName.getText().toString() + ");");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "데이터가 입력됨",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = dbHelper.getReadableDatabase();
                // 읽기모드
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM charTable;",
                        null);
                // cursor = 쿼리 저장 결과 rawQuery로 쿼리사용 SQL의 SELECT값 조회
                String strTitles = "애니제목" + "\r\n" + " ----------" + "\r\n";
                String strNames = "주인공"+"\r\n"+"----------" + "\r\n";
                while(cursor.moveToNext()){
                    // 첫번째 필드 값 제목
                    strTitles += cursor.getString(0) + "\r\n";
                    strNames += cursor.getString(1) + "\r\n";
                    // 두번째 필드 값 (이름)
                }
                tvTitles.setText(strTitles);
                tvNames.setText(strNames);

                cursor.close();
                sqlDB.close();
                // 사용 후 커서와 DB를 닫는다
            }
        });
    }
}