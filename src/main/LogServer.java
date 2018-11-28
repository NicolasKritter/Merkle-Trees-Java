package main;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Scanner;

public class LogServer {
	String filePath;
	MerkleTreesNode tree;
	public LogServer(String filePath) {
		this.filePath =filePath;
		try {
			readLogFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void readLogFile(String path) throws IOException {
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path))) {
			//lecture via scanner
			String word; 
			while(scan.hasNextLine()) {
				 word = scan.nextLine();
			}
		}
	}
	public void appendEvent(String log) {
		
	}
	public byte[] getRootHash() {
		if (tree == null) {
			return null;
		}
		return tree.getHash();
	}
	public byte[] genPath(int i) {
		
	}
	public byte[] genProof(int i) {
		
	}
	
	public String getFilePath() {
		return filePath;
	}
	
}
