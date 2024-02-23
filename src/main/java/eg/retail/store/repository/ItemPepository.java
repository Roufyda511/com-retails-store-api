package eg.retail.store.repository;

import org.springframework.data.repository.CrudRepository;

import eg.retail.store.model.Item;

public interface ItemPepository  extends CrudRepository<Item, Integer>{

}
