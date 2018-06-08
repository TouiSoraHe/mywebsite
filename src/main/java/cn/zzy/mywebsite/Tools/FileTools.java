package cn.zzy.mywebsite.Tools;

import org.springframework.util.ClassUtils;

import java.io.File;

public class FileTools {

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
}
