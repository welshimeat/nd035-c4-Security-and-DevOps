package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp() throws Exception {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        when(userRepository.findByUsername("test")).thenReturn(createUser());
        when(orderRepository.save(any())).thenReturn(createOrder());
        when(orderRepository.findByUser(any())).thenReturn(List.of(createOrder()));
    }

    private User createUser() {
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(createCart());
        return user;
    }

    public Cart createCart() {
        Cart cart = new Cart();
        cart.setId(Long.valueOf(1));
        cart.addItem(createItem());
        return cart;
    }

    private UserOrder createOrder() {
        return UserOrder.createFromCart(createCart());
    }

    private Item createItem(){
        Item item = new Item();
        item.setId(Long.valueOf(1));
        item.setDescription("clothes");
        item.setPrice(BigDecimal.valueOf(7,05));
        return item;
    }

    @Test
    public void verifySubmit() {
        ResponseEntity<UserOrder> userOrder = orderController.submit("test");
        assertEquals(HttpStatus.OK, userOrder.getStatusCode());
    }

    @Test
    public void verifyGetOrdersForUser() {
        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser("test");
        assertEquals(HttpStatus.OK, ordersForUser.getStatusCode());
    }
}
