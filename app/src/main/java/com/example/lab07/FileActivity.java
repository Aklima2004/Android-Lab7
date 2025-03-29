package com.example.lab07;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_WRITE_PERM = 401;
    private Button btnWriteFile, btnReadFile;
    private EditText edtInputText;
    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        btnWriteFile = findViewById(R.id.btnWriteFile);
        btnReadFile = findViewById(R.id.btnReadFile);
        edtInputText = findViewById(R.id.edtInputText);
        tvData = findViewById(R.id.tvData);

        requestNeededPermission();

        btnWriteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWriteFile.getText().toString().equals("Write to File")) {
                    edtInputText.setVisibility(View.VISIBLE);
                    btnWriteFile.setText("Save to File");
                } else {
                    String inputData = edtInputText.getText().toString();
                    writeFile(inputData);
                    edtInputText.setVisibility(View.GONE);
                    btnWriteFile.setText("Write to File");
                    Toast.makeText(FileActivity.this, "The data is written to a file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileContent = readFile();
                if (fileContent.isEmpty()) {
                    tvData.setText("The file is empty");
                } else {
                    tvData.setText(fileContent);
                }
            }
        });
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(FileActivity.this, "I need it for File", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_PERM);
        } else {
            Toast.makeText(this, "Already have permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeFile(String data) {
        try {
            // Запись в файл
            FileOutputStream fos = openFileOutput("file_example.txt", MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        String result = "";
        try {
            // Чтение из файла
            FileInputStream fis = openFileInput("file_example.txt");
            int character;
            while ((character = fis.read()) != -1) {
                result += (char) character;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
