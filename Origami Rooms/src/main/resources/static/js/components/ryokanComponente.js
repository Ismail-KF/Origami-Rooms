export function crearElementoRyokan(ryokan) {
    const numeroEstrellas = '⭐'.repeat(ryokan.estrellas);

    return `
        <a href="ryokanDetalle.html?id=${ryokan.id}" class="card mb-3 text-decoration-none text-dark shadow-sm border-success">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <h5 class="card-title mb-0 text-success">
                        ${ryokan.nombre} <span class="badge bg-success bg-opacity-75 fs-6 ms-2">Ryokan</span>
                    </h5>
                    <span class="fs-5">${numeroEstrellas}</span>
                </div>
                <p class="text-muted small mt-1"><i class="bi bi-geo-alt"></i> ${ryokan.direccion} (${ryokan.region})</p>
                
                <p class="card-text mt-3 text-truncate" style="max-height: 3rem;">
                    ${ryokan.descripcion}
                </p>
                
                <div class="row mt-3 border-top pt-3">
                    <div class="col-md-7">
                        <strong>Experiencia Tradicional:</strong>
                        <ul class="list-unstyled mb-0 small mt-1">
                            ${ryokan.tieneOnsen ? '<li>Baños termales (Onsen)</li>' : ''}
                            ${ryokan.tieneKimono ? '<li>Servicio de Yukata/Kimono</li>' : ''}
                            ${ryokan.tieneJardin ? '<li>Jardín Tradicional Zen</li>' : ''}
                        </ul>
                    </div>
                    <div class="col-md-5 text-end d-flex flex-column justify-content-end">
                        <span class="badge bg-success fs-6">Nota: ${ryokan.puntuacionMedia} / 10</span>
                    </div>
                </div>
            </div>
        </a>`;
}