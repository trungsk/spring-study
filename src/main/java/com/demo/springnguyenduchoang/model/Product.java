package com.demo.springnguyenduchoang.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO) // auto-increment
	// create your own rule of generating using sequence
	@SequenceGenerator(
			name = "product_sequence",
			sequenceName = "product_sequence",
			allocationSize = 1 // increment by 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "product_sequence"
	)

	private Long id;
	@Column(name = "product_name", nullable = false, unique = true, length = 50)
	private String productName;
	private Integer year;
	private Double price;

	@Transient // transient field means this doesn't exist and evolved to DB, just here to caculate
	private int age;

	public Integer getYear() {
		return Calendar.getInstance().get(Calendar.YEAR) - year;
	}

	public Product(String productName, Integer year, Double price) {
		this.productName = productName;
		this.year = year;
		this.price = price;
	}
}
