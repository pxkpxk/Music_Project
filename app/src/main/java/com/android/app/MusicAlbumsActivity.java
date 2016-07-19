package com.android.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.view.View;
import android.util.Log;
import android.widget.ListView;

import com.allenliu.sidebar.SideBar;
import com.dlighttech.music.adapter.ContentAdapter;
import com.dlighttech.music.model.ContentItem;
import com.dlighttech.music.model.MusicInfo;

import java.util.ArrayList;

/**
 * Created by pengxinkai001 on 2016/6/24.
 */
public class MusicAlbumsActivity extends BaseActivity implements ContentAdapter.OnConvertViewClicked, ContentAdapter.OnOperateClicked {

    private ListView mListview;
    private ArrayList<ContentItem> items = new ArrayList<ContentItem>();
    private ContentAdapter mAdapter;
    private SideBar sb_navigation_bar;
    private ArrayList<MusicInfo> arrayList;

    private Cursor cursor;

    private MusicUtils.ServiceToken token;


    @Override
    public void onInitView() {
        setContentView(R.layout.music_albums_layout);

    }

    @Override
    public void onCreateView() {
        super.setTitleText("Album");
        mListview = (ListView) findViewById(R.id.lv_music_detail);
        mListview.setAdapter(new ContentAdapter(this, items, false));
        sb_navigation_bar = (SideBar) findViewById(R.id.navigation_bar);
    }


    @Override
    public void onCreateData() {

        // 获取所有歌曲的专辑

//        long[] songIds = MusicUtils.getAllSongs(this);
//        StringBuilder selection = new StringBuilder();
//        String[] selectionArgs = new String[songIds.length];


//        MusicUtils.getSongListForAlbum()

//        for (int j = 0; j < songIds.length; j++) {
//            // 获取当前专辑下的所有歌曲
//            selection.append(MediaStore.Audio.Media._ID + "=?");
//            selection.append(j == songIds.length - 1 ? "" : " or ");
//
//            selectionArgs[j] = String.valueOf(songIds[j]);
//        }

        arrayList = MusicUtils.getMusicInfo(this, false);
        String albumName = "";
        for (int i = 0; i < arrayList.size(); i++) {

            MusicInfo info = arrayList.get(i);
            Log.d("TAG", info.toString());

            Bitmap bitmap = MusicUtils.getArtwork(this, info.getMusicId(), info.getAlbumId(), true);
            long[] songsIds = MusicUtils.getSongListForAlbum(this, info.getAlbumId());

            if (info.getMusicAlbumsName().equals(albumName)) {
                continue;
            }
            ContentItem item = new ContentItem(bitmap, R.drawable.c_right
                    , info.getMusicAlbumsName(), songsIds.length + "首");
            items.add(item);

            albumName = info.getMusicAlbumsName();

        }


    }


    @Override
    public void onOperateClicked(int position, View v) {

        Intent intent = new Intent(MusicAlbumsActivity.this, MediaPlaybackActivity.class);
        startActivity(intent);


    }

    @Override
    public void onSearchTextChanged(String text) {

    }

    @Override
    public void onSearchSubmit(String text) {

    }


    @Override
    public void onConvertViewClicked(int position) {

        playmusic(position);


    }
         //播放音乐
    private void playmusic(int position) {

        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        StringBuilder selector = new StringBuilder();

        String[] selectorArgs = new String[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {

            selector.append(MediaStore.Audio.Media._ID + "=?");
            selector.append(i == arrayList.size() - 1 ? "" : "or");

           selectorArgs[i]= String.valueOf(arrayList.get(i).getMusicId());
        }
            super.playCursor(selector.toString(),selectorArgs,false,position);

    }
}
