package org.example.integration;

import java.util.List;

import org.example.dto.SupermarketProductDTO;

public interface SupermarketProvider {
    String name();
    List<SupermarketProductDTO> search(String query);
}

