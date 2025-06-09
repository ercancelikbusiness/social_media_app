package com.ercancelik.questapp.requests;

import lombok.Data;

@Data
public class PostUpdateRequest {
	
	// kısaca body'deki şablonda bu iki data  doldurulup gönderilcek gereksiz datalar olmasın diye bu sınıfı açtıkki bu datalarla
	// işlem yapacağımız zaman bu sınıfı kullanırız aslında gereklilik gereksizlikten ziyade kullanılacak dataları belirleyebiliyoruz
	
		String title;
		String text;
	
	

}
