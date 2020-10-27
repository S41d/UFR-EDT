package reader;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class Files {
    public static final File PATH = new File(
            FileSystemView.getFileSystemView().getDefaultDirectory() +
            File.separator + ".emploi-du-temps" +
            File.separator + "downloaded");

    public void createDir() {
        System.out.println("Checking if :PATH: exists");
        if (!PATH.isDirectory()) {
            System.out.println("Creating Directory");
            PATH.mkdirs();
        }
        System.out.println("createDir: Done");
    }

    public void check() {
        createDir();
        System.out.println("Check: Finished");
    }
}
