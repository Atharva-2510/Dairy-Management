package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Product;
import com.DM.dairyManagement.model.Stock;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.StockRepository;
import com.DM.dairyManagement.service.ProductService;
import com.DM.dairyManagement.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class StockController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/stockForm")
    public String showStockForm(Model model) {
        List<Product> products = productService.getAllProducts();   // ⬅️ for dropdown
        List<Stock> stocks = stockService.getAllStocks();           // ⬅️ for table display

        model.addAttribute("products", products);
        model.addAttribute("stocks", stocks);

        return "stockForm"; // View for adding new stock
    }
    
    @GetMapping("/stockList")
	public String showStockList(Model model) {
		List<Stock> stocks = stockService.getAllStocks(); // Fetch all stocks
		model.addAttribute("stocks", stocks);
		return "stockList"; // View for displaying stock list
	}


	@PostMapping("/stock/add")
	public String addStock(@RequestParam("productId") Long productId,
	                       @RequestParam("quantity") int quantity) {
	
	    Product product = productService.getProductById(productId);
	
	    Stock stock = new Stock();
	    stock.setProduct(product);
	    stock.setQuantity(quantity);
	    stock.setLastUpdated(LocalDateTime.now());
	
	    stockService.saveStock(stock);
	
	    return "redirect:/stockForm";
		}

	@DeleteMapping("/stock/delete/{id}")
		public ResponseEntity<String> deleteStock(@PathVariable Long id) {
		    Optional<Stock> stockOptional = stockService.getStockById(id);
	
		    if (stockOptional.isPresent()) {
		        stockService.deleteStockById(id);
		        return ResponseEntity.ok("Stock deleted successfully");
		    } else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found");
		    }
		}
		
		@GetMapping("/stock/edit/{id}")
		public String editStock(@PathVariable Long id, Model model) {
		    Stock stock = stockRepository.findById(id)
		            .orElseThrow(() -> new IllegalArgumentException("Invalid stock ID: " + id));
		    
		    List<Product> products = productRepository.findAll(); // Fetch product list
	
		    model.addAttribute("stock", stock);
		    model.addAttribute("products", products);
	
		    return "editStockDetails"; // Load edit_stock.html
		}
		
		@PostMapping("/stock/update")
		public String updateStock(@RequestParam Long stockId, 
		                          @RequestParam Long productId, 
		                          @RequestParam int quantity) {
		    Stock stock = stockRepository.findById(stockId)
		            .orElseThrow(() -> new IllegalArgumentException("Invalid stock ID: " + stockId));
		    
		    Product product = productRepository.findById(productId)
		            .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

		    stock.setProduct(product);
		    stock.setQuantity(quantity);
		    stock.setLastUpdated(LocalDateTime.now());

		    stockRepository.save(stock);

		    return "redirect:/stockList"; // Redirect back to stock list
		}

		@PostMapping("/stock/return")
		public ResponseEntity<String> returnStock(@RequestParam("stockId") Long stockId,
		                                          @RequestParam("quantity") String returnQuantityStr) {
		    Stock stock = stockRepository.findById(stockId)
		            .orElseThrow(() -> new IllegalArgumentException("Invalid stock ID: " + stockId));

		    int currentQty = stock.getQuantity();
		    int returnQty = Integer.parseInt(returnQuantityStr);

		    if (returnQty > currentQty) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                .body("Return quantity cannot be greater than available quantity.");
		    }

		    int updatedQty = currentQty - returnQty;
		    int returnedQty = Integer.parseInt(stock.getReturnedQuantity()) + returnQty;

		    stock.setQuantity(updatedQty);
		    stock.setReturnedQuantity(String.valueOf(returnedQty));
		    stockRepository.save(stock);

		    return ResponseEntity.ok("Return successful");
		}

		// Add this endpoint to your controller

		@GetMapping("/api/stock/all")
		@ResponseBody
		public List<Stock> getAllStockData() {
		    return stockService.getAllStocks();
		}

}
