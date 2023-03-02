package com.maniargh.utils.backup.service;

import com.maniargh.exception.InvalidArgumentException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class FileSyncService {
    public static final String FS_SEPARATOR = System.getProperty("file.separator");
    private static final Logger LOG = Logger.getLogger("file-sync");

    /**
     * Copies all files contained inside a directory, creating direct subdirectory if needed
     *
     * @param sourcePath         source directory
     * @param destinationPath    destination directory
     * @param createSubdirectory if true, creates also direct subdirectories
     * @throws InvalidArgumentException
     */
    public void syncDirectory(String sourcePath, String destinationPath, boolean createSubdirectory) throws InvalidArgumentException, IOException {
        if (StringUtils.isBlank(sourcePath)) {
            String errorMessage = "Source path '" + sourcePath + "' is not valid";
            LOG.warning(errorMessage);
            throw new InvalidArgumentException(errorMessage);
        }

        if (StringUtils.isBlank(destinationPath)) {
            String errorMessage = "Destination path '" + destinationPath + "' is not valid";
            LOG.warning(errorMessage);
            throw new InvalidArgumentException(errorMessage);
        }

        // cycle files inside source path
        File sourceDirectory = new File(sourcePath);
        String[] sourceFiles = sourceDirectory.list();
        LOG.info("Analyzing folder '" + sourcePath + "'...");
        if (sourceFiles == null || sourceFiles.length == 0) {
            LOG.info("Folder '" + sourcePath + "' is empty");
            return;
        } else {
            LOG.info("Folder '" + sourcePath + "' contains " + sourceFiles.length + " files");

            File destinationDirectory = new File(destinationPath);
            String[] destinationFiles = destinationDirectory.list();
            if (destinationDirectory == null) {
                destinationFiles = new String[]{};
            }

            // compare files and directories
            for (String sourceFilePath : sourceFiles) {
                // check if file is directory
                String absoluteSourceFilePath = sourcePath + FS_SEPARATOR + sourceFilePath;
                File sourceFile = new File(absoluteSourceFilePath);
                String absoluteDestinationFilePath = destinationPath + FS_SEPARATOR + sourceFilePath;

                if (sourceFile.isDirectory()) {
                    // create destination directory if needed and
                    // recursively call method
                    if (createSubdirectory)
                        Files.createDirectories(Paths.get(absoluteDestinationFilePath));

                    syncDirectory(absoluteSourceFilePath, absoluteDestinationFilePath, createSubdirectory);
                } else {
                    // copy files if not present
                    if (!Arrays.stream(destinationFiles).anyMatch(sourceFilePath::equals)) {
                        LOG.info("File '" + sourceFilePath + "' not present, copying...");

                        FileUtils.copyFile(sourceFile, new File(absoluteDestinationFilePath));
                    } else {
                        LOG.info("File '" + sourceFilePath + "' already synced");
                    }
                }
            }
        }
    }

}
