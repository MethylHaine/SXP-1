package network.factories;

import network.api.advertisement.ItemAdvertisementInterface;
import network.api.advertisement.PeerAdvertisementInterface;
import network.api.advertisement.UserAdvertisementInterface;
import network.api.advertisement.EstablisherAdvertisementInterface;
import network.impl.advertisement.ItemAdvertisement;
import network.impl.advertisement.PeerAdvertisement;
import network.impl.advertisement.UserAdvertisement;
import network.impl.advertisement.EstablisherAdvertisement;

public class AdvertisementFactory {
	public static ItemAdvertisementInterface createItemAdvertisement() {
		return new ItemAdvertisement();
	}
	
	public static UserAdvertisementInterface createUserAdvertisement() {
		return new UserAdvertisement();
	}
	
	public static PeerAdvertisementInterface createPeerAdvertisement() {
		return new PeerAdvertisement();
	}
	
	public static EstablisherAdvertisementInterface createEstablisherAdvertisement() {
		return new EstablisherAdvertisement();
	}
}
