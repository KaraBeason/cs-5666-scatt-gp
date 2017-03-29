import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * FileUtils.java
 *
 * @author Michelle Melton
 * @author Kara Beason
 * @author Cydney Caldwell
 */

/**
 * Class for file utilities.
 *
 * @author Michelle Melton
 * @author Kara Beason
 * @author Cydney Caldwell
 * @version Mar 2017
 */
public class FileUtils 
{
    /**
     * Copy file to zips directory to preserve original.
     * Convert file to .zip.
     *
     * @param zipsDir 
     * @param file 
     */
    public static void convertToZip(File zipsDir, File file)
    {
        if (!zipsDir.exists())
        {
            zipsDir.mkdir();
        }
        String zipName = getBaseName(file) + ".zip";
        File zip = new File(zipsDir, zipName);
        if (!zip.exists())
        {
            try
            {
                Files.copy(file.toPath(), zip.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Make zip named directory in unzips directory.
     * Copy zip into .zip named directory.
     * Unzip files.
     * Delete copied zip.
     *
     * @param zipsDir 
     * @param unzipsDir 
     * @param sb2 
     */
    public static void unZip(File zipsDir, File unzipsDir, File sb2)
    {
        if (!unzipsDir.exists())
        {
            unzipsDir.mkdir();
        }
        String zipDirName = getBaseName(sb2);
        String zipName = zipDirName + ".zip"; 
        File unzipDir = new File(unzipsDir, zipDirName);
        unzipDir.mkdir();

        File zip = new File(zipsDir, zipName);
        File copy = new File(unzipDir, zipName);
        try
        {
            Files.copy(zip.toPath(), copy.toPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        unZipUtil(copy, unzipDir);
        
        copy.delete();
    }

    /**
     * Unzip utility.
     * Adapted from Pankaj
     * http://www.journaldev.com/960/java-unzip-file-example
     *
     * @param zip 
     * @param destDir 
     */
    public static void unZipUtil(File zip, File destDir)
    {
        FileInputStream fis;

        // Buffer for read and write data to file.
        byte[] buffer = new byte[1024];

        try
        {
            fis = new FileInputStream(zip);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null)
            {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get filename without extension.
     *
     * @param file 
     * @return base filename
     */
    public static String getBaseName(File file)
    {
        String fileName = file.getName();
        int len = fileName.length();
        String baseName = fileName.substring(0, len - 4);
        return baseName;
    }
}