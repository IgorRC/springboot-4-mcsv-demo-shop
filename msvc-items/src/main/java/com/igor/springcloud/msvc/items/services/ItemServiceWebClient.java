package com.igor.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.igor.springcloud.msvc.items.models.Item;
import com.igor.springcloud.msvc.items.models.Product;

@Primary
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder webClient;

    public ItemServiceWebClient(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Item> findAll() {
        return this.webClient.build().get().uri("http://mscv-products/api/products")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Product.class)
        .map(p -> new Item(p, new Random().nextInt(10) + 1))
        .collectList()
        .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return Optional.ofNullable(this.webClient.build().get().uri("http://mscv-products/api/products{id}", params)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Product.class)
        .map(p -> new Item(p, new Random().nextInt(10) + 1))
        .block());
    }

}
