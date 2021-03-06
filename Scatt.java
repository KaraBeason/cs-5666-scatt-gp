import java.io.File;
import java.util.Arrays;

/**
 * Scatt.java
 */

/**
 * Scratch Code Analysis Tool for Teachers (SCATT). 
 *  
 * @author Kara Beason
 * @author Cydney Caldwell
 * @author Michelle Melton
 * @version Spr 2017
 */
public class Scatt
{
    private static String dirName;

    /**
     * Method to get the name of selected directory.
     */
    public static String getDirName()
    {
        return dirName;
    }

    /**
     * Main method for Scatt class.
     *
     * @param args 
     */
    public static void main(String[] args)
    {
        System.out.print("Please enter the folder name: ");
        dirName = System.console().readLine();
        File directory = new File(dirName);
        Boolean isValid = FileUtils.readValidDirectory(directory);
        if (!isValid)
        {
            System.out.println("Invalid folder name.");
            return;
        }
        
        File[] sb2s = directory.listFiles();
        Arrays.sort(sb2s);
        Submission[] submissions = new Submission[sb2s.length];
        for (int i = 0; i < submissions.length; i++)
        {
            submissions[i] = new Submission(sb2s[i]);
        }
        
        Report report = new Report(submissions);
        report.makeReport();
    }
}
