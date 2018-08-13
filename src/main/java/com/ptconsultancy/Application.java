package com.ptconsultancy;

import com.ptconsultancy.admin.BuildVersion;
import com.ptconsultancy.entities.AddressEntity;
import com.ptconsultancy.repositories.AddressEntityRepository;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.thymeleaf.util.StringUtils;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Autowired
    private AddressEntityRepository addressRepository;

    public static void main(String[] args) throws Throwable {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        populateDatabase();
        if (strings == null) {
            prepareRunnableBatchFile();
        }
        outputMessage();
    }

    private void outputMessage() {
        String serverPort = env.getProperty("server.port");
        System.out.println("************************************************************************");
        System.out.println("* AddressApi, Version: " + BuildVersion.getBuildVersion());
        System.out.println("************************************************************************");
        System.out.println("* AddressApi is now running on:- localhost:" + serverPort);
        System.out.println("************************************************************************");
    }

    private void prepareRunnableBatchFile() throws IOException {

        String projectFilename = "";
        File file = new File("build\\libs");
        projectFilename = findJar(projectFilename, file);

        file = new File("run.bat");
        if (file.exists()) {
            file.delete();
        }

        if (!StringUtils.isEmpty(projectFilename) && projectFilename.contains(".jar")) {
            RandomAccessFile fout = new RandomAccessFile("run.bat", "rw");

            fout.writeBytes("cd build\\libs\n\n");
            fout.writeBytes("java -jar " + projectFilename + " no-file");

            fout.close();
        } else {
            file = new File(".");
            projectFilename = findJar(projectFilename, file);

            file = new File("run.bat");
            if (file.exists()) {
                file.delete();
            }

            if (!StringUtils.isEmpty(projectFilename) && projectFilename.contains(".jar")) {
                RandomAccessFile fout = new RandomAccessFile("run.bat", "rw");

                fout.writeBytes("cd build\\libs\n\n");
                fout.writeBytes("java -jar " + projectFilename + " no-file");

                fout.close();
            } else {
                System.out.println("************************************************************************");
                System.out.println("* There does not seem to be any projectFilename set.                   *");
                System.out.println("* Try running the gradlew clean build first, and then create run.bat!  *");
                System.out.println("************************************************************************");
            }
        }
    }

    private String findJar(String projectFilename, File file) {
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(".jar")) {
                    projectFilename = files[i].getName();
                }
            }
        }
        return projectFilename;
    }

    private void populateDatabase() {

        if (addressRepository.findByUserId((long) 11111).size() == 0) {
            String prop;
            int i = 1;
            do {
                String address = "address" + String.valueOf(i++);
                prop = env.getProperty(address);
                if (!StringUtils.isEmpty(prop)) {
                    String[] addressDetails = prop.split(", ");
                    addressRepository.save(new AddressEntity(Long.parseLong(addressDetails[0]), addressDetails[1], addressDetails[2], addressDetails[3],
                        addressDetails[4], addressDetails[5], addressDetails[6], addressDetails[7], addressDetails[8]));
                }
            } while (!StringUtils.isEmpty(prop));
        }
    }
}

