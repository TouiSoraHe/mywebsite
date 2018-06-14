package cn.zzy.mywebsite.Tools;

import org.springframework.util.ClassUtils;

import java.io.File;
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
        return  message;
    }
}
