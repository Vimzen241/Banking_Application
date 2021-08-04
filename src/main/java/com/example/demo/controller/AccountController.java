package com.example.demo.controller;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Balance;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.BalanceRepo;

@RestController
public class AccountController {

	@Autowired
	AccountRepo repo;

	@Autowired
	BalanceRepo balanceRepo;

	@GetMapping("/")
	public String home() {
		return "Welcome";
	}

	@PostMapping("/create")
	public Account create(@RequestBody Account account) {
		account = repo.save(account);
		int id = account.getId();
		Balance balance = new Balance();
		balance.setAccount_id(id);
		balance.setBalance_amount(0);
		balanceRepo.save(balance);
		return account;
	}

	@GetMapping("/balance/{id}")
	public long balance(@PathVariable("id") int account_id) {
		Balance balance = balanceRepo.findById(account_id).orElse(null);
		if (balance != null) {
			return balance.getBalance_amount();
		}
		return 0;

	}

	@PostMapping("/charge")
	public boolean charge(@RequestBody JSONObject json) {
		int refundAmount = Integer.parseInt(json.get("charge").toString());
		int id = Integer.parseInt(json.get("id").toString());
		Balance balance = balanceRepo.getById(id);
		long existingAmount = balance.getBalance_amount();
		existingAmount = existingAmount + refundAmount;

		balance.setBalance_amount(existingAmount);
		balanceRepo.save(balance);
		return true;
	}

	@PostMapping("/refund")
	public boolean refund(@RequestBody JSONObject json) {
		int refundAmount = Integer.parseInt(json.get("refund").toString());
		int id = Integer.parseInt(json.get("id").toString());
		Balance balance = balanceRepo.getById(id);
		long existingAmount = balance.getBalance_amount();
		existingAmount = existingAmount - refundAmount;
		if (existingAmount < 0) {
			return false;
		} else {
			balance.setBalance_amount(existingAmount);
			balanceRepo.save(balance);
			return true;
		}

	}

}
