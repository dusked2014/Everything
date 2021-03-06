package com.github.v1.config;

import lombok.Getter;
import lombok.ToString;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 处理的目录
 */
@Getter
@ToString
public class Handlerpath {

    /**
     * 包含的目录
     */
    private Set<String> includePath = new HashSet<>();

    /**
     * 排除的目录
     */
    private Set<String> excludePath = new HashSet<>();

    private Handlerpath(){}

    /**
     * 包含的目录路径
     * @param path 路径
     */
    public void addIncludePath(String path){
        this.includePath.add(path);
    }

    /**
     * 排除的目录路径
     * @param path 路径
     */
    public void addExcludePath(String path){
        this.excludePath.add(path);
    }

    /**
     * 处理构建索引时的文件路径
     * @return
     */
    public static Handlerpath getDefaultHandlerPath(){
        Handlerpath handlerpath = new Handlerpath();
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();

        //默认要包含的目录，即构建索引是要处理的路径
        paths.forEach(path ->{
            handlerpath.addIncludePath(path.toString());
        });

        //默认要排除的目录，即构建索引时不需要处理的路径
        String systemName = System.getProperty("os.name");

        // windows 操作系统
        if (systemName.contains("Windows")) {
            handlerpath.addExcludePath("C:\\Windows");
            handlerpath.addExcludePath("C:\\Program Files");
            handlerpath.addExcludePath("C:\\program Files(x86)");
            handlerpath.addExcludePath("C:\\ProgramData");

        } else { // Linux操作系统
            handlerpath.addExcludePath("/root");
            handlerpath.addExcludePath("/temp");

        }
        return handlerpath;
    }

//测试
//    public static void main(String[] args) {
//        System.out.println(Handlerpath.getDefaultHandlerPath());
//    }

}
