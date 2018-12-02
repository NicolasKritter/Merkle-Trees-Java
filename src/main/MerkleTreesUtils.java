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
	
	public static List<byte[]> genPath(int index,MerkleTreesNode node) {
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
	
	
	private static boolean isInNode(int index, MerkleTreesNode node) {
		return (index>=node.getBeginningIndex() && index<= node.getEndIndex());

	}
	
	private static MerkleTreesNode mergeTrees(MerkleTreesNode node1,MerkleTreesNode node2) {
		if (node1.getEndIndex()<=node2.getBeginningIndex()) {
			return new MerkleTreesNode(node1, node2);
		}else {
			return new MerkleTreesNode(node2, node1);
		}
	}
	
	public static MerkleTreesNode addNodestoTree(MerkleTreesNode tree,List<MerkleTreesNode> list) {
		MerkleTreesNode newTree = buildTree(list);
		return mergeTrees(tree, newTree);
	}
	
	
	public static List<byte[]> genProof(int i,MerkleTreesNode build) {
		List<byte[]> result = new ArrayList<>();
//		if (0 < i && i < index) {
			MerkleTreesNode temp = build;
			while (temp!=null && temp.getEndIndex()!=i ) {
					if (isInNode(i,temp.getLeftNode())) {
						System.out.println("left");
						result.add(temp.getRightNode().getHash());
						temp = temp.getLeftNode();
					}
					else if (isInNode(i,temp.getRightNode())){
						result.add(temp.getLeftNode().getHash());
						System.out.println("right");
						temp = temp.getRightNode();
					}else {
						result.add(null);
						temp=null;
					}
				
			}
			if (temp!=null) {
				result.add(temp.getHash());
			}
			return result;
//		}
//		else {
//			System.out.println("i non valide");
//			return null;
//		}
	}
	
	
}
