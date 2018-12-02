package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LogServer {
	String filePath;
	MerkleTreesNode tree;
	int index = 0;
	public LogServer(String filePath) {
		this.filePath =filePath;
		try {
			List<MerkleTreesNode> liste  = readLogFile(filePath);
			tree = MerkleTreesUtils.buildTree(liste);
			System.out.println(tree);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}


	private List<MerkleTreesNode> readLogFile(String path) throws IOException {

		List<MerkleTreesNode> liste = new LinkedList<MerkleTreesNode>();
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path))) {
			//lecture via scanner
			String word; 
			while(scan.hasNextLine()) {
				 word = scan.nextLine();
				 liste.add(new MerkleTreesNode(word, index));
				 index+=1;
			}
		}
		return liste;
	}

	

	public void appendEvent(String log) throws IOException {
		try(FileWriter fw = new FileWriter(filePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			    out.println(log);
			} catch (IOException e) {  
			}
	}
	public void appendEvent(List<String> logs) throws IOException {
		List<MerkleTreesNode> liste = new LinkedList<MerkleTreesNode>();
		for(String s: logs) {
			 liste.add(new MerkleTreesNode(s, index));
			 index+=1;
		}
		tree = MerkleTreesUtils.addNodestoTree(tree, liste);
	}
	
	public byte[] getRootHash() {
		if (tree == null) {
			return null;
		}
		return tree.getHash();
	}
	
	public List<byte[]> genPath(int i) {
		return MerkleTreesUtils.genPath(i, tree);
	}
	public List<byte[]> genProof(int i) {
		return MerkleTreesUtils.genProof(i, tree);
	}
	
	public String getFilePath() {
		return filePath;
	}
	
}
