import { crearBarraNavegacion } from '../components/navbar.js';
import { reservaAPI } from '../api/reservaAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const reservaId = urlParams.get('id');

    if (!reservaId) {
        alert('Error: No se ha proporcionado un ID de reserva válido.');
        window.location.href = 'reservaLista.html';
        return;
    }

    root.innerHTML = `
        <div class="container text-center py-5">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Cargando detalles de la reserva...</span>
            </div>
        </div>
    `;

    try {
        const reserva = await reservaAPI.buscarPorId(reservaId);
        const nombreCliente = `${reserva.cliente.nombre} ${reserva.cliente.apellido}`;
        const emailCliente = reserva.cliente?.email;
        const telefonoCliente = reserva.cliente?.telefono;
        const listaHabitaciones = reserva.habitaciones.map(habitacion => `<span class="badge bg-dark me-1">Habitación ${habitacion.numeroHabitacion}. Tipo: ${habitacion.tipoHabitacion}</span>`).join('');

        let estiloEstado = 'bg-secondary';
        let textoEstado = '';

        if (reserva.estadoReserva === 'COMPLETADA') {
            estiloEstado = 'bg-success';
            textoEstado = 'Confirmada / Pagada';
        } else if (reserva.estadoReserva === 'PENDIENTE') {
            estiloEstado = 'bg-warning text-dark';
            textoEstado = 'Pendiente de Pago';
        } else if (reserva.estadoReserva === 'CANCELADA') {
            estiloEstado = 'bg-danger';
            textoEstado = 'Cancelada';
        }

        const precioFormateado = new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' })
            .format(reserva.precioTotal || 0);

        root.innerHTML = `
            <div class="container mt-4 mb-5" style="max-width: 800px;">
                <div class="mb-3">
                    <a href="reservaLista.html" class="btn btn-sm btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Volver a la lista de reservas
                    </a>
                </div>

                <div class="card shadow-sm border-0">
                    <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center py-3">
                        <h4 class="mb-0">Resumen de Reserva #${reserva.id}</h4>
                        <span class="badge ${estiloEstado} px-3 py-2 fw-bold">${textoEstado}</span>
                    </div>

                    <div class="card-body p-4">
                        <div class="row g-4">
                            
                            <div class="col-md-6 border-end">
                                <h5 class="text-primary border-bottom pb-2 mb-3">
                                    <i class="bi bi-person-fill"></i> Información del Cliente
                                </h5>
                                <p class="mb-2"><strong>Nombre:</strong> ${nombreCliente}</p>
                                <p class="mb-2"><strong>Email:</strong> ${emailCliente}</p>
                                <p class="mb-0"><strong>Teléfono:</strong> ${telefonoCliente}</p>
                            </div>

                            <div class="col-md-6">
                                <h5 class="text-primary border-bottom pb-2 mb-3">
                                    <i class="bi bi-calendar-check-fill"></i> Datos de Estancia
                                </h5>
                                <p class="mb-2"><strong>Fecha Entrada:</strong> ${reserva.fechaEntrada || 'No definida'}</p>
                                <p class="mb-2"><strong>Fecha Salida:</strong> ${reserva.fechaSalida || 'No definida'}</p>
                                <p class="mb-0"><strong>Habitaciones:</strong> ${listaHabitaciones}</p>
                            </div>

                            <div class="col-12 mt-4">
                                <div class="p-3 bg-light rounded d-flex justify-content-between align-items-center">
                                    <div>
                                        <h6 class="mb-0 text-muted fw-bold">Importe Total</h6>
                                    </div>
                                    <div class="text-end">
                                        <span class="display-6 fw-bold text-success">${precioFormateado}</span>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="d-flex justify-content-between align-items-center mt-5 pt-3 border-top">
                            <button id="btnEliminarReserva" class="btn btn-outline-danger">
                                <i class="bi bi-trash"></i>Eliminar Reserva
                            </button>

                            <div>
                                <a href="editarReserva.html?id=${reserva.id}" class="btn btn-warning text-dark fw-bold px-4">
                                    <i class="bi bi-pencil-square"></i> Modificar Datos
                                </a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        `;

        document.getElementById('btnEliminarReserva').addEventListener('click', async () => {
            const seguro = confirm('¿Estás completamente seguro de que deseas eliminar permanentemente esta reserva del sistema? Esta acción no se puede deshacer.');

            if (seguro) {
                try {
                    await reservaAPI.eliminarReserva(reservaId);
                    alert('La reserva ha sido eliminada correctamente.');
                    window.location.href = 'reservaLista.html';
                } catch (error) {
                    console.error('Error al intentar eliminar la reserva:', error);
                    alert('Hubo un error en el servidor y no se pudo eliminar la reserva.');
                }
            }
        });

    } catch (error) {
        alert(`Error al obtener los detallesde la reserva: ${error.message}`);
        root.innerHTML = `
            <div class="container mt-5">
                <div class="alert alert-danger text-center shadow-sm" role="alert">
                    <h4 class="alert-heading">Reserva no encontrada</h4>
                    <p>No se pudo recuperar ninguna reserva con el ID <strong>${reservaId}</strong> o el servidor no responde.</p>
                    <hr>
                    <a href="index.html" class="btn btn-danger">Volver a la Página Principal</a>
                </div>
            </div>
        `;
    }
});