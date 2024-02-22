package eg.retail.store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import eg.retail.store.exception.FunctionalException;
import eg.retail.store.model.Bill;
import eg.retail.store.model.Item;
import eg.retail.store.model.ItemType;
import eg.retail.store.repository.BillRepossitory;
import eg.retail.store.service.dto.BillDto;
import eg.retail.store.service.dto.ItemDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BillService {

	private final BillRepossitory billRepossitory;

	public BillDto calculatePurchuserBill(int billId) throws FunctionalException {

		Optional<Bill> bill = billRepossitory.findById(billId);
		if (bill.isPresent()) {
			Bill billData = bill.get();

			
			List<ItemDto> itemDtoList = new ArrayList<>();

			int discountValue = billData.getPurchuser().getPurchuserType().getValue();

			Map<Integer, List<Item>> map = groupBy(billData.getItemsList(), Item::getId);
			map.values().forEach(itemsList -> {
				int numberOfItems = itemsList.size() + 1;
				Optional<Item> i = itemsList.stream().findFirst();
				if (i.isPresent()) {
					Item itemDate = i.get();
					itemDtoList.add(buildItemDto(itemDate, numberOfItems, discountValue));

				}
			});
			return BillDto.builder().creationDate(billData.getCreationDate()).billId(billId)
					.marketName(billData.getMarketName()).itemDtoList(itemDtoList).discountAmount(discountValue)
					.purchuser(billData.getPurchuser().getId()).build();

		} else {
			throw new FunctionalException(HttpStatus.BAD_REQUEST, "invalid.billId");
		}

	}

	public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
		return Optional.ofNullable(list).orElseGet(ArrayList::new).stream().collect(Collectors.groupingBy(keyFunction));
	}

	public ItemDto buildItemDto(Item item, int numberOfItems, int discount) {

		int priceBeforeDiscount = (item.getPrice() * numberOfItems);

		int priceAfterDiscount = item.getType() != ItemType.GROCERY ? (priceBeforeDiscount) * discount / 100
				: priceBeforeDiscount;

		return ItemDto.builder().Type(item.getType()).name(item.getName()).amount(numberOfItems).price(item.getPrice())
				.priceAfterDiscount(priceAfterDiscount).priceBeforeDiscount(priceBeforeDiscount).build();
	}

}
