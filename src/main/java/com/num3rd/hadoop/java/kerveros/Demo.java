package com.num3rd.hadoop.java.kerveros;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * Created by pipe on 1/4/17.
 */
public class Demo {
    public static void main(String[] args) {
        String keyUser = "";
        String keyPath = "";
        String filePath = "";
        String defaultFs = "";
        if (args.length >= 4) {
            keyUser = args[0];
            keyPath = args[1];
            filePath = args[2];
            defaultFs = args[3];
        } else {
            System.out.println("args >= 4");
            System.out.println("Usage: keyUser,keyPath,filePath,defaultFs");
            return;
        }
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", defaultFs);
        configuration.set("hadoop.security.authentication", "Kerberos");

        // If use maven-assembly plugin, detail see DemoReadMe
        configuration.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        configuration.set("fs.file.impl", LocalFileSystem.class.getName());

        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab(keyUser, keyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileSystem fileSystem = FileSystem.get(configuration);
            Path path = new Path(filePath);
            boolean directory = fileSystem.isDirectory(path);
            System.out.println("===============================");
            System.out.println(directory);
            System.out.println("===============================");

            RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(path, false);
            while (locatedFileStatusRemoteIterator.hasNext()) {
                System.out.println(locatedFileStatusRemoteIterator.next().getPath().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
