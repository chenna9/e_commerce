package org.jsp.repository;

import java.util.List;

import org.jsp.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
	List<Product> findByBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(String brand, String category);
	List<Product> findByCategoryContainingIgnoreCase(String category);
	List<Product> findByCategoryIn(List<String> categories);
	List<Product> findByPriceGreaterThanEqualAndPriceLessThanEqual(double minPrice,double maxPrice);
	List<Product> findByCategoryInAndPriceGreaterThanEqualAndPriceLessThanEqual(List<String> category,double minPrice,double maxPrice);
	
}
