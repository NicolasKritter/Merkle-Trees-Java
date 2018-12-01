package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MerkleTreesUtils {

	
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
			hashes.add(null);
			node = null;
		}
		}
		return hashes;

	}
	
	public static MerkleTreesNode buildTree(List<MerkleTreesNode> list) {
		if (list.isEmpty()) {return new MerkleTreesNode("", 0);}
		List<MerkleTreesNode> layer = addTreeLayer(list);
		System.out.println(layer.size());
		while(layer.size()!=1) {
			layer=addTreeLayer(layer);
			System.out.println(layer.size());

		}
		return layer.get(0);
	}
	
	
	public static boolean isInNode(int index, MerkleTreesNode node) {
		return (index>=node.getBeginningIndex() && index<= node.getEndIndex());

	}
}
