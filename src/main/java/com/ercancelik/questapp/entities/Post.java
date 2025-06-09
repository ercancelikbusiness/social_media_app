package com.ercancelik.questapp.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="post")
@Data

//not: örneğin postmanda post oluşturmak için post isteği yapcaz  ( controllerda postmapping ) json body sine burdaki id user title fln girceksin

public class Post {
	
	@Id
	Long id;
	@ManyToOne(fetch = FetchType.LAZY)   // postları atan bir user olur yani çok post bir user'a ait
	@JoinColumn(name="user_id", nullable = false)   //veritabanı camelCase kodunu örn userId'yi user_id  olarak görüntüler demiştik bu ek bilgiyi hatırlayalım
													// Burada post, user_id sütunu ile User tablosuna bağlanır.  ve user_id column u oluşturulur ama istersek @Column(name = "userName") yapıp veritabanındada öyle gösterebilirdik
													//aynı zamanda foreign key'imiz user_id dir
													//Bu user_id kolonu(sütun)  bu entitynin foreign key'i idi, user tablosundaki id alanına bağlanır. çünkü User sınıfının
													//Çünkü: User entity’sinde @Id ile tanımlanmış primary key 'id' alanıdır. bu sınıf User'a @ManyToOne ilişkisiyle bağlıysa bu olur
	                                                //Yani, JPA user_id kolonunu, User.id'ye bağlar.. User.id ye bağlanır demek: User sınıfındaki id ye bağlanır

													//column: veritabanındaki sutunu temsil eder ve boş olamaz
	//ayrıca üstteki kod ile user tablosundaki id sütununa bağlanacak. Çünkü User sınıfının @Id alanı bu.
													
	@OnDelete(action = OnDeleteAction.CASCADE)//bir user silindiğinde onun tüm postları silinmeli
	
	@JsonIgnore
	User user;   // postları atan bir user ise onu tanımlarız...
	             // JPA  şunu der: "Ha, burada bir User nesnesi var. O zaman bu entity ile bir ilişki kurulacak demektir."
	
	//yani @ManyToOne → Bu bir "çoktan-bire" ilişkidir. Post sınıfındaki bir user, bir User nesnesine bağlıdır.
	
	//not: User user; Şu satır, Post sınıfındaki bir alanı (field) temsil ediyor. Bu alanın adı user. Türü ise User entity’si.
	//not: @JoinColumn(name="user_id") Bu şu anlama gelir: "Post tablosunda user adlı alan, veritabanında user_id adında bir sütunla temsil edilecek."
	
	
	String title;
	@Lob
	@Column(columnDefinition = "text")
	String text;

}
