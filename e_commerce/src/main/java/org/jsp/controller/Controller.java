package org.jsp.controller;

import java.util.List;

import org.jsp.dto.Product;
import org.jsp.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private Service service;

	@PostMapping("/product/save")
	public ResponseEntity<List<Product>> saveProduct(@RequestBody List<Product> product) {
		return ResponseEntity.ok(service.saveProduct(product));
	}

	@GetMapping("/product/search")
	public List<Product> search(@RequestParam String keyword) {
		return service.search(keyword);
	}

//	@GetMapping("/product/filter")
//	public List<Product> filterByCategory(@RequestParam String category){
//		return service.filterByCategory(category);
//	}
	@GetMapping("/product/filter")
	public List<Product> filterByMultipleCategory(@RequestParam List<String> category) {
		return service.filterByMultipleCategory(category);
	}

	@GetMapping("/product/price")
	public List<Product> filterByPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
		return service.filterByPrice(minPrice, maxPrice);
	}

	@GetMapping("/product/pricecategory")
	public List<Product> filterByCategoryAndPrice(@RequestParam List<String> category, @RequestParam double minPrice,
			@RequestParam double maxPrice) {
		return service.filterByCategoryAndPrice(category, minPrice, maxPrice);
	}

	@GetMapping("/product/sorting")
	public List<Product> sorting(@RequestParam String field, @RequestParam String direction) {
		return service.sorting(field, direction);
	}
//	@GetMapping("product/pagination")
//	public Page<Product> pagination(@RequestParam int page,@RequestParam int size){
//		return service.pagination(page,size);
//	}

	@GetMapping("product/pagination")
	public Page<Product> pagination(@RequestParam(name = "page", defaultValue = "2") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		return service.pagination(page, size);
	}
	@PutMapping("product/{id}")
	public ResponseEntity<Product> updateProduct(
	        @PathVariable int id,
	        @RequestBody Product request) {

	    Product product = service.updateProduct(id, request);

	    return ResponseEntity.ok(product);
	}
	@DeleteMapping("product/{id}")
	public ResponseEntity<String> deleteProduct(
	        @PathVariable int id) {

	    return ResponseEntity.ok(
	            service.deleteProduct(id)
	    );
	}
	@PostMapping("/product")
	public ResponseEntity<Product> saveSingleProduct(
	        @RequestBody Product product) {

	    return ResponseEntity.ok(service.saveSingleProduct(product));
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
	    return ResponseEntity.ok(service.getAllProducts());
	}
}
