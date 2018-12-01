package main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MerkleTreesNode {
	static  MessageDigest digest ;
	private byte[] hash;
	private MerkleTreesNode leftNode;
	private MerkleTreesNode rightNode;
	private int beginningIndex;
	private int endIndex;
	
	public MerkleTreesNode(String log, int index) {
		generateDigest();

		try {
			byte[] bytes = log.getBytes("UTF-8");
			byte[] leaf = prependByteArray(new byte[]{0x00},bytes);
			this.hash = digest.digest(leaf);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.beginningIndex = index;
		this.endIndex = index;
	}
	
	public MerkleTreesNode(MerkleTreesNode left,MerkleTreesNode right) {
		generateDigest();
		
		byte[] prepended = prependByteArray(new byte[]{0x01}, left.getHash());
		byte[] concat = prependByteArray(prepended, right.getHash());
		this.hash = digest.digest(concat);
		this.beginningIndex = left.beginningIndex;
		this.endIndex = right.endIndex;
		this.leftNode = left;
		this.rightNode = right;
	}
	
	private byte[] prependByteArray(byte[] prepend,byte[] bytes) {
		byte[] out = new byte[bytes.length+prepend.length];
		for (int i = 0; i < prepend.length; i++) {
			out[i] = prepend[i];
		}
		for (int i = prepend.length; i < bytes.length; i++) {
			out[i] = bytes[i];
		}

		return out;
	}
	private void generateDigest() {
		if (digest==null) {
			try {
				digest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static List<MerkleTreesNode> addTreeLayer(List<MerkleTreesNode> NodesLayer) {
		List<MerkleTreesNode> parents = new ArrayList<MerkleTreesNode>(NodesLayer.size()/2);
			for (int i = 0; i < NodesLayer.size() - 1; i += 2) {
				MerkleTreesNode M = new MerkleTreesNode(NodesLayer.get(i), NodesLayer.get(i+1));
				parents.add(M);
			}
			
			if(NodesLayer.size()%2 != 0) {
				MerkleTreesNode M = new MerkleTreesNode(NodesLayer.get(NodesLayer.size()-1), NodesLayer.get(NodesLayer.size()-1));
				parents.add(M);
			}
			return parents;
		}
	
	public static List<byte[]> navigate(int index,MerkleTreesNode node) {
		List<byte[]> hashes = new LinkedList<>();
		while (node!=null ) {
		if (node.getBeginningIndex()==index && node.getEndIndex()==index) {
			return hashes;
			}
		if (isInNode(index,node.getLeftNode())) {
			hashes.add(node.getRightNode().getHash());
			node = node.getLeftNode();
		}else if (isInNode(index,node.getRightNode())){
			hashes.add(node.getLeftNode().getHash());
			node =  node.getRightNode();
		}else {
			node = null;
		}
		}
		return hashes;

	}
	
	public static boolean isInNode(int index, MerkleTreesNode node) {
		return (index>=node.getBeginningIndex() && index<= node.getEndIndex());

	}
	public byte[] getHash() {
		return hash;
	}

	public MerkleTreesNode getLeftNode() {
		return leftNode;
	}

	public MerkleTreesNode getRightNode() {
		return rightNode;
	}

	public int getBeginningIndex() {
		return beginningIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}


	public void setLeftNode(MerkleTreesNode leftNode) {
		this.leftNode = leftNode;
	}

	public void setRightNode(MerkleTreesNode rightNode) {
		this.rightNode = rightNode;
	}
	
	@Override
	public String toString() {
		return "MerkleTreesNode [hash=" +hash + ", leftNode="
				+ leftNode.getHash() + ", rightNode=" + rightNode.getHash() + ", beginningIndex="
				+ beginningIndex + ", endIndex=" + endIndex + "]";
	}


	
	
}
