import { crearBarraNavegacion } from '../components/navbar.js';
import { clienteAPI } from '../api/clienteAPI.js';
import { reservaAPI } from '../api/reservaAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const root = document.getElementById('root');
    const parametros = new URLSearchParams(window.location.search);
    const idCliente = parametros.get('id');

    if (!idCliente) {
        root.innerHTML = `<div class="alert alert-danger m-4">Error: No se ha proporcionado un ID de cliente válido.</div>`;
        return;
    }

    try {
        const cliente = await clienteAPI.buscarPorId(idCliente);

        let reservasHtml = '';
        try {
            const reservas = await reservaAPI.buscarReservas(idCliente);

            if (reservas && reservas.length > 0) {
                reservasHtml = reservas.map(reserva => `
                    <a href="reservaDetalle.html?id=${reserva.id}" class="list-group-item list-group-item-action flex-column align-items-start">
                    <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                        <div>
                            <strong>Reserva #${reserva.id}</strong> 
                            <span class="badge ${reserva.estadoReserva === 'COMPLETADA' ? 'bg-success' : 'bg-warning text-dark'} ms-2">
                                ${reserva.estadoReserva}
                            </span>
                            <div class="small text-muted mt-1">
                                Entrada: ${reserva.fechaEntrada} | Salida: ${reserva.fechaSalida}
                            </div>
                        </div>
                        <span class="text-primary fw-bold fs-5">${reserva.precioTotal.toFixed(2)} €</span>
                    </div>
                    </a>
                `).join('');
            } else {
                reservasHtml = `<p class="text-muted m-3 text-center">Este cliente no tiene reservas registradas en el sistema.</p>`;
            }
        } catch (errorReservas) {
            console.error("Error al cargar las reservas:", errorReservas);
            reservasHtml = `<p class="text-warning m-3 text-center">No se pudieron cargar las reservas del cliente.</p>`;
        }

        const iniciales = (cliente.nombre.charAt(0) + cliente.apellido.charAt(0)).toUpperCase();

        root.innerHTML = `
            <div class="container mt-4">
                <div class="mb-3">
                    <a href="clienteLista.html" class="btn btn-outline-secondary btn-sm">
                        Volver a la lista
                    </a>
                </div>

                <div class="row">
                    <div class="col-md-5 mb-4">
                        <div class="card shadow-sm border-0">
                            <div class="card-body text-center border-bottom bg-light py-4">
                                <div class="bg-primary text-white rounded-circle d-flex justify-content-center align-items-center mx-auto mb-3 shadow" style="width: 80px; height: 80px; font-size: 2rem;">
                                    ${iniciales}
                                </div>
                                <h3 class="card-title mb-1">${cliente.nombre} ${cliente.apellido}</h3>
                                <span class="badge bg-dark">ID Cliente: #${cliente.id}</span>
                            </div>
                            <div class="card-body">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item px-0">
                                        <strong>Correo Electrónico:</strong>
                                        <div class="text-muted">${cliente.email}</div>
                                    </li>
                                    <li class="list-group-item px-0">
                                        <strong>Teléfono:</strong>
                                        <div class="text-muted">${cliente.telefono}</div>
                                    </li>
                                    <li class="list-group-item px-0">
                                        <strong>Pasaporte:</strong>
                                        <div class="text-muted">${cliente.pasaporte}</div>
                                    </li>
                                    <li class="list-group-item px-0">
                                        <strong>Nacionalidad:</strong>
                                        <div class="text-muted">${cliente.nacionalidad}</div>
                                    </li>
                                </ul>
                                
                                <div class="d-grid gap-2 mt-4">
                                    <button id="btn-eliminar" class="btn btn-outline-danger">
                                        Eliminar Cliente
                                    </button>
                                    <a href="editarCliente.html?id=${cliente.id}" class="btn btn-warning">Editar Datos</a>

                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-7">
                        <div class="card shadow-sm border-0">
                            <div class="card-header bg-dark text-white py-3">
                                <h5 class="mb-0">Historial de Reservas</h5>
                            </div>
                            <div class="list-group list-group-flush">
                                ${reservasHtml}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;

        document.getElementById('btn-eliminar').addEventListener('click', async () => {
            const confirmar = confirm(`¿Estás completamente seguro de eliminar a ${cliente.nombre} ${cliente.apellido}?\nEsta acción no se puede deshacer.`);
            if (confirmar) {
                try {
                    await clienteAPI.eliminarCliente(cliente.id);
                    alert('Cliente eliminado con éxito.');
                    window.location.href = 'clienteLista.html';
                } catch (error) {
                    alert('Error: No se pudo eliminar al cliente. Comprueba si tiene reservas activas.');
                    console.error(error);
                }
            }
        });

    } catch (error) {
        alert(`Error al obtener los detalles del cliente: ${error.message}`);
        root.innerHTML = `
            <div class="container mt-4">
                <div class="alert alert-danger shadow-sm">
                    <h4>Error de comunicación</h4>
                    <p>No se pudo localizar o cargar la información del cliente con ID: <strong>${idCliente}</strong>.</p>
                    <a href="clienteLista.html" class="btn btn-danger mt-2">Volver a la lista</a>
                </div>
            </div>
        `;
    }
});