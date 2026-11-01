export function crearElementoReserva(reserva) {
    let colorEstado = 'bg-secondary';
    if (reserva.estadoReserva === 'COMPLETADA') colorEstado = 'bg-success';
    if (reserva.estadoReserva === 'CANCELADA') colorEstado = 'bg-danger';
    if (reserva.estadoReserva === 'PENDIENTE') colorEstado = 'bg-warning text-dark';

    const nombreCliente = reserva.cliente ? `${reserva.cliente.nombre} ${reserva.cliente.apellido}` : 'Cliente desconocido';

    return `
        <a href="reservaDetalle.html?id=${reserva.id}" class="card mb-3 text-decoration-none text-dark shadow-sm border-start border-4 border-primary">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <h5 class="card-title mb-0">Reserva #${reserva.id}</h5>
                    <span class="badge ${colorEstado} fs-6">${reserva.estadoReserva}</span>
                </div>
                
                <div class="row mt-3 border-top pt-3">
                    <div class="col-md-7">
                        <p class="mb-1"><strong>Cliente:</strong> ${nombreCliente}</p>
                        <p class="mb-1 text-success"><strong><i class="bi bi-box-arrow-in-right"></i> Entrada:</strong> ${reserva.fechaEntrada}</p>
                        <p class="mb-1 text-danger"><strong><i class="bi bi-box-arrow-right"></i> Salida:</strong> ${reserva.fechaSalida}</p>
                    </div>
                    <div class="col-md-5 text-end d-flex flex-column justify-content-between">
                        <p class="mb-1 text-muted small">Habitaciones: ${reserva.numeroHabitaciones}</p>
                        <h4 class="text-primary mt-2 mb-0">${(reserva.precioTotal || 0).toFixed(2)} €</h4>
                    </div>
                </div>
            </div>
        </a>`;
}