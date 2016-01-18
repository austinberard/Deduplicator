import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Dedup {
	private File folder;
	private ArrayList<File> files;
	private ArrayList<FileWrapper> filesWrapped;
	private ArrayList<FileWrapper> dups;

	public Dedup(String path) throws NoSuchAlgorithmException, IOException {
		folder = new File(path);
		FileFilter filter = new ExtentionFilter();
		files = new ArrayList<File>(Arrays.asList(folder.listFiles(filter)));
		dups = new ArrayList<FileWrapper>();
		for(File f: files){
			if(f.isDirectory()){
				files.remove(f);
			}
		}
		filesWrapped = new ArrayList<FileWrapper>();
		init();
	}
	
	public void init() throws NoSuchAlgorithmException, IOException{
		wrapFiles();
		checkForDups();
		removeDups();
	}
	
	public void wrapFiles() throws NoSuchAlgorithmException, IOException{
		for(int i = 0; i < files.size(); i++){
			filesWrapped.add(new FileWrapper(files.get(i)));
			
		}
	}
	
	public void checkForDups() throws NoSuchAlgorithmException, IOException {
		for(int i = 0; i < filesWrapped.size(); i++){
			for(int k = i+1; k < filesWrapped.size(); k++){
				if(filesWrapped.get(i).getHash().equals(filesWrapped.get(k).getHash())){
					dups.add(larger(filesWrapped.get(i), filesWrapped.get(k)));
				}
			}
		}
		if(dups.size() == 0){
			System.out.println("No duplicate files were found ¯\\_(ツ)_/¯");
			return;
		}
		else{
			System.out.println("A total of " + dups.size() + " duplicate files were found! \\_(•◡•)_/");
			System.out.println("They are:");
			for(FileWrapper f: dups){
				System.out.println(f.getFile().getName());
			}
		}
	}
	
	private FileWrapper larger(FileWrapper x, FileWrapper y){
		if(x.getFile().getName().length() > y.getFile().getName().length()){
			return x;
		}
		else{
			return y;
		}
	}
	
	public void removeDups() {
		for(FileWrapper f: dups){
			System.out.println("Removing: " + f.getFile().getName());
			f.getFile().delete();
		}
	}



	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		try {
			System.out.println("Checking " + args[0] + " for any duplicates");
			Dedup dp = new Dedup(args[0]);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("You must include a file path to check");
			e.printStackTrace();
		}
		
	}
}
