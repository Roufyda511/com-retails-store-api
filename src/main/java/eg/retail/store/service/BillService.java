package eg.retail.store.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import eg.retail.store.exception.FunctionalException;
import eg.retail.store.model.Bill;
import eg.retail.store.model.Item;
import eg.retail.store.model.ItemType;
import eg.retail.store.model.PurchuserType;
import eg.retail.store.repository.BillRepossitory;
import eg.retail.store.service.dto.BillDto;
import eg.retail.store.service.dto.ItemDto;
import eg.retail.store.util.LocalDateTimeFormatter;
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

			int discountValue = getPurchuserDiscountPercentage(billData);

			Map<String, List<Item>> map = groupBy(billData.getItemsList(), Item::getCode);
			map.values().forEach(itemsList -> {
				int numberOfItems = itemsList.size();
				Optional<Item> i = itemsList.stream().findFirst();

				if (i.isPresent()) {
					Item itemDate = i.get();
					itemDtoList.add(buildItemDto(itemDate, numberOfItems, (discountValue) / (double) 100));

				}
			});
			double totalAfterDiscount = itemDtoList.stream().collect(Collectors.summingDouble(ItemDto :: getPriceAfterDiscount));
			double totalBeforeDiscount = itemDtoList.stream().collect(Collectors.summingDouble(ItemDto :: getPriceBeforeDiscount));
			return BillDto.builder().creationDate(prepareCreationDate(billData.getCreationDate())).billId(billId)
					.marketName(billData.getMarketName()).itemsList(itemDtoList)
					.discountAmount((Integer.toString(discountValue)) + "%").purchuser(billData.getPurchuser().getId())
					.totalAfterDiscount(totalAfterDiscount).totalBeforeDiscount(totalBeforeDiscount).build();

		} else {
			throw new FunctionalException(HttpStatus.BAD_REQUEST, "retails.store.invalid.billId");
		}

	}

	public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
		return Optional.ofNullable(list).orElseGet(ArrayList::new).stream().collect(Collectors.groupingBy(keyFunction));
	}

	public ItemDto buildItemDto(Item item, int numberOfItems, double discount) {

		double priceBeforeDiscount = (item.getPrice() * numberOfItems);

		double priceAfterDiscount = item.getType() != ItemType.GROCERY ? (priceBeforeDiscount) * (1 - discount)
				: priceBeforeDiscount;

		return ItemDto.builder().Type(item.getType()).name(item.getName()).amount(numberOfItems).price(item.getPrice())
				.priceAfterDiscount(priceAfterDiscount).priceBeforeDiscount(priceBeforeDiscount).build();
	}

	private String prepareCreationDate(Long Date) {
		return LocalDateTimeFormatter.convertEpochToLocalDate(Date);

	}

	private int getPurchuserDiscountPercentage(Bill billData) {
		if (billData.getPurchuser().getPurchuserType() != PurchuserType.CUSTOMER) {
			return billData.getPurchuser().getPurchuserType().getValue();
		} else {
			
			LocalDateTime billDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(billData.getCreationDate()),
					TimeZone.getDefault().toZoneId());
			int currentYear = billDate.getYear();

			Instant instant = Instant.ofEpochMilli(billData.getPurchuser().getRegisterationDate().getTime());
			LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

			int purchuserRegYear = ldt.getYear();

			if (currentYear - purchuserRegYear < 2) {
				return 0;
			} else {
				return billData.getPurchuser().getPurchuserType().getValue();
			}
		}

	}
}
