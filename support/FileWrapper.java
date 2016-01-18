import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class FileWrapper {
	private Path path;
	private String hash;
	private File file;
	
	public FileWrapper(File file) throws NoSuchAlgorithmException, IOException{
		this.file = file;
		String path = file.getCanonicalPath();
		this.path = Paths.get(path);
		hash = SHA1.sha1(file);
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
