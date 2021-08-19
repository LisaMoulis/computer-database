package model;

import java.util.ArrayList;

import javax.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String username;
	@Column
	private String password;
	@Column
	private String authority;
	@Column
	private boolean enabled;
	@Transient
	private boolean accountExpired = false;
	@Transient
	private boolean locked = false;
	@Transient
	private boolean credentialExpired = false;
	
	public User(String name, String encode) {
		this.username = name;
		this.password = encode;
		this.authority = "USER";
		this.enabled = true;
		//this.authorities = new ArrayList<Authority>();
		//this.authorities.add(new Authority("USER"));
	}

	public User()
	{}
	
	@Override
	public ArrayList<Authority> getAuthorities() {
		ArrayList<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(this.authority));
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !this.credentialExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
