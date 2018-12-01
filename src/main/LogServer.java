package main;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
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
			List<String> lines = new ArrayList<>();
			List<MerkleTreesNode> leaves = new ArrayList<>();
			String line; 
			while(scan.hasNextLine()) {
				 line = scan.nextLine();
				 lines.add(line);
			}
			int i=0;
			for (String l : lines) {
				MerkleTreesNode N = new MerkleTreesNode(l,i);
				leaves.add(N);
				i++;
			}
			List<MerkleTreesNode> Layer = addLayer(leaves);
			while(Layer.size()!=1) {
				Layer=addLayer(Layer);
			}
			
			
		}
	}
	
	public List<MerkleTreesNode> addLayer(List<MerkleTreesNode> NodesLayer) {
		List<MerkleTreesNode> parents = new ArrayList<MerkleTreesNode>(NodesLayer.size()/2);
		System.out.println(NodesLayer.size()/2);
			for (int i = 0; i < NodesLayer.size() - 1; i += 2) {
				MerkleTreesNode M = new MerkleTreesNode(NodesLayer.get(i), NodesLayer.get(i+1));
				parents.add(M);
			}
			
			if(NodesLayer.size()%2 != 0) {
				MerkleTreesNode M = new MerkleTreesNode(NodesLayer.get(NodesLayer.size()-1));
				parents.add(M);
			}
			return parents;
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
