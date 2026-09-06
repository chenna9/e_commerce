package org.jsp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jsp.dto.Product;
import org.jsp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@org.springframework.stereotype.Service
public class Service {
	@Autowired
	private ProductRepo productRepo;
	
	public List<Product> saveProduct(List<Product> product) {
		for (Product product1 : product) {
		  product1.setCreatedDate(LocalDateTime.now());
		}

		return productRepo.saveAll(product);
	}

	public List<Product> search(String keyword) {
		
		return productRepo.findByBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);
	}

	public List<Product> filterByCategory(String category) {
		return productRepo.findByCategoryContainingIgnoreCase(category);
	}
	

	public List<Product> filterByMultipleCategory(List<String> category) {
		
		return productRepo.findByCategoryIn(category);
	}

	public List<Product> filterByPrice(double minPrice, double maxPrice) {
		return productRepo.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
	}

	public List<Product> filterByCategoryAndPrice(List<String> category, double minPrice, double maxPrice) {
		return productRepo.findByCategoryInAndPriceGreaterThanEqualAndPriceLessThanEqual(category, minPrice, maxPrice);
	}

	public List<Product> sorting(String field, String direction) {
		Sort sort;
		if(direction.equalsIgnoreCase("asc")) {
			sort = Sort.by(field).ascending();
		}
		else {
			sort = Sort.by(field).descending();
		}
		return productRepo.findAll(sort);
	}

	public Page<Product> pagination(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepo.findAll(pageable);
	}
	public Product updateProduct(int id, Product product) {

	    Product existingProduct = productRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Product not found"));

	    existingProduct.setName(product.getName());
	    existingProduct.setDescription(product.getDescription());
	    existingProduct.setPrice(product.getPrice());
	    existingProduct.setBrand(product.getBrand());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setSubCategory(product.getSubCategory());
	    existingProduct.setSize(product.getSize());
	    existingProduct.setColor(product.getColor());
	    existingProduct.setStock(product.getStock());
	    existingProduct.setRating(product.getRating());
	    existingProduct.setStatus(product.getStatus());

	    return productRepo.save(existingProduct);
	}
	
	public String deleteProduct(int id) {

	    Product product = productRepo.findById(id)
	            .orElseThrow(() ->
	                    new RuntimeException("Product not found"));

	    productRepo.delete(product);

	    return "Product deleted successfully";
	}
}
