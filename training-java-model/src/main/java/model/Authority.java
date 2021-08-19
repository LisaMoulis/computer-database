package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String authority;
	
	public Authority(String string) {
		this.authority = string;
	}

	public Authority()
	{}

	@Override
	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String id)
	{
		this.authority = id;
	}

}
