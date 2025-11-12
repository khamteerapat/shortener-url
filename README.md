# shortener-url

## การติดตั้งและรันโปรแกรม Shorten URL
### 1. ติดตั้งเครื่องมือที่จำเป็น (Install Required Tools)

ก่อนเริ่มต้น โปรดตรวจสอบว่าเครื่องของคุณมีเครื่องมือต่อไปนี้แล้ว

- [Docker](https://www.docker.com/products/docker-desktop/)

- [Docker Compose](https://docs.docker.com/compose/install/)

- [Git](https://git-scm.com/downloads)

ตรวจสอบเวอร์ชันด้วยคำสั่ง:
```
docker --version
docker compose version
git --version
```

***

### 2. การ Clone Project
เปิด Command Prompt (Windows) หรือ Terminal (Mac/Linux) แล้วรันคำสั่ง:
```
git clone https://github.com/khamteerapat/shortener-url.git
```
หลังจาก Clone เสร็จ ให้เข้าไปยังโฟลเดอร์โปรเจ็กต์:
```
cd ./<path>/shortener-url
```
***
### 3. การรันโปรแกรมด้วย Docker Compose
ภายในโฟลเดอร์โปรเจ็กต์ รันคำสั่ง:
```
docker compose up -d
```
เมื่อต้องการหยุดโปรแกรม:
```
docker compose down
```
** หากใช้เป็น Docker Desktop สามารถ monitor สถานะและสั่งหยุด container ได้ผ่าน GUI **
***
### 4. ทดสอบการทำงาน
หลังจากรันสำเร็จ สามารถไปสู่เนื้อหาต่อไปของการใช้งานโปรเจ็กต์ได้เลย

***

## วิธีการใช้งาน Postman Collection สำหรับทดสอบและเรียกใช้งาน API ของโปรเจ็กต์ Shorten URL
### เอกสาร

ลิงก์เอกสารออนไลน์
[Postman Documentation - Shorten URL API](https://documenter.getpostman.com/view/14101300/2sB3Wttew2])

หรือ

Postman Collections ได้ที่ path ดังกล่าวใน repository
/sources/ShortenURL.postman_collection.json

***

### วิธีนำเข้า Postman Collection

1. เปิดโปรแกรม Postman.
2. ไปที่เมนู File → Import.
3. เลือกแท็บ File แล้วอัปโหลดไฟล์ ShortenURL.postman_collection.json.
4. เมื่อ Import เสร็จ จะเห็น Collection ชื่อ Shorten URL API.

ภายในจะมีตัวอย่าง request เช่น
- POST /api/shorten → ใช้สำหรับสร้าง short URL
- GET /api/r/{shortCode} → ใช้สำหรับ redirect ไปยัง URL จริง
- GET /api/urls→ ใช้ดูข้อมูล url ที่เคยสร้าง