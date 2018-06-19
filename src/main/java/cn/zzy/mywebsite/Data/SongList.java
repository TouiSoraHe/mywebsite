package cn.zzy.mywebsite.Data;

import cn.zzy.mywebsite.Exception.AssetNotFoundException;
import cn.zzy.mywebsite.Tools.GlobalVariables;
import com.alibaba.fastjson.JSON;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SongList {
    private static String database = GlobalVariables.AudioRootPath;
    public List<Song> source = new ArrayList<>();

    public SongList() {
    }

    public static SongList GetSongList(String songListName) throws InvalidDataException, IOException, UnsupportedTagException {
        File songListFile = new File(database + "/" + songListName);
        SongList list = null;
        if (songListFile.exists()) {
            File songListJsonFile = new File(songListFile.getAbsolutePath() + "/source.json");
            if (songListJsonFile.exists()) {
                list = ReadFromFile(songListJsonFile);
                //判断MP3文件的数量与播放列表是否一致
                int count = 0;
                for (File songFile : songListFile.listFiles()) {
                    if (songFile.isFile() && songFile.getName().lastIndexOf(".mp3") == songFile.getName().length() - 4) {
                        count++;
                    }
                }
                //如果不一致则重新生成
                if (count != list.source.size()) {
                    list = CreateSongList(songListFile);
                    WriteToFile(list, songListJsonFile);
                }
            } else {
                list = CreateSongList(songListFile);
                WriteToFile(list, songListJsonFile);
            }
        } else {
            throw new AssetNotFoundException("未找到歌单:" + songListName);
        }
        return list;
    }

    private static SongList ReadFromFile(File file) throws IOException {
        SongList list = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            list = JSON.parseObject(fileInputStream, Charset.forName("utf8"), SongList.class);
        }
        return list;
    }

    private static void WriteToFile(SongList list, File file) throws IOException {
        String json = JSON.toJSONString(list);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        }
    }

    private static SongList CreateSongList(File songListFile) throws InvalidDataException, IOException, UnsupportedTagException {
        SongList list = new SongList();
        for (File songFile : songListFile.listFiles()) {
            if (songFile.isFile() && songFile.getName().lastIndexOf(".mp3") == songFile.getName().length() - 4) {
                String cover = "/Audio/" + songListFile.getName() + "/" + songFile.getName() + "/cover";
                String src = "/Audio/" + songListFile.getName() + "/" + songFile.getName();
                Song song = new Song(songFile, src, cover);
                list.source.add(song);
            }
        }
        list.SortSource();
        return list;
    }

    public void SortSource() {
        source.sort(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.name.compareTo(o2.name);
            }
        });
    }
}
