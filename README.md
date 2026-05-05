# 📚 LibraryApp - Kütüphane Yönetim Uygulaması

Bu proje, modern Android geliştirme teknikleri kullanılarak hazırlanmış bir kütüphane yönetim uygulamasıdır. Proje, **Turkcell Geleceği Yazanlar 5.0 (GYGY)** eğitimi kapsamında geliştirilmektedir.

## 📱 Uygulama İçi Ekran Görüntüleri

<p align="center">
  <img src="https://github.com/user-attachments/assets/25ddbec5-0c9f-44e8-bc75-8b9a85386f71" width="20%" alt="Kitaplarım Ekranı" />
  <img src="https://github.com/user-attachments/assets/3998dc78-3234-4612-9abc-0119e336f832" width="20%" alt="Kitap Arama Ekranı" />
  <img src="https://github.com/user-attachments/assets/fe19a189-b82d-4f95-86c2-7c46fb2696fd" width="20%"" alt="Kitap Düzenlenme Ekranı " />
  <img src="https://github.com/user-attachments/assets/6b280e61-9cbd-46eb-816c-7cdb21fc945f" width="20%"" alt="Kitap Düzenlenmiş Hali " />

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

* **`data/`**: Veri modelleri (`Book`, `Profile`), Repository sınıfları (`BookRepository`, `AuthRepository`) ve Supabase istemci yapılandırması bu katmanda yer alır.
* **`ui/`**: Jetpack Compose ekranları (`HomeScreen`, `LoginScreen`, `RegisterScreen`), bileşenler (`BookCard`) ve ekranlar arası geçişleri yöneten `NavGraph` burada bulunur.
* **`viewmodel/`**: UI katmanı ile veri katmanı arasındaki iletişimi sağlayan, iş mantığının yönetildiği bölümdür.

## 🚀 Özellikler

* **Kitap Yönetimi:** Kitap listeleme, detay görüntüleme, kitap ekleme ve güncelleme ve silme işlemleri.
* **Kimlik Doğrulama (Auth):** Supabase Auth kullanarak kullanıcı kayıt ve giriş sistemleri.
* **Gerçek Zamanlı Veri:** Supabase ile veritabanı üzerinden anlık veri senkronizasyonu.

