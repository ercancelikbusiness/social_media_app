package com.ercancelik.questapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//entity - repos katmanından sonra
// controllerda  mappingleri metodları vb yazarız sonra  bu metodu servicede  auto yaratırız servis jpa(repos) yı çağırcaktır 
//servisi interface olarak kullanmazsan(manager sınıfsız) ve yazacağın metod içi jpa nın hazır kodunun dışına çıkıyorsa 
//repos'ada metodu oluşturcaksın



@Entity   // veritabanındaki bir tabloya maplenceğini söylüyor
@Table(name="user")  //bu anotasyonları import ederken javax yerine jakarta kullanırız çünkü pom.xml de versiyon 3 lü olanlardayız yani
                     //spring boot 3x kullanıyoruz not: security bağımlılığı varsa postmanda istekler engellenebilir
                     //ayrıca Table veritabanındaki ana tabloyu ifade eder sütün ve satırların içinde oldugu ana tabloyu
					 // veritabanında user adlı bir tablo olcak içindeki sütünlarıda  aşağıda belirteceğiz örn String password;  demek password diye bir sütunu temsil eder
					 // yada sutunları columnName joinColumn gibi ifadelerlede belirtiyoruz zamanla oturucak
					 

@Data  //lombok'dan gelir  yani get set constructor gibi angarya kodlar otomatik hallolur


public class User {
	
	@Id               // id veritabanındaki tablonun birincil anahtarıdır. demektir benzersiz tanımlanacak demektir
	Long id;
	String userName;  // bu bir sütün  veritabanı sütünlarında javadaki camelCase yazımlar user_name şeklinde gözükür
	String password;
	
	
	

}



/* 
 * Bu entity ile JPA aşağıdaki gibi bir tablo oluşturur (örnek olarak):
 * CREATE TABLE user (
  id BIGINT PRIMARY KEY,
  user_name VARCHAR(255),
  password VARCHAR(255)
);*/
 