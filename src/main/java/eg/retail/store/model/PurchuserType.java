package eg.retail.store.model;

public enum PurchuserType {
	EPMOLYEE(30), AFFILIATE1(10), CUSTOMER(5);

	private final int value;

	PurchuserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
