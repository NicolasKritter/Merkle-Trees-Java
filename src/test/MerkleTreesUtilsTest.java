package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.MerkleTreesNode;
import main.MerkleTreesUtils;

class MerkleTreesUtilsTest extends MerkleTreesUtils {
	
	static final String[] testLogPaire = {"l1","l2","l3","l4","l5","l6","l7","l8"};
	static final String[] testLogInPaire = {"l1","l2","l3","l4","l5","l6","l7","l8","l9"};

	static List<MerkleTreesNode> listePaire =  new LinkedList<>();
	static List<MerkleTreesNode> listeInPaire =  new LinkedList<>();

	static int index = 0;

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
	} 
	
	@Test
	void buildTreesPair() {
		MerkleTreesNode node= MerkleTreesUtils.buildTree(listePaire);
		assertNotNull(node);
		assertEquals(1, node.getBeginningIndex());
		assertEquals(testLogPaire.length, node.getEndIndex());
		
	}
	@Test
	void buildTreesPairImpaire() {
		MerkleTreesNode node= MerkleTreesUtils.buildTree(listeInPaire);
		assertNotNull(node);
		assertEquals(1, node.getBeginningIndex());
		assertEquals(testLogInPaire.length, node.getEndIndex());
		
	}
}
