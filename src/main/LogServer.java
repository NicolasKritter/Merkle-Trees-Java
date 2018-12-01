package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LogServer {
	String filePath;
	MerkleTreesNode tree;
	List<MerkleTreesNode> liste = new LinkedList<MerkleTreesNode>();
	int depth = 0;
	public LogServer(String filePath) {
		this.filePath =filePath;
		try {
			readLogFile(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tree = buildTree(liste);
		System.out.println(tree);
	}


	private void readLogFile(String path) throws IOException {
		int index = 0;
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path))) {
			//lecture via scanner
			String word; 
			while(scan.hasNextLine()) {
				 word = scan.nextLine();
				 liste.add(new MerkleTreesNode(word, index));
				 index+=1;
			}
		}
	}
	private MerkleTreesNode buildTree(List<MerkleTreesNode> list) {
		if (list.isEmpty()) {return new MerkleTreesNode("", 0);}
		List<MerkleTreesNode> layer = MerkleTreesNode.addTreeLayer(list);
		depth=1;
		while(layer.size()!=1) {
			System.out.println(layer.size());
			layer=MerkleTreesNode.addTreeLayer(layer);
			depth+=1;
		}
		return layer.get(0);
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
		try(FileWriter fw = new FileWriter(filePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			for(String s: logs)
			    out.println(s);
			} catch (IOException e) {  
			}
	}
	
	public byte[] getRootHash() {
		if (tree == null) {
			return null;
		}
		return tree.getHash();
	}
	
	public List<byte[]> genPath(int i) {
		return MerkleTreesNode.navigate(i, tree);
	}
	public byte[] genProof(int i) {
		return null;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
}
