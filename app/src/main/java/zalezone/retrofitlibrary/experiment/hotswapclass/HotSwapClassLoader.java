package zalezone.retrofitlibrary.experiment.hotswapclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

/**
 * Created by zale on 2017/2/27.
 */

public class HotSwapClassLoader extends ClassLoader{

    private String baseDir;//该类加载器直接加载的类文件的基目录
    private HashSet dynclanzns;//需要由该类加载器直接加载的类名

    public HotSwapClassLoader(ClassLoader parentLoader, String baseDir, String[] clanzs) {
        super(null);//指定父类为null,取消双亲委派
        this.baseDir = baseDir;
        this.dynclanzns = new HashSet();
        loadClassByMe(clanzs);
    }

    private void loadClassByMe(String[] clanzs) {
        for (int i=0;i<clanzs.length;i++){
            loadDirectly(clanzs[i]);
        }
    }

    private void loadDirectly(String name) {
        Class cls = null;
        StringBuffer sb = new StringBuffer(baseDir);
        String className = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator+className);
        File classF = new File(sb.toString());
        try {
            cls = instantiateClass(name,new FileInputStream(classF),classF.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Class instantiateClass(String name, InputStream fin,long len){
        byte[] raw = new byte[(int) len];
        try {
            fin.read(raw);
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name,raw,0,raw.length);
    }

    protected Class loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class cls = null;
        cls = findLoadedClass(name);
        if (!this.dynclanzns.contains(name)&&cls == null){
            cls = getSystemClassLoader().loadClass(name);
        }
        if (cls ==null){
            throw new ClassNotFoundException(name);
        }
        if (resolve){
            resolveClass(cls);
        }
        return cls;
    }
}
