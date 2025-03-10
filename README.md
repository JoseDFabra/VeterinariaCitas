# 🐱🐶 Gestión de Citas Veterinarias (Java Swing & JDBC)

## ✨ Descripción del Proyecto

Este proyecto es una aplicación de escritorio para la gestión de citas en una clínica veterinaria. Permite registrar y administrar las citas de mascotas con sus veterinarios, llevando un control de la información de los dueños, las mascotas y los detalles de cada cita. 

- ✨ **Interfaz Gráfica con Java Swing**
- 🔐 **Conexión a SQL Server con JDBC**
- ⚖️ **CRUD completo (Crear, Leer, Actualizar, Eliminar citas)**
- 🔎 **Búsqueda de citas por documento del dueño**

---

## ⚙ Requisitos

Antes de ejecutar el proyecto, asegúrate de contar con los siguientes requisitos instalados y configurados:

- **Java Development Kit (JDK) 17**
- **Apache Maven (ultima versión)**
- **Microsoft SQL Server** (2019 o superior)
- **Visual Studio Code** (opcional, pero recomendado)

---

## 💡 Configuración de la Base de Datos

1. **Crear una base de datos en SQL Server** con el siguiente script:

```sql
CREATE DATABASE VeterinariaDB;
GO

USE VeterinariaDB;
GO

CREATE TABLE Citas (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre_dueño NVARCHAR(100),
    documento_dueño NVARCHAR(20),
    nombre_mascota NVARCHAR(50),
    tipo_animal NVARCHAR(50),
    raza NVARCHAR(50),
    edad INT,
    peso FLOAT,
    motivo NVARCHAR(255),
    fecha_hora DATETIME DEFAULT GETDATE(),
    fecha_recogida DATETIME NULL
);
```

2. **Configurar credenciales** en el archivo `ConexionSQL.java` (ubicado en `src/main/java/com/veterinaria/database`). Modifica los valores según tu configuración:

```java
private String url = "jdbc:sqlserver://localhost:1433;databaseName=VeterinariaDB";
private String user = "sa";  // Cambia por tu usuario de SQL Server
private String password = "tu_contraseña";  // Cambia por tu contraseña
```

---

## ✨ Instalación y Ejecución

### 👤 1. Clonar el repositorio
```bash
git clone git clone https://github.com/usuario/nombre-del-repositorio.git
cd vetcare
```

### 📝 2. Compilar el proyecto con Maven
```bash
mvn clean compile
```

### 🔄 3. Ejecutar la aplicación
#### Opcion 1: Desde Maven
```bash
mvn exec:java -Dexec.mainClass="com.veterinaria.Main"
```

#### Opcion 2: Generar y ejecutar el JAR
```bash
mvn clean package
java -jar target/vetcare-1.0.jar
```

---

## 🔎 Uso de la aplicación

1. **Agregar Cita**: Presiona el botón `Agregar Cita` y llena los datos.
2. **Buscar Cita por Documento**: Ingresa el documento del dueño en el campo de búsqueda y presiona `Buscar`.
3. **Editar Cita**: Presiona el botón `Editar` en la cita correspondiente.
4. **Eliminar Cita**: Presiona `Eliminar` y confirma la acción.
5. **Marcar como Recogida**: Presiona `Marcar como recogida` cuando la mascota haya sido atendida.

---

## 📝 Licencia
Este proyecto es de código abierto bajo la licencia MIT.

---

## 🛠️ Soporte
Si tienes problemas para ejecutar el proyecto, abre un **issue** en GitHub o contáctame en mi correo.

---

🚀 **Happy coding!**

