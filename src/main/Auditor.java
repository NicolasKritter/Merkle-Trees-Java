package main;

import java.util.List;

public class Auditor {
	LogServer logServer;
	byte[] rootHash;
	int size;
	public Auditor(LogServer logServer) {
		this.logServer = logServer;
		this.rootHash = logServer.getRootHash();
		this.size = logServer.getIndex();
		System.out.println(size);
		// TODO Auto-generated constructor stub
	}
	public boolean isMember(String event) {
		byte[] hash = new MerkleTreesNode(event, 0).getHash();
		int index = logServer.getIndex(hash);
		if (index==-1) {
			return false;
		}
		List<byte[]> genPath = logServer.genPath(index);
		if (genPath.get(genPath.size())==null) {
			return false;
		}else {
			byte[] computedhash = rebuildHash(hash, genPath,index);
			return isRootHash(computedhash);
		}
	}
	
	private byte[] rebuildHash(byte[] hash,List<byte[]> path,int index) {
		byte[] computedhash = hash;
		for (int i = 0; i<path.size(); i++) {
			if (isLeft(index, i)) {
				computedhash = MerkleTreesNode.makeHashDigest(computedhash, path.get(path.size()-i));
			}else {
				computedhash = MerkleTreesNode.makeHashDigest(path.get(path.size()-i), computedhash);
			}
		}
		return computedhash;
	}
	
	public  boolean isRootHash(byte[] hash) {
		return hash.equals(this.rootHash);
	}
	
	private boolean isLeft(int index, int i) {
		if (i==0 && index%2==0) {return false;}
		return true;
	}
}
