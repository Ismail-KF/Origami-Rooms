export function crearElementoHabitacion(habitacion) {
    return `
        <a href="habitacionDetalle.html?id=${habitacion.id}" class="card text-decoration-none text-dark mb-3 shadow-sm">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <h5 class="card-title text-primary">${habitacion.numeroHabitacion}</h5>
                    <span class="badge bg-primary fs-6">${habitacion.precioPorNoche} € / noche</span>
                </div>
                
                <h6 class="card-subtitle mb-2 text-muted mt-2">${habitacion.tipoHabitacion}</h6>
                
                <p class="card-text mt-3">
                    <strong>Capacidad:</strong> ${habitacion.capacidad} persona(s)
                </p>
                
                ${habitacion.establecimiento ? `
                    <div class="mt-3 border-top pt-2">
                        <small class="text-muted">Perteneciente a:</small><br>
                        <strong>${habitacion.establecimiento.nombre}</strong> 
                        <span class="text-muted">(${habitacion.establecimiento.region})</span>
                    </div>
                ` : ''}
            </div>
        </a>`;
}