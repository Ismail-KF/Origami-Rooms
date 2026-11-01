export function crearElementoHotel(hotel) {
    const numeroEstrellas = '⭐'.repeat(hotel.estrellas);

    return `
        <a href="hotelDetalle.html?id=${hotel.id}" class="card mb-3 text-decoration-none text-dark shadow-sm">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <h5 class="card-title mb-0">${hotel.nombre}</h5>
                    <span class="fs-5">${numeroEstrellas}</span>
                </div>
                <p class="text-muted small mt-1"><i class="bi bi-geo-alt"></i> ${hotel.direccion} (${hotel.region})</p>
                
                <p class="card-text mt-3 text-truncate" style="max-height: 3rem;">
                    ${hotel.descripcion}
                </p>
                
                <div class="row mt-3 border-top pt-3">
                    <div class="col-md-6">
                        <strong>Servicios:</strong>
                        <ul class="list-unstyled mb-0 small">
                            ${hotel.tieneRestaurante ? '<li>🍽️ Restaurante</li>' : ''}
                            ${hotel.tieneGimnasio ? '<li>Gimnasio</li>' : ''}
                            ${hotel.tieneServicioHabitaciones ? '<li>Servicio Habitaciones</li>' : ''}
                        </ul>
                    </div>
                    <div class="col-md-6 text-end">
                        <span class="badge bg-success fs-6">Nota: ${hotel.puntuacionMedia} / 10</span>
                    </div>
                </div>
            </div>
        </a>`;
}