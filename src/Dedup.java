import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Dedup {
	private File targetDirectory;
	private ArrayList<File> files;

	public Dedup(String path) throws NoSuchAlgorithmException, IOException {
		targetDirectory = new File(path);
		init();
	}
	
	public void init() throws NoSuchAlgorithmException, IOException{
		files = new ArrayList<File>();
		listAllFilesRecur(targetDirectory);
		ArrayList<FileWrapper> filesWrapped = wrapFiles(files);
		ArrayList<FileWrapper> duplicates = checkForDups(filesWrapped);
		removeDups(duplicates);
	}
	
	public void listAllFilesRecur(File directory) {
		File[] folder = directory.listFiles();
		for(int i = 0; i < folder.length; i++){
			if(folder[i].isDirectory()){
				listAllFilesRecur(folder[i]);
			}
			else{
				files.add(folder[i]);
			}
		}
	}
	
	public ArrayList<FileWrapper> wrapFiles(ArrayList<File> listOfFiles) throws NoSuchAlgorithmException, IOException{
		ArrayList<FileWrapper> filesWrapped = new ArrayList<FileWrapper>();
		for(int i = 0; i < listOfFiles.size(); i++){
			filesWrapped.add(new FileWrapper(files.get(i)));
			
		}
		return filesWrapped;
	}
	
	public ArrayList<FileWrapper> checkForDups(ArrayList<FileWrapper> filesToCheck) throws NoSuchAlgorithmException, IOException {
		ArrayList<FileWrapper> dups = new ArrayList<FileWrapper>();
		for(int i = 0; i < filesToCheck.size(); i++){
			for(int k = i+1; k < filesToCheck.size(); k++){
				if(filesToCheck.get(i).getHash().equals(filesToCheck.get(k).getHash())){
					dups.add(larger(filesToCheck.get(i), filesToCheck.get(k)));
				}
			}
		}
		if(dups.size() == 0){
			System.out.println("No duplicate files were found ¯\\_(ツ)_/¯");
			return dups;
		}
		else{
			System.out.println("A total of " + dups.size() + " duplicate files were found! \\_(•◡•)_/");
			System.out.println("They are:");
			for(FileWrapper f: dups){
				System.out.println(f.getFile().getName());
			}
		return dups;
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
	
	public void removeDups(ArrayList<FileWrapper> dups) {
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
