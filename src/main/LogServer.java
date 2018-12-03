package main;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import interfaces.LogServerInterface;

public class LogServer extends UnicastRemoteObject implements LogServerInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String filePath;
	MerkleTreesNode tree;
	private int index = 0;
	private List<byte[]> hashList = new ArrayList<>();
	public LogServer(String filePath) throws RemoteException  {
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
		MerkleTreesNode n;
		try(Scanner scan = new Scanner(FileSystems.getDefault().getPath(path))) {
			//lecture via scanner
			String word; 
			while(scan.hasNextLine()) {
				 word = scan.nextLine();
				 n = new MerkleTreesNode(word, index);
				 liste.add(n);
				 hashList.add(n.getHash());
				 index+=1;
			}
		}
		return liste;
	}

	
	public void appendEvent(List<String> logs) {
		List<MerkleTreesNode> liste = new LinkedList<MerkleTreesNode>();
		MerkleTreesNode n;
		for(String s: logs) {
			 n = new MerkleTreesNode(s, index);
			 liste.add(n);
			 hashList.add(n.getHash());
			 index+=1;
		}
		tree = MerkleTreesUtils.addNodestoTree(tree, liste);
	}
	
	@Override
	public byte[] getRootHash() {
		if (tree == null) {
			return null;
		}
		return tree.getHash();
	}
	
	public int getIndex(byte[] hash) {
		return hashList.indexOf(hash);
	}
	
	@Override
	public List<byte[]> genPath(int i) {
		return MerkleTreesUtils.genPath(i, tree);
	}
	@Override
	public List<byte[]> genProof(int i) {
		return MerkleTreesUtils.genProof(i, tree);
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public int getIndex() {
		return index;
	}
}
