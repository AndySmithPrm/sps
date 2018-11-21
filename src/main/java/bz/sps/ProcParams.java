package bz.sps;

import java.util.UUID;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/*
 * структура пяти параметров вызова обеих функций
 * задается валидация
 * в принципе надо было сделать отдельно, структура для входящих запросов с валидацией,
 * отдельно структуры для вызовов базы,
 * тут они одинаковы, разделить будет легко, когда отличаться начнут
 */
public class ProcParams {
	@NotNull
	@Size(min = 5, max = 10)
	private String name;
	@NotNull
	@Min(18)
	@Max(118)
	private Integer age;
	@NotNull
	@DecimalMin("50.0")
	@DecimalMax("100.0")
	private Double weight;
	private UUID id;
	@Email
	private String email;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getWeight() {
		return weight;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
