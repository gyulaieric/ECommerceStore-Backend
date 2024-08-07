package com.gyulaieric.ECommerceStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ordered_items")
public class OrderedItem {
    @Id
    @SequenceGenerator(
            name = "ordered_items_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "ordered_items_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Quantity should not be null")
    @Min(1)
    private Integer quantity;

    public OrderedItem() { }

    public OrderedItem( Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
