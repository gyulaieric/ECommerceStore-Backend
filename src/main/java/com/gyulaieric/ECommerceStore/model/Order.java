package com.gyulaieric.ECommerceStore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "order_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private LocalDate date;
    @NotNull(message = "User ID should not be null")
    private Long userId;
    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty(message = "Address should not be empty")
    private String address;
    @NotEmpty(message = "Country should not be empty")
    private String country;
    @NotEmpty(message = "State should not be empty")
    private String state;
    @NotEmpty(message = "City should not be empty")
    private String city;
    @NotEmpty(message = "Zip code should not be empty")
    private String zipCode;
    @ManyToMany(
           fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "order_ordered_items_junction",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "ordered_item_id")}
    )
    private List<OrderedItem> orderedItems;
    private OrderStatus orderStatus;

    public Order() { }

    public Order(LocalDate date, Long userId ,String firstName, String lastName, String email, String address, String country, String state, String city, String zipCode, List<OrderedItem> orderedItems,OrderStatus orderStatus) {
        this.date = date;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.orderedItems = orderedItems;
        this.orderStatus = orderStatus;
    }

    public void addOrderedItem(OrderedItem orderedItem) {
        orderedItems.add(orderedItem);
    }

    public void removeOrderedItem(OrderedItem orderedItem) {
        orderedItems.remove(orderedItem);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(userId, order.userId) && Objects.equals(firstName, order.firstName) && Objects.equals(lastName, order.lastName) && Objects.equals(email, order.email) && Objects.equals(address, order.address) && Objects.equals(country, order.country) && Objects.equals(state, order.state) && Objects.equals(city, order.city) && Objects.equals(zipCode, order.zipCode) && Objects.equals(orderedItems, order.orderedItems) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, userId, firstName, lastName, email, address, country, state, city, zipCode, orderedItems, orderStatus);
    }
}
