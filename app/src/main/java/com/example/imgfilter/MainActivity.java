package com.example.imgfilter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private Bitmap originalBitmap;
    private ImageView originalImageView;
    private RecyclerView filtersRecyclerView;
    private FilterAdapter filterAdapter;
    private List<Bitmap> filteredBitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalImageView = findViewById(R.id.originalImageView);
        filtersRecyclerView = findViewById(R.id.filtersRecyclerView);
        Button doneButton = findViewById(R.id.doneButton);

        filtersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        doneButton.setOnClickListener(v -> saveFilteredImages());

        // Start image picker
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                originalImageView.setImageBitmap(originalBitmap);

                filterAdapter = new FilterAdapter(originalBitmap, filteredBitmap -> originalImageView.setImageBitmap(filteredBitmap));
                filtersRecyclerView.setAdapter(filterAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFilteredImages() {
        if (originalImageView.getDrawable() != null) {
            filteredBitmaps.add(((android.graphics.drawable.BitmapDrawable) originalImageView.getDrawable()).getBitmap());
            Toast.makeText(this, "Filtered images saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
