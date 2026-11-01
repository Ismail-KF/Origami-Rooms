import { crearBarraNavegacion } from '../components/navbar.js';
import { reservaAPI } from '../api/reservaAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4 mb-5">
            <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
                <div>
                    <h2 class="fw-bold mb-0">Gestión de Reservas</h2>
                </div>
            <a href="crearReserva.html" class="btn btn-primary">Crear Reserva</a>
            </div>

            <div class="card shadow-sm border-0">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="col" class="ps-4 py-3">ID</th>
                                    <th scope="col" class="py-3">Cliente</th>
                                    <th scope="col" class="py-3">Entrada (Check-in)</th>
                                    <th scope="col" class="py-3">Salida (Check-out)</th>
                                    <th scope="col" class="py-3">Estado</th>
                                    <th scope="col" class="py-3">Importe Total</th>
                                    <th scope="col" class="text-end pe-4 py-3">Acciones</th>
                                </tr>
                            </thead>
                            <tbody id="tablaCuerpoReservas">
                                <tr>
                                    <td colspan="7" class="text-center py-5">
                                        <div class="spinner-border text-primary" role="status">
                                            <span class="visually-hidden">Cargando reservas...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    `;

    const tbody = document.getElementById('tablaCuerpoReservas');

    try {
        const reservas = await reservaAPI.mostrarTodas();

        tbody.innerHTML = '';

        if (reservas && reservas.length > 0) {
            reservas.forEach(reserva => {
                const nombreCliente = `${reserva.cliente.nombre} ${reserva.cliente.apellido}`;

                let estiloEstado = 'bg-secondary';
                let textoEstado = '';

                if (reserva.estadoReserva === 'COMPLETADA') {
                    estiloEstado = 'bg-success-subtle text-success border border-success-subtle';
                    textoEstado = 'Completada';
                } else if (reserva.estadoReserva === 'PENDIENTE') {
                    estiloEstado = 'bg-warning-subtle text-warning-emphasis border border-warning-subtle';
                    textoEstado = 'Pendiente de Pago';
                } else if (reserva.estadoReserva === 'CANCELADA') {
                    estiloEstado = 'bg-danger-subtle text-danger border border-danger-subtle';
                    textoEstado = 'Cancelada';
                }

                const precioFormateado = new Intl.NumberFormat('es-ES', {
                    style: 'currency',
                    currency: 'EUR'
                }).format(reserva.precioTotal || 0);

                tbody.innerHTML += `
                    <tr>
                        <td class="fw-bold text-secondary ps-4">#${reserva.id}</td>
                        <td class="fw-semibold">${nombreCliente}</td>
                        <td>${reserva.fechaEntrada || '---'}</td>
                        <td>${reserva.fechaSalida || '---'}</td>
                        <td>
                            <span class="badge ${estiloEstado} px-2.5 py-1.5 fw-semibold rounded">
                                ${textoEstado}
                            </span>
                        </td>
                        <td class="fw-bold text-success">${precioFormateado}</td>
                        <td class="text-end pe-4">
                            <a href="reservaDetalle.html?id=${reserva.id}" class="btn btn-sm btn-dark fw-bold me-1">
                                <i class="bi bi-eye"></i> Ver
                            </a>
                            <a href="editarReserva.html?id=${reserva.id}" class="btn btn-sm btn-outline-warning text-dark fw-bold">
                                <i class="bi bi-pencil"></i>Modificar
                            </a>
                        </td>
                    </tr>
                `;
            });
        } else {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" class="text-center text-muted py-5">
                        <h5 class="mb-0">No se han encontrado registros de reservas actualmente.</h5>
                    </td>
                </tr>
            `;
        }

    } catch (error) {
        alert(`Error al recuperar la lista de reservas: ${error.message}`);
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center py-5">
                    <div class="alert alert-danger d-inline-block shadow-sm mb-0" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        No se pudo establecer conexión con el servidor. Verifica que tu backend esté encendido.
                    </div>
                </td>
            </tr>
        `;
    }
});