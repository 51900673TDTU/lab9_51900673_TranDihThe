package com.example.bai2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaFileActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MediaFileAdaptor mAdapter;
    List<MediaFile> mMediaList = new ArrayList<>();
    public static String MP3_PATTERN = ".mp3";
    public static String MP3_PATH = "/mnt/sdcard/mymusic/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanDirectory(new File(MP3_PATH));

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new MediaFileAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mMediaList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));

    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(MP3_PATTERN)) {
            MediaFile mediaFile = new MediaFile();
            mediaFile.setName(song.getName().substring(0, (song.getName().length() - 4)));
            mediaFile.setPath(song.getPath());

            // Adding each song to MediaList
            mMediaList.add(mediaFile);
        }
    }
}