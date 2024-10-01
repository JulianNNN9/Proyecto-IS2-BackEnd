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
    ID_Estilista BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Estilista VARCHAR(100) NOT NULL
);

CREATE TABLE Servicio (
    ID_Servicio BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Servicio VARCHAR(100) NOT NULL,
    Precio DECIMAL(10, 2) NOT NULL -- Precio del servicio
);

CREATE TABLE Cita (
    ID_Cita BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    ID_Cita BIGINT,
    ID_Servicio BIGINT,
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

```

# Instrucciones de Migración de Base de Datos

# No usar Aun, es para prueba mia unicamente.

A continuación se detallan las instrucciones para modificar la estructura de las tablas `Cita`, `Cita_Servicio`, y `Servicio` en la base de datos.

```sql
ALTER TABLE Cita_Servicio DROP FOREIGN KEY cita_servicio_ibfk_1;
ALTER TABLE Cita_Servicio DROP FOREIGN KEY cita_servicio_ibfk_2;

ALTER TABLE Cita MODIFY COLUMN ID_Cita BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE Cita_Servicio MODIFY COLUMN ID_Cita BIGINT;
ALTER TABLE Cita_Servicio MODIFY COLUMN ID_Servicio BIGINT;

ALTER TABLE Cita_Servicio
ADD CONSTRAINT fk_cita FOREIGN KEY (ID_Cita) REFERENCES Cita(ID_Cita);

ALTER TABLE Cita_Servicio
ADD CONSTRAINT fk_servicio FOREIGN KEY (ID_Servicio) REFERENCES Servicio(ID_Servicio);

ALTER TABLE Cita_Servicio DROP FOREIGN KEY fk_servicio;

ALTER TABLE Cita_Servicio MODIFY COLUMN ID_Servicio BIGINT NOT NULL;
ALTER TABLE Servicio MODIFY COLUMN ID_Servicio BIGINT NOT NULL;

ALTER TABLE Cita_Servicio
ADD CONSTRAINT fk_servicio FOREIGN KEY (ID_Servicio) REFERENCES Servicio(ID_Servicio);

SELECT CONSTRAINT_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_NAME = 'Cita';

ALTER TABLE Cita DROP FOREIGN KEY nombre_correcto_de_la_clave;

ALTER TABLE Cita MODIFY COLUMN preferencia_estilista BIGINT;
ALTER TABLE Estilista MODIFY COLUMN ID_Estilista BIGINT NOT NULL;

ALTER TABLE Cita
ADD CONSTRAINT fk_estilista FOREIGN KEY (preferencia_estilista) REFERENCES Estilista(ID_Estilista);
