package com.lge.mams.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
	 // A method to Compress a directory.
    public void zipDirectory(File inputDir, File outputZipFile) {
        // Create parent directory for the output file.
        outputZipFile.getParentFile().mkdirs();

        String inputDirPath = inputDir.getAbsolutePath();
        byte[] buffer = new byte[1024];

        FileOutputStream fileOs = null;
        ZipOutputStream zipOs = null;
        try {

            List<File> allFiles = this.listChildFiles(inputDir);

            // Create ZipOutputStream object to write to the zip file
            fileOs = new FileOutputStream(outputZipFile);
            //
            zipOs = new ZipOutputStream(fileOs);
            for (File file : allFiles) {
                String filePath = file.getAbsolutePath();

                System.out.println("Zipping " + filePath);
                // entryName
                String entryName = filePath.substring(inputDirPath.length() + 1);

                ZipEntry ze = new ZipEntry(entryName);
                // Put new entry into zip file.
                zipOs.putNextEntry(ze);
                // Read the file and write to ZipOutputStream.
                FileInputStream fileIs = new FileInputStream(filePath);

                int len;
                while ((len = fileIs.read(buffer)) > 0) {
                    zipOs.write(buffer, 0, len);
                }
                fileIs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuite(zipOs);
            closeQuite(fileOs);
        }

    }

    private void closeQuite(OutputStream out) {
        try {
            out.close();
        } catch (Exception e) {
        }
    }

    // This method returns the list of files,
    // including the children, grandchildren files of the input folder.
    private List<File> listChildFiles(File dir) throws IOException {
        List<File> allFiles = new ArrayList<File>();

        File[] childFiles = dir.listFiles();
        for (File file : childFiles) {
            if (file.isFile()) {
                allFiles.add(file);
            } else {
                List<File> files = this.listChildFiles(file);
                allFiles.addAll(files);
            }
        }
        return allFiles;
    }
}
