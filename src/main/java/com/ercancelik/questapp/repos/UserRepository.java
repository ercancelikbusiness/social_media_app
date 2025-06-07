package com.ercancelik.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

//Spring Data JPA'da bir repository (veri erişim katmanı) oluşturur. Veritabanındaki User tablosuna erişmek için kullanılır.
/*	<User> : Entity (varlık) sınıfı	User tablo ismi ile aynı değil entity sınıfının ismiyle aynı olmalı
 * Entity'nin @Id ile işaretlenmiş alanının veri tipi	"Long"
 * */
 