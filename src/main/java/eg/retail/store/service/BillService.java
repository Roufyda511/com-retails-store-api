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
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class BillService {

	private final BillRepossitory billRepossitory;

	public BillDto calculatePurchuserBill(int billId) throws FunctionalException {
		log.info("calculatePurchuserBill");
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
			double totalBeforeDiscount = itemDtoList.stream()
					.collect(Collectors.summingDouble(ItemDto::getPriceBeforeDiscount));
			double totalAfterDiscount = calculateTotalAmountAfterDiscount(itemDtoList);

			return BillDto.builder().creationDate(prepareCreationDate(billData.getCreationDate())).billId(billId)
					.marketName(billData.getMarketName()).itemsList(itemDtoList)
					.discountAmount((Integer.toString(discountValue)) + "%").purchuser(billData.getPurchuser().getId())
					.totalAfterDiscount(totalAfterDiscount).totalBeforeDiscount(totalBeforeDiscount).build();

		} else {
			throw new FunctionalException(HttpStatus.NOT_FOUND, "retails.store.invalid.billId");
		}

	}

	private double calculateTotalAmountAfterDiscount(List<ItemDto> itemDtoList) {
		log.info("Calculate Discount based on Purchuser Type");
		double discountBasedonPurchuserType = itemDtoList.stream()
				.collect(Collectors.summingDouble(ItemDto::getPriceAfterDiscount));
		log.info("deduct 5 $ per each 100$ in amount");
		return discountBasedonPurchuserType - (((int) (discountBasedonPurchuserType / 100)) * 5);

	}

	private static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
		return Optional.ofNullable(list).orElseGet(ArrayList::new).stream().collect(Collectors.groupingBy(keyFunction));
	}

	private ItemDto buildItemDto(Item item, int numberOfItems, double discount) {

		double priceBeforeDiscount = (item.getPrice() * numberOfItems);

		double priceAfterDiscount = item.getType() != ItemType.GROCERY ? (priceBeforeDiscount) * (1 - discount)
				: priceBeforeDiscount;

		return ItemDto.builder().Type(item.getType()).name(item.getName()).amount(numberOfItems).price(item.getPrice())
				.priceAfterDiscount(Math.round(priceAfterDiscount* 100.0) / 100.0).
				priceBeforeDiscount(Math.round(priceBeforeDiscount* 100.0) / 100.0).build();
	}

	private String prepareCreationDate(Long date) {
		return LocalDateTimeFormatter.convertEpochToLocalDate(date);

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
