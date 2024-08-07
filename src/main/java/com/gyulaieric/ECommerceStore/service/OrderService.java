package com.gyulaieric.ECommerceStore.service;

import com.gyulaieric.ECommerceStore.model.*;
import com.gyulaieric.ECommerceStore.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderedItemRepository orderedItemRepository, ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Authentication authentication, Long id) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new IllegalStateException(String.format("User %s doesn't exist", authentication.getName()))
        );

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Order with id %s doesn't exist", id))
        );

        if (order.getUserId().equals(user.getId())) {
            return order;
        } else {
            throw new IllegalStateException("You can only view orders that you have placed");
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new IllegalStateException(String.format("User %s doesn't exist", authentication.getName()))
        );

        return orderRepository.findAllByUserId(user.getId());
    }

    @Override
    public Long addOrder(Order order) {
        for (OrderedItem orderedItem : order.getOrderedItems()) {
           orderedItemRepository.save(orderedItem);
        }

        order.setDate(LocalDate.now());
        orderRepository.save(order);

        for (Cart cart : cartRepository.findAllByUserId(order.getUserId())) {
            cartRepository.delete(cart);
        }

        return order.getId();
    }

    @Override
    public void addProductToOrder(Long id, Long orderedItemId, Long productId, Integer quantity) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Order with id %s doesn't exist", id))
        );

        Optional<OrderedItem> existingOrderItem = order.getOrderedItems().stream().filter(item -> item.getId().equals(orderedItemId)).findFirst();

        if (existingOrderItem.isPresent()) {
            OrderedItem orderedItem = existingOrderItem.get();

            orderedItem.setQuantity(orderedItem.getQuantity() + quantity);

            orderedItemRepository.save(orderedItem);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                OrderedItem newOrderedItem = new OrderedItem();
                newOrderedItem.setProduct(product);
                newOrderedItem.setQuantity(quantity);

                order.addOrderedItem(newOrderedItem);

                orderedItemRepository.save(newOrderedItem);
                orderRepository.save(order);
            } else {
                throw new IllegalStateException(String.format("Product with id %s doesn't exist", productId));
            }
        }
    }

    @Override
    public void deleteProductFromOrder(Long id, Long orderedItemId) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Order with id %s doesn't exist", id))
        );

        Optional<OrderedItem> existingOrderItem = order.getOrderedItems().stream().filter(item -> item.getId().equals(orderedItemId)).findFirst();

        if (existingOrderItem.isPresent()) {
            OrderedItem orderedItem = existingOrderItem.get();

            order.removeOrderedItem(orderedItem);

            orderedItemRepository.delete(orderedItem);
            orderRepository.save(order);
        } else {
            throw new IllegalStateException(String.format("Item (%s) is not in order", orderedItemId));
        }
    }

    @Override
    public void updateOrder(Long id, Order order) {
        Order orderToUpdate = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Order with id %s doesn't exist", id))
        );

        orderToUpdate.setFirstName(order.getFirstName());
        orderToUpdate.setLastName((order.getLastName()));
        orderToUpdate.setEmail(order.getEmail());
        orderToUpdate.setAddress(order.getAddress());
        orderToUpdate.setCountry(order.getCountry());
        orderToUpdate.setState(order.getState());
        orderToUpdate.setCity(order.getCity());
        orderToUpdate.setZipCode(order.getZipCode());
        orderToUpdate.setOrderStatus(order.getOrderStatus());

        orderRepository.save(orderToUpdate);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Order with id %s doesn't exist", id))
        );

        orderRepository.deleteById(id);

        for (OrderedItem orderedItem : order.getOrderedItems()) {
            orderedItemRepository.delete(orderedItem);
        }
    }
}
