package eg.retail.store.repository;

import org.springframework.data.repository.CrudRepository;

import eg.retail.store.model.Bill;

public interface BillRepossitory extends CrudRepository<Bill,Integer> {

}
