package org.sadmansakib.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PopularProductService {
    private Map<String, List<PopularProduct>> dataStore = new HashMap<>();
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @EventListener(ApplicationReadyEvent.class)
    public void buildDataset() {
        var popularProducts = Stream.of(
                new PopularProduct("IDLC", "DPS", 10, "DPS_001"),
                new PopularProduct("IDLC", "DPS", 20, "DPS_002"),
                new PopularProduct("IDLC", "DPS", 30, "DPS_003"),
                new PopularProduct("IDLC", "FDR", 4, "FDR_001"),
                new PopularProduct("IDLC", "FDR", 20, "FDR_002"),
                new PopularProduct("IDLC", "FDR", 10, "FDR_003")
        ).sorted(Comparator.comparing(PopularProduct::frequency).reversed()).toList();

        log.info("Popular products: {}", popularProducts);

        var products = Stream.of(
                new Product("IDLC", "DPS", "DPS_001", "MONTHLY",
                        12, false, true, 10.0, "REGULAR"),
                new Product("IDLC", "DPS", "DPS_002", "MONTHLY",
                        24, false, true, 20.0, "ISLAMIC"),
                new Product("IDLC", "DPS", "DPS_003", "MONTHLY",
                        36, false, true, 30.0, "REGULAR"),
                new Product("IDLC", "FDR", "FDR_001", "MONTHLY",
                        12, false, true, 40.0, "ISLAMIC"),
                new Product("IDLC", "FDR", "FDR_002", "MONTHLY",
                        24, false, true, 20.0, "REGULAR"),
                new Product("IDLC", "FDR", "FDR_003", "MONTHLY",
                        36, false, true, 10.0, "REGULAR")

        ).sorted((p1, p2) -> p2.getProfit().compareTo(p1.getProfit())).toList();

        log.info("Products: {}", products);

        dataStore = popularProducts.stream()
                .map(popularProduct -> {
                    var matchedProduct = products.stream()
                            .filter(product -> product.getProductCode().equals(popularProduct.productCode()))
                            .findFirst()
                            .orElse(null);
                    return Map.entry(matchedProduct.getTerm(), popularProduct);
                }).collect(Collectors
                        .groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        var tracker = new ConcurrentHashMap<>(Map.of("REGULAR", false, "ISLAMIC", false));

        var popularListForATerm = (dataStore.get("MONTHLY"));

        log.info("Popular list for term: {}", popularListForATerm);

        popularListForATerm
                .forEach(
                popularProduct -> {
                    products.stream()
                            .filter(Product::isActive)
                            .filter(product -> product.getProductCode().equals(popularProduct.productCode()))
                            .findFirst()
                            .ifPresent(product -> {
                                tracker.computeIfAbsent(product.getAccountType(), k -> false);
                                if (!tracker.get(product.getAccountType())) {
                                    product.setPopular(true);
                                    tracker.put(product.getAccountType(), true);
                                }
                            });
                }
        );
        var sorted = products.stream()
                .filter(Product::isActive)
                .filter(product -> product.getTerm().equals("MONTHLY"))
                .collect(Collectors.groupingBy(Product::getAccountType))
                .values()
                .stream()
                .flatMap(List::stream)
                .sorted((p1, p2) -> {
                    if (p1.isPopular() && p2.isPopular()) {
                        if (p1.getAccountType().equals("REGULAR")) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                    if (p1.isPopular() && !p2.isPopular()) {
                        return -1; // Popular products come first
                    } else if (!p1.isPopular() && p2.isPopular()) {
                        return 1; // Non-popular products come after popular products
                    } else {
                        // Compare based on total payout for non-popular products, descending order
                        return Double.compare(p2.getProfit(), p1.getProfit());
                    }
                }).toList();

        log.info("tracker post sorting: {}", tracker);

        sorted.forEach(product -> log.info("Product post processing: {}", product));
    }
}
