package main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
		this.hash = makeHashDigest(left.getHash(),right.getHash());
		this.beginningIndex = left.beginningIndex;
		this.endIndex = right.endIndex;
		this.leftNode = left;
		this.rightNode = right;
	}
	
	public static byte[]  makeHashDigest(byte[] left,byte[] right) {
		byte[] prepended = prependByteArray(new byte[]{0x01}, left);
		byte[] concat = prependByteArray(prepended, right);
		return digest.digest(concat);
	}
	private static byte[] prependByteArray(byte[] prepend,byte[] bytes) {
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
		
	
}
