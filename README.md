# ProyectoJava
Sistema base de facturación para una empresa de maquillaje.  
Incluye modelos `Cliente`, `Producto`, `Venta` y `VentaItem` (N–M con atributos), persistidos con **Hibernate/JPA** en **MySQL**.  
**No** expone controladores; el acceso a datos se realiza vía **DAO con `EntityManager`** y se pueblan datos usando **`CommandLineRunner`** al iniciar la app.

---

## 🚀 Requisitos

- **Java 21** (JDK 21)
- **Maven** 3.9+
- **Docker** (para MySQL)
- Puerto **3306** libre en tu máquina (o ajusta el mapping)

---

## 🐬 Levantar MySQL con Docker

Ejecuta en tu terminal:

```bash
docker run -d \
  --name mysql8 \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=Sara \
  -e MYSQL_DATABASE=makeup_billing \
  mysql:8.4
