package org.jsp.repository;

import java.util.Optional;

import org.jsp.dto.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

}