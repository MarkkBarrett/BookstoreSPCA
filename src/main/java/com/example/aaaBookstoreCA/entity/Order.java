package com.example.aaaBookstoreCA.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private double totalPrice;
    private Date orderDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "orders_items") 
    private List<OrderItem> orderItems;

    // Empty constructor
    public Order() {
    }

    // Full constructor
    public Order(User user, double totalPrice, Date orderDate, List<OrderItem> items) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = items;
    }

    // getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
