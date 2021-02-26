package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {
    private CartController cartController;

    private UserController userController;

    private CartRepository cartRepository = mock(CartRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        userController = new UserController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
        when(userRepository.findByUsername("test")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        when(cartRepository.save(any())).thenReturn(createCart());
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

    private Item createItem(){
        Item item = new Item();
        item.setId(Long.valueOf(1));
        item.setDescription("clothes");
        item.setPrice(BigDecimal.valueOf(7,05));
        return item;
    }

    @Test
    public void verifyAddToCart() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());

        ModifyCartRequest c = new ModifyCartRequest();
        c.setUsername(u.getUsername());
        c.setItemId(1);
        c.setQuantity(1);

        ResponseEntity<Cart> cart = cartController.addTocart(c);

        assertNotNull(cart);
        assertEquals(HttpStatus.OK, cart.getStatusCode());
    }

    @Test
    public void verifyRemoveFromCart() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());

        ModifyCartRequest c = new ModifyCartRequest();
        c.setUsername(u.getUsername());
        c.setItemId(1);
        c.setQuantity(1);

        ResponseEntity<Cart> cart = cartController.removeFromcart(c);

        assertNotNull(cart);
        assertEquals(HttpStatus.OK, cart.getStatusCode());
    }

}
