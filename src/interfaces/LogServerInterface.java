package interfaces;

import java.rmi.Remote;
import java.util.List;

public interface LogServerInterface extends Remote{
	void appendEvent(List<String> logs);
	byte[] getRootHash();
	List<byte[]> genPath(int i);
	List<byte[]> genProof(int i);
	
}
