package com.ercancelik.questapp.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

//entity - repos katmanından sonra
//controllerda  mappingleri metodları vb yazarız sonra  bu metodu servicede  auto yaratırız servis jpa(repos) yı çağırcaktır 
//servisi interface olarak kullanmazsan(manager sınıfsız yani şuanki projemiz sonucta restful api yapmıyoruz ama  projemizde  rest api var) 
//ve yazacağın metodun içi jpa nın hazır kodunun dışına çıkıyorsa repos'ada metodu oluşturcaksın

@Entity
@Table(name="comment")
@Data

public class Comment {
	
	@Id   //Bu alan comment tablosunun primary key (birincil anahtar) alanı. Yani her yorumun veritabanında benzersiz bir kimliği var.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch = FetchType.LAZY)     //burayı anlaman için Post entity'sindeki yorum satırlarını oku  burdada en altta anlattım
	@JoinColumn(name="post_id", nullable = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	
	@JsonIgnore
	Post post;   
	 
	
	@ManyToOne(fetch = FetchType.LAZY)    // bu kısımı Post sınıfında yorum olarak anlattım	
	@JoinColumn(name="user_id", nullable = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	
	@JsonIgnore
	User user;   // postları atan bir user ise onu tanımlarız...
	@Lob
	@Column(columnDefinition = "text")
	String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;

}

/*
 * @JoinColumn(name="post_id") ile veritabanındaki comment tablosunda bir post_id sütunu olduğunu ve bunun Post tablosunun
 *  birincil anahtarına işaret ettiğini söylüyor.
 *  nullable = false ile her yorumun mutlaka bir gönderiye ait olması zorunlu.
 *  @OnDelete(action = OnDeleteAction.CASCADE) ile eğer o gönderi (Post) silinirse, ona bağlı tüm yorumlar (Comment) da
 *   otomatik olarak silinir (cascade delete).
 *   fetch = FetchType.LAZY ise bu ilişkili Post nesnesinin, yorum çağrıldığında hemen yüklenmemesini, gerektiğinde (lazy) yüklenmesini sağlar.
 *   @JsonIgnore ise, bu nesne JSON'a çevrilirken (örneğin REST API’de), post alanının görünmemesi için.
 *      post_id → Bu, yorumun hangi gönderiye ait olduğunu belirtir. Bir Comment (yorum), bir Post'a (gönderiye) ait olur.
 *      Çok sayıda yorum, tek bir gönderiye ait olabilir → bu yüzden @ManyToOne.

	user_id → Bu, yorumun hangi kullanıcı tarafından yazıldığını belirtir.
	altta yazdıgım ilk manytoone ile başlayan 5 satırlık Post post; ile biten kod satırlarını açıklıyor ayrıca Post sınıfını oku ki anla
	Bu post_id, Post tablosundaki id sütununa bağlanır (çünkü Post sınıfında @Id Long id; var). post_id ise  bu sınıfın foreign Key'i
	alttaki ikinci manytoonelık 5 satırı User user; ile bitene kadarki kısmı açıklıyor
	comment tablosunda yine bir user_id sütunu oluşur.

	Bu sütun, User tablosundaki id sütununa bağlanır.
	Bağlandığı Alan User.id  Anlamı şudur: User sınıfının id alanına bağlan.”
	Post → 1 kullanıcıya bağlıdır (user_id) [ Post sınıfındaki mantığı açıkladım ]

	Comment → 1 gönderiye bağlıdır (post_id) ve 1 kullanıcıya bağlıdır (user_id)
	
	Uygulamada Örnek (Senaryo):
Ahmet bir gönderi oluşturdu (post). Ayşe o gönderiye bir yorum yaptı (comment).

Post tablosunda:

id: 10, user_id: 1 (Ahmet)

Comment tablosunda:

id: 100, post_id: 10, user_id: 2 (Ayşe)

user_id ile user sınıfının id si temsil ediliyordu o halde bu kişinin id numarası
post_id ise Post sınıfının primary keyini yani id yi temsil ediyordur  id:100 ise comment sınıfının  id si oda primary key idi onuda like'da
kullanırız
*/
