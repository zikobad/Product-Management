package com.alten.product.management.dto;

import com.alten.product.management.entity.InventoryStatus;
import lombok.Data;

@Data
public class ProductRequest {
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    private String internalReference;
    private Long shellId;
    private InventoryStatus inventoryStatus;
    private Double rating;
}



