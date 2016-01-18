import java.io.File;
import java.io.FileFilter;

//import javax.swing.filechooser.FileFilter;

public class ExtentionFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		// TODO Auto-generated method stub
		if(pathname.isDirectory()){
			return false;
		}
		return true;
	}

}
