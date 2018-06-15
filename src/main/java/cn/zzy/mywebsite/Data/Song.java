package cn.zzy.mywebsite.Data;

import cn.zzy.mywebsite.Tools.Util;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Song{
    public String name;
    public String author;
    public String src;
    public String cover;

    public Song() {
        name = "未知";
        author = "未知";
        cover = "";
        src = "";
    }

    public Song(File file,String src,String cover) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3File = new Mp3File(file.getAbsolutePath());
        ID3v2 id3v2 = mp3File.getId3v2Tag();
        author = id3v2.getArtist();
        name = id3v2.getTitle();
        File coverFile = new File(file.getAbsolutePath()+".cover");
        if(!coverFile.exists()){
            try(FileOutputStream fileOutputStream = new FileOutputStream(coverFile)) {
                byte[] img = id3v2.getAlbumImage();
                fileOutputStream.write(img==null?new byte[0]:img);
            }
        }
        if(author == null || "".equals(author)){
            author = "未知";
        }
        if(name == null || "".equals(name)){
            name = Util.GetFileNameWithoutExtension(file.getName());
        }
        this.src =src;
        this.cover = cover;
    }
}
