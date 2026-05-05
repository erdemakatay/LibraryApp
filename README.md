# 📚 LibraryApp - Kütüphane Yönetim Uygulaması

Bu proje, modern Android geliştirme teknikleri kullanılarak hazırlanmış bir kütüphane yönetim uygulamasıdır. Proje, **Turkcell Geleceği Yazanlar 5.0 (GYGY)** eğitimi kapsamında geliştirilmektedir.

## 📱 Uygulama İçi Ekran Görüntüleri

<p align="center">
  <img src="https://github.com/user-attachments/assets/2d1ffd29-02cc-45ab-9a2e-90bc8cc8cdb3" width="20%" alt="Giriş Ekranı" />
  <img src="https://github.com/user-attachments/assets/8965b318-dd7b-45ee-87c1-0d6d48411e03" width="20%" alt="Kayıt Ol Ekranı" />
  <img src="https://github.com/user-attachments/assets/6d6f6685-d927-4db5-91ee-470eb27e31b2" width="20%" alt="Kitaplarım Ekranı" />
  <img src="https://github.com/user-attachments/assets/6e49a51e-47e3-403d-890d-c3ece8975d29" width="20%" alt="Kitapları Düzenle Ekranı" />
  <img src="https://github.com/user-attachments/assets/f3353a44-c20b-4da9-b63b-ca9cc3674153" width="20%" alt="Kitapların Düzenlendikten Sonraki Ekran" />
  <img src="https://github.com/user-attachments/assets/48beee0d-5e40-4e8f-8ad9-ef7deefe6766" width="20%" alt="Ödünç Al Ekranı" />
  <img src="https://github.com/user-attachments/assets/8e6f7475-dba8-410d-8043-09758c043717" width="20%" alt="Kiralamalarım Ekranı" />
</p>

## 🛠 Kullanılan Teknolojiler ve Mimari

Uygulama, sürdürülebilir ve test edilebilir bir yapı sunmak için **MVVM (Model-View-ViewModel)** mimarisi üzerine inşa edilmiştir.

* **Dil:** Kotlin
* **UI Framework:** Jetpack Compose 
* **Backend & Veritabanı:** Supabase
* **Mimari Yapı:** MVVM + Repository Pattern
* **Bağımlılık Yönetimi:** Kotlin DSL (.gradle.kts)

## 📂 Proje Yapısı

Proje klasör yapısı, sorumlulukların ayrılması prensibine göre düzenlenmiştir:

* **`data/`**: Veri modelleri (`Book`, `Profile`,`BorrowRecord`), Repository sınıfları (`BookRepository`, `AuthRepository`) ve Supabase istemci yapılandırması bu katmanda yer alır.
* **`ui/`**: Jetpack Compose ekranları (`HomeScreen`, `LoginScreen`, `RegisterScreen`,`BorrowScreen`), bileşenler (`BookCard`) ve ekranlar arası geçişleri yöneten `NavGraph` burada bulunur.
* **`viewmodel/`**: UI katmanı ile veri katmanı arasındaki iletişimi sağlayan, iş mantığının yönetildiği bölümdür.

## 🚀 Özellikler

* **Kitap Yönetimi:** Kitap listeleme, detay görüntüleme, kitap ekleme ve güncelleme ve silme işlemleri.
* **Kimlik Doğrulama (Auth):** Supabase Auth kullanarak kullanıcı kayıt ve giriş sistemleri.
* **Gerçek Zamanlı Veri:** Supabase ile veritabanı üzerinden anlık veri senkronizasyonu.
* **Dinamik Stok Kontrolü:** Kitap kartlarında availableCopies değerine göre otomatik buton yönetimi (Ödünç Al / Stokta Yok).
* **Akıllı Kiralama Sistemi**: BorrowRecord tablosu üzerinden maksimum 5 günlük kiralama süresi hesaplama ve kayıt işlemi.



