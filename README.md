# Students MVC (JavaFX + SQL Server)

تطبيق تعليمي بسيط يطبّق نمط **MVC** باستخدام **JavaFX** للواجهة، و**JDBC (HikariCP)** للاتصال بـ **SQL Server**.  
الواجهة تسمح بإضافة طالب، البحث بالرقم أو الاسم، عرض القائمة، وحذف الطالب المحدد.

---

## 💡 الهدف
- فصل واضح بين الطبقات:
  - **View**: `StudentsView.fxml` + `StudentsViewController`
  - **Controller (Use Cases)**: `AppController`
  - **Service (قواعد العمل)**: `StudentService`
  - **Repository (JDBC)**: `JdbcStudentRepository` عبر `DataSourceFactory`
- تعلم الأساسيات: JavaFX، JDBC، HikariCP، Maven، واستخدام `.env`.

---

## 🧱 التقنيات
- Java 21 (OpenJDK)
- JavaFX (Controls + FXML)
- Maven
- HikariCP (Connection Pool)
- MS SQL Server
- JUnit 5 + AssertJ للاختبارات
- dotenv-java لقراءة ملف `.env`

---
StudentsView (FXML+Controller)
  └─ ينادي
     AppController (Use Cases)
        └─ يستخدم
           StudentService (قواعد العمل والـValidation)
              └─ يستدعي
                 Repository (JDBC)
                    └─ SQL Server

---

## ✅ المتطلبات
- JDK 21
- Maven 3.9+
- SQL Server شغّال على `localhost:1433` (TCP/IP مفعّل)
- حساب SQL لديه صلاحية على قاعدة البيانات

---

## 🗄️ إنشاء قاعدة البيانات (مرة واحدة)

افتح **SSMS** ونفّذ:

```sql
IF DB_ID('StudentsDB') IS NULL CREATE DATABASE StudentsDB;
GO
USE StudentsDB;
IF OBJECT_ID('dbo.Students','U') IS NULL
  CREATE TABLE dbo.Students (
    Id   INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
  );
GO

-- بيانات تجريبية (اختياري):
INSERT INTO dbo.Students (Name) VALUES (N'Ahmad'), (N'Ihab');
🔐 الإعداد السري (.env)

أنشئ ملف .env في جذر المشروع (نفس مستوى pom.xml) ولا ترفعه للـGit:
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=StudentsDB;encrypt=true;trustServerCertificate=true;
DB_USER=اسم_المستخدم
DB_PASS=كلمة_السر
أضِف .env إلى .gitignore.

▶️ التشغيل

من جذر المشروع:

mvn -q clean compile
mvn -q javafx:run

حقوق النشر (c) 2025 مجموعة CodeHouse (Discord)
