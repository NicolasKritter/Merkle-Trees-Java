package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.internal.runners.statements.Fail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.LogServer;
import main.MerkleTreesNode;
import main.MerkleTreesUtils;

class LogServerTest {
	
	static final String[] testLogPaire = {"l1","l2","l3","l4","l5","l6","l7","l8"};
	static final String[] testLogInPaire = {"l1","l2","l3","l4","l5","l6","l7","l8","l9"};

	static List<MerkleTreesNode> listePaire =  new LinkedList<>();
	static List<MerkleTreesNode> listeInPaire =  new LinkedList<>();

	static int index = 0;
	static MerkleTreesNode tree;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		genList();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	static void genList() {
		MerkleTreesNode node;
		for (String log : testLogPaire) {
			index+=1;
			node = new MerkleTreesNode(log, index);
			System.out.println(node.getHash());
			listeInPaire.add(node);
			listePaire.add(node);
		}
		listeInPaire.add(new MerkleTreesNode(testLogInPaire[index], index+1));
		tree= MerkleTreesUtils.buildTree(listePaire);
	} 
	

	
	@Test
	void genPathpoure4() {
		List<byte[]>list = MerkleTreesUtils.navigate(4,tree);
		assertNotNull(list);
		System.out.println(list);
		//on reprend l'exemple du cours
		//le dernier élément est le hash de e3
		MerkleTreesNode h12 = tree.getLeftNode().getLeftNode();
		assertEquals(3, list.size());
		assertEquals(listePaire.get(2).getHash(), list.get(2),"h3");
		assertEquals(h12.getHash(), list.get(1),"h12");
		assertEquals(tree.getRightNode().getHash(), list.get(0),"h58");

	
	}
	
	@Test
	void genPathpoureNotFound() {
		List<byte[]>list = MerkleTreesUtils.navigate(9,tree);
		assertNotNull(list);
		System.out.println(list);
		//on reprend l'exemple du cours
		//le dernier élément est le hash de e3
		assertEquals(null, list.get(list.size()-1),"-1");
	}

}
