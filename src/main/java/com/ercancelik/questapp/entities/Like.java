package com.ercancelik.questapp.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="p_like")
@Data

public class Like {

	@Id
	Long id;
	@ManyToOne(fetch = FetchType.LAZY)     //burayı anlaman için Post ve comment  entity'sindeki yorum satırlarını oku  burdada en altta anlattım
	@JoinColumn(name="post_id", nullable = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	
	@JsonIgnore
	Post post;   
	 
	
	@ManyToOne(fetch = FetchType.LAZY)    // bu kısımı Post sınıfında yorum olarak anlattım	
	@JoinColumn(name="user_id", nullable = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	
	@JsonIgnore
	User user;   
	
}

