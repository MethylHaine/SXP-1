package model.factory;

import model.api.ContractSyncManager;
import model.api.ItemSyncManager;
import model.api.UserSyncManager;
import model.syncManager.UserSyncManagerImpl;
import model.syncManager.ContractSyncManagerImpl;
import model.syncManager.ItemSyncManagerImpl;

public class SyncManagerFactory {
	public static UserSyncManager createUserSyncManager() {
		return new UserSyncManagerImpl();
	}
	public static ItemSyncManager createItemSyncManager() {
		return new ItemSyncManagerImpl();
	}
	public static ContractSyncManager createContractSyncManager() {
		return new ContractSyncManagerImpl();
	}
}
