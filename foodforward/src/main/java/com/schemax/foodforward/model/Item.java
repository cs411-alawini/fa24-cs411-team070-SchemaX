package com.schemax.foodforward.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Item {

    private Long itemId;

    private String itemName;

    private String category;
}
