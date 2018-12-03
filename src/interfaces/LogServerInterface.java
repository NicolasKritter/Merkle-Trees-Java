package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LogServerInterface extends Remote{
	void appendEvent(List<String> logs)throws RemoteException;;
	byte[] getRootHash()throws RemoteException;;
	List<byte[]> genPath(int i)throws RemoteException;;
	List<byte[]> genProof(int i)throws RemoteException;;
	
}
