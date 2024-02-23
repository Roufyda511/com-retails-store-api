package eg.retail.store.model;

public enum PurchuserType {
	EMPLOYEE(30), AFFILIATE(10), CUSTOMER(5);

	private final int value;

	PurchuserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
