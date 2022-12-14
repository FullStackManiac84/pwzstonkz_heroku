package com.zywicki.pwzstonkz;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"https://buddy-stonkz.herokuapp.com"}, 
methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class StockResource {

	@Autowired
	StockRepository repo;
	
	@GetMapping("stock/{id}")
	public Optional<Stock> getStock(@PathVariable(value = "id") String ticker) {
		return repo.findById(ticker);
	}
	
	@GetMapping("/stocks")
	public List<Stock> getStocks() {
		return (List<Stock>) repo.findAll();
	}
	
	@PostMapping("/addStock")
	public void createStock(@RequestBody Stock s) {
		repo.save(s);
	}
	
	@PutMapping("/stock/{id}")
	public void updateStock(@PathVariable(value = "id") String ticker, 
			@RequestBody Stock s) {
			Optional<Stock> hotStock = repo.findById(ticker);
			if (hotStock.isEmpty()) {
				repo.save(s);
			} else {
				hotStock.get().setHigh(s.getHigh());
				hotStock.get().setLow(s.getLow());
				hotStock.get().setOpen(s.getOpen());
				hotStock.get().setClose(s.getClose());
				repo.save(hotStock.get());
			}
	}
	
	@DeleteMapping("/stock/{id}")
	public void deleteStock(@PathVariable(value = "id") String ticker) {
		repo.deleteById(ticker);
	}
}
