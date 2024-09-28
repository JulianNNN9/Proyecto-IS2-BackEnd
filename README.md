# Proyecto-IS2-BackEnd

# Proyecto de Sistema de Citas para Peluquería

## Descripción

Este proyecto tiene como objetivo implementar un sistema de gestión de citas para una peluquería. La base de datos contiene información sobre estilistas, servicios y citas de clientes.

## Requisitos

- **MySQL**: Asegúrate de tener MySQL instalado en tu máquina. Puedes descargarlo desde [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).

## Instrucciones de Creación de la Base de Datos

### 1. Crear la base de datos

Abre tu terminal o consola de MySQL y ejecuta el siguiente comando para crear la base de datos `peluqueria_db`:

```sql
CREATE DATABASE peluqueria_db;

USE peluqueria_db;

CREATE TABLE Estilista (
    ID_Estilista INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Estilista VARCHAR(100) NOT NULL
);

CREATE TABLE Servicio (
    ID_Servicio INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Servicio VARCHAR(100) NOT NULL,
    Precio DECIMAL(10, 2) NOT NULL -- Precio del servicio
);

CREATE TABLE Cita (
    ID_Cita INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Completo VARCHAR(150) NOT NULL,
    Numero_Telefono VARCHAR(15) NOT NULL,
    Correo_Electronico VARCHAR(100), -- Opcional
    Fecha_Cita DATE NOT NULL,
    Hora_Cita TIME NOT NULL,
    Preferencia_Estilista INT, -- Relación opcional con Estilista
    Notas_Adicionales TEXT,
    FOREIGN KEY (Preferencia_Estilista) REFERENCES Estilista(ID_Estilista)
);

CREATE TABLE Cita_Servicio (
    ID_Cita INT,
    ID_Servicio INT,
    PRIMARY KEY (ID_Cita, ID_Servicio),
    FOREIGN KEY (ID_Cita) REFERENCES Cita(ID_Cita),
    FOREIGN KEY (ID_Servicio) REFERENCES Servicio(ID_Servicio)
);

INSERT INTO Estilista (Nombre_Estilista) VALUES
('Sofía Martínez'),
('Diego Pérez'),
('Valentina Gómez'),
('Carlos Rodríguez'),
('Lucía Torres');

INSERT INTO Servicio (Nombre_Servicio, Precio) VALUES
('Corte de cabello', 50000.00),  -- Precio en pesos colombianos
('Tinte de cabello', 120000.00),
('Peinado especial', 80000.00),
('Lavado y secado', 30000.00),
('Manicure', 25000.00);
