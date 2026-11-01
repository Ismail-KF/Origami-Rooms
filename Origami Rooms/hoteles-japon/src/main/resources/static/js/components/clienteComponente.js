export function crearElementoCliente(cliente) {
    const iniciales = (cliente.nombre.charAt(0) + cliente.apellido.charAt(0)).toUpperCase();

    return `
        <a href="clienteDetalle.html?id=${cliente.id}" class="card mb-3 text-decoration-none text-dark shadow-sm">
            <div class="card-body">
                <div class="d-flex align-items-center">
                    <div class="bg-primary text-white rounded-circle d-flex justify-content-center align-items-center me-3 shadow-sm" style="width: 55px; height: 55px; font-size: 1.5rem; flex-shrink: 0;">
                        ${iniciales}
                    </div>
                    
                    <div class="flex-grow-1">
                        <h5 class="card-title mb-0">${cliente.nombre} ${cliente.apellido}</h5>
                        <p class="card-text text-muted small mb-0 mt-1">${cliente.email}</p>
                    </div>
                </div>
                
                <div class="row mt-3 border-top pt-2 small text-muted">
                    <div class="col-6">
                        <strong>Teléfono:</strong> ${cliente.telefono}<br>
                        <strong>Nacionalidad:</strong> ${cliente.nacionalidad}
                    </div>
                    <div class="col-6 text-end">
                        <strong>Pasaporte:</strong><br> ${cliente.pasaporte}
                    </div>
                </div>
            </div>
        </a>`;
}