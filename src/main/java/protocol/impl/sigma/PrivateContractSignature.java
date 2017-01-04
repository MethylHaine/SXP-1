package protocol.impl.sigma;

import model.entity.ElGamalKey;
import java.util.HashMap;
import java.math.BigInteger;

public class PrivateContractSignature {
	public And[] ands = new And[2];
	private Or pcs;
	private ElGamalKey senderK;
	private ElGamalKey receiverK;
	private ElGamalKey trentK;
	private ResEncrypt res;
	private Sender sender;
	
	//setters
	public void setSender(Sender s){
		sender = s;
		senderK = s.getKeys();
	}
	public void setReceiverKeys(ElGamalKey r){
		receiverK=r;
	}
	public void setTrentKeys(ElGamalKey t){
		trentK=t;
	}
	public void setResEncrypt (ResEncrypt r){
		res = r;
	}
	
	public ElGamalKey getReceiverKey(){
		return receiverK;
	}
	
	/**
	 * Getter
	 * @return pcs : the private contract signature
	 */
	public Or getPcs(){
		return pcs;
	}
	
	/**
	 * Make the PCS
	 */
	public void setPcs(){
		Receiver receiver = new Receiver();
		
		//Creates the Schnorr and CCE signature we will "AND"
		ResponsesCCE resCce1 = sender.SendResponseCCE(res.getM(), trentK);
		ResponsesSchnorr resSchnorr2 = sender.SendResponseSchnorrFabric(receiverK);
		ResponsesCCE resCce2 = sender.SendResponseCCEFabric(res, trentK);
		
		//For the last response, we need to choose the right challenge (to be able to compose in the or) :
		Masks mask = sender.SendMasksSchnorr();
		BigInteger c = sender.SendChallenge(mask, res.getM());
		BigInteger challenge = c.xor(resSchnorr2.getChallenge().xor(resCce1.getChallenge().xor(resCce2.getChallenge())));
		ResponsesSchnorr resSchnorr1 = sender.SendResponseSchnorr(mask, challenge);
		
		//Maps the responses with the receiver key
		HashMap<Responses,ElGamalKey> rK1 = new HashMap <Responses,ElGamalKey>();
		rK1.put(resSchnorr1, senderK);
		rK1.put(resCce1, trentK);
		
		HashMap<Responses,ElGamalKey> rK2 = new HashMap <Responses,ElGamalKey>();
		rK2.put(resSchnorr2, receiverK);
		rK2.put(resCce2, trentK);
		
		
		//Create the arrays of responses and make the "ands"
		Responses[] resp1={resSchnorr1,resCce1};
		Responses[] resp2={resSchnorr2,resCce2};
		
		ands[0] = new And(receiver,rK1,res,resp1);
		ands[1] = new And(receiver,rK2,res,resp2);
		
		//Make the PCS
		pcs = new Or(receiver, mask.getA(), ands);
	}
	
	/**
	 * Constructor
	 * @param m : message to be signed
	 * @param s : sender keys
	 * @param r : receiver public key
	 * @param t : trent public key
	 */
	public PrivateContractSignature(Sender s, ResEncrypt resE, ElGamalKey r, ElGamalKey t){
		setSender(s);
		setReceiverKeys(r);
		setTrentKeys(t);
		setResEncrypt(resE);
		setPcs();
	}
}
