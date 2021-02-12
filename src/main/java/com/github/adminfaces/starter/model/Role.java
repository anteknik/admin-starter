package com.github.adminfaces.starter.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends BaseEntityLongID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1649099006484049666L;
	
	@Column(name="name", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private Role parent;
	
	@OneToMany(mappedBy="parent", fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();
	
	private String icon;
	
	private String link;
	
	private Integer index;
	
	
	public Role() {}
	
	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role _o = (Role) o;

        return id.equals(_o.id);
    }

    
}
