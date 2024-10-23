const mysql = require('mysql2');

// Configuración de la conexión a MySQL
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'admin',
    database: 'bd_peluqueria'
});

// Conectar a la base de datos
connection.connect((err) => {
    if (err) {
        console.error('Error al conectar a la base de datos:', err);
        return;
    }
    console.log('Conexión exitosa a la base de datos');

    // Inserciones en la tabla Administrador
    const administradores = [
        ['Carlos Perez', 'carlos@correo.com', 'hashed_password_1'],
        ['Laura Gomez', 'laura@correo.com', 'hashed_password_2'],
        ['José Martinez', 'jose@correo.com', 'hashed_password_3'],
        ['Ana Ruiz', 'ana@correo.com', 'hashed_password_4'],
        ['David Torres', 'david@correo.com', 'hashed_password_5']
    ];

    const sqlAdministrador = 'INSERT INTO Administrador (nombre, correo, contrasena) VALUES ?';
    connection.query(sqlAdministrador, [administradores], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en Administrador completadas:', result.affectedRows);
    });

    // Inserciones en la tabla Cliente
    const clientes = [
        ['Cliente 1', 'cliente1@correo.com', 'hashed_password_1', '3011111111'],
        ['Cliente 2', 'cliente2@correo.com', 'hashed_password_2', '3012222222'],
        ['Cliente 3', 'cliente3@correo.com', 'hashed_password_3', '3013333333'],
        ['Cliente 4', 'cliente4@correo.com', 'hashed_password_4', '3014444444'],
        ['Cliente 5', 'cliente5@correo.com', 'hashed_password_5', '3015555555']
    ];

    const sqlCliente = 'INSERT INTO Cliente (nombre, correo, contrasena, telefono) VALUES ?';
    connection.query(sqlCliente, [clientes], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en Cliente completadas:', result.affectedRows);
    });

    // Inserciones en la tabla Producto
    const productos = [
        ['Producto 1', 5000.00, 10, 'Marca A'],
        ['Producto 2', 15000.00, 5, 'Marca B'],
        ['Producto 3', 2000.00, 20, 'Marca C'],
        ['Producto 4', 8000.00, 12, 'Marca D'],
        ['Producto 5', 30000.00, 8, 'Marca E']
    ];

    const sqlProducto = 'INSERT INTO Producto (nombre, precio, stock, marca) VALUES ?';
    connection.query(sqlProducto, [productos], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en Producto completadas:', result.affectedRows);
    });

    // Inserciones en la tabla Servicio
    const servicios = [
        ['Corte de cabello', 20000.00, '30 minutos'],
        ['Peinado', 15000.00, '20 minutos'],
        ['Manicure', 10000.00, '45 minutos'],
        ['Pedicure', 12000.00, '50 minutos'],
        ['Maquillaje', 25000.00, '60 minutos']
    ];

    const sqlServicio = 'INSERT INTO Servicio (nombre, precio, duracion) VALUES ?';
    connection.query(sqlServicio, [servicios], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en Servicio completadas:', result.affectedRows);
    });

        // Inserciones en la tabla Estilista
        const estilistas = [
            ['Correo1','Estilista 1'],
            ['Correo2','Estilista 2'],
            ['Correo3','Estilista 3'],
            ['Correo4','Estilista 4'],
            ['Correo5','Estilista 5']
        ];
    
        const sqlEstilista = 'INSERT INTO Estilista (correo, nombre) VALUES ?';
        connection.query(sqlEstilista, [estilistas], (err, result) => {
            if (err) throw err;
            console.log('Inserciones en Estilista completadas:', result.affectedRows);
        });

    // Inserciones en la tabla Cita
    const citas = [
        ['2024-10-04 10:00:00', 50000.00, true, 'CONFIRMADA', 5000.00, 'Comentario 1', 1, 1, 'Direccion 1'],
        ['2024-10-05 11:00:00', 45000.00, true, 'PENDIENTE', 0.00, 'Comentario 2', 2, 2, 'Direccion 2']
    ];

    const sqlCita = 'INSERT INTO Cita (fecha, totalPago, confirmacion, estadoCita, propina, comentario, cliente_id, estilista_id, direccion) VALUES ?';
    connection.query(sqlCita, [citas], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en Cita completadas:', result.affectedRows);
    });

    // Inserciones en la tabla DetalleServicioCita
    const detalleServicios = [
        [1, 1], // Cita 1, Servicio 1
        [1, 2], // Cita 1, Servicio 2
        [2, 3]  // Cita 2, Servicio 3
    ];

    const sqlDetalleServicio = 'INSERT INTO DetalleServicioCita (cita_id, servicio_id) VALUES ?';
    connection.query(sqlDetalleServicio, [detalleServicios], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en DetalleServicioCita completadas:', result.affectedRows);
    });

    // Inserciones en la tabla DetalleProductoCita
    const detalleProductos = [
        [1, 1, 2, 5000.00], // Cita 1, Producto 1, Cantidad 2, Precio 5000.00
        [1, 2, 1, 15000.00], // Cita 1, Producto 2, Cantidad 1, Precio 15000.00
        [2, 3, 3, 2000.00]  // Cita 2, Producto 3, Cantidad 3, Precio 2000.00
    ];

    const sqlDetalleProducto = 'INSERT INTO DetalleProductoCita (cita_id, producto_id, cantidad, precio) VALUES ?';
    connection.query(sqlDetalleProducto, [detalleProductos], (err, result) => {
        if (err) throw err;
        console.log('Inserciones en DetalleProductoCita completadas:', result.affectedRows);
    });

    // Cierra la conexión
    connection.end();
});