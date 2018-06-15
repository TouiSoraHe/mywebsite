package cn.zzy.mywebsite.Tools;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Util {

    public static String GetJarParentPath()
    {
        String rootPath = null;
        String temp = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        temp = temp.replace("file:/","");
        while (temp != null){
            File tempFile = new File(temp);
            String pathName = tempFile.getName();
            if(pathName.indexOf(".jar")!=-1 && pathName.indexOf(".jar!") == pathName.length()-5){
                temp = tempFile.getParent();
                break;
            }
            temp = tempFile.getParent();
        }
        rootPath = temp;
        return rootPath;
    }

    public static String EncodeURIComponent(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String GetErrorMessage(Exception e)
    {
        String message = "";
        if(e.getCause()!=null && e.getCause().getMessage()!=null && !"".equals(e.getCause().getMessage()))
        {
            message = e.getCause().getMessage();
        }
        else if(e.getMessage()!=null && !"".equals(e.getMessage()))
        {
            message = e.getMessage();
        }
        return  e.getClass().getName() +":"+ message;
    }

    /**
     * 获取给定的路径的文件或文件夹名,不包括后缀名
     * @param path 给定的路径
     * @return 文件/文件夹名,不包过后缀名
     */
    public static String GetFileNameWithoutExtension(String path) {
        if(path==null || "".equals(path)){
            return path;
        }
        File file = new File(path);
        String fileName = file.getName();
        int pointIndex = fileName.lastIndexOf(".");
        if (pointIndex != -1) {
            return fileName.substring(0,pointIndex);
        } else {
            return path;
        }
    }

    public static ResponseEntity DownloadFile(String filePath) throws IOException {
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition",
                "attachment;"
                        +" filename=\"" + EncodeURIComponent(file.getFilename()) + "\";"
                        +" filename*=utf-8''"+EncodeURIComponent(file.getFilename())
        );
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("charset", "utf-8");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    public static String GetDomainWithPort(HttpServletRequest request) {
        return  request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort();
    }
}
