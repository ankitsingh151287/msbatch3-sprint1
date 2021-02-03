package com.sl.ms.sprint1.superleaguesprint1.util;

import java.time.LocalDate;
import java.util.Date;

public class InventoryBean {
		public int Id;
		public String Name; 
		public double Price; 
		public int Stock;
		public int Sales;
		public LocalDate OrderDate;
		public int getId() {
			return Id;
		}
		public int getSales() {
			return Sales;
		}
		public void setSales(int sales) {
			Sales = sales;
		}
		public void setId(int id) {
			Id = id;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		
		public double getPrice() {
			return Price;
		}
		public void setPrice(double price) {
			Price = price;
		}
		public int getStock() {
			return Stock;
		}
		public void setStock(int stock) {
			Stock = stock;
		}

	public LocalDate getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		OrderDate = orderDate;
	}

	@Override
	public String toString() {
		return "ShopInventory{" +
				"Id=" + Id +
				", Name='" + Name + '\'' +
				", Price=" + Price +
				", Stock=" + Stock +
				", Sales=" + Sales +
				", OrderDate=" + OrderDate +
				'}';
	}
}
