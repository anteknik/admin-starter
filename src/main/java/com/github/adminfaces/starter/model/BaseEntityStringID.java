package com.github.adminfaces.starter.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class BaseEntityStringID<T> extends Auditable<String> {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 50)
	protected T id;
	
	public T getId() {
		return this.id;
	}

	public void setId(T id) {
		this.id = id;
	}
			
	public abstract boolean equals(Object T);
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
