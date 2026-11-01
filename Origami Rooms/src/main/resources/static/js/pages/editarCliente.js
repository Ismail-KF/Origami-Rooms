import { crearBarraNavegacion } from '../components/navbar.js';
import { clienteAPI } from '../api/clienteAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');
    const urlParams = new URLSearchParams(window.location.search);
    const clienteId = urlParams.get('id');

    if (!clienteId) {
        alert('Error: No se ha proporcionado un ID de cliente valido.');
        window.location.href = 'clienteLista.html';
        return;
    }

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 750px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-warning text-dark">
                    <h4 class="mb-0">Modificar Datos del Cliente</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioCliente">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label for="nombre" class="form-label fw-bold">Nombre</label>
                                <input type="text" id="nombre" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label for="apellido" class="form-label fw-bold">Apellido</label>
                                <input type="text" id="apellido" class="form-control" required>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="pasaporte" class="form-label fw-bold">Pasaporte / DNI</label>
                                <input type="text" id="pasaporte" class="form-control" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="nacionalidad" class="form-label fw-bold">Nacionalidad</label>
                                <input type="text" id="nacionalidad" class="form-control" required>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="telefono" class="form-label fw-bold">Telefono</label>
                                <input type="tel" id="telefono" class="form-control" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="email" class="form-label fw-bold">Correo Electronico</label>
                                <input type="email" id="email" class="form-control" required>
                            </div>

                            <div class="col-12 mt-5 d-flex justify-content-between align-items-center">
                                <div>
                                    <a href="clienteLista.html" class="btn btn-outline-secondary me-2">Cancelar</a>
                                    <button type="submit" class="btn btn-warning text-dark fw-bold">Guardar Cambios</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    try {
        const cliente = await clienteAPI.buscarPorId(clienteId);

        document.getElementById('nombre').value = cliente.nombre || '';
        document.getElementById('apellido').value = cliente.apellido || '';
        document.getElementById('pasaporte').value = cliente.pasaporte || '';
        document.getElementById('nacionalidad').value = cliente.nacionalidad || '';
        document.getElementById('telefono').value = cliente.telefono || '';
        document.getElementById('email').value = cliente.email || '';
    } catch (error) {
        console.error('Error al cargar datos del cliente:', error);
        alert('No se pudieron recuperar los datos del cliente solicitado.');
        window.location.href = 'clienteLista.html';
        return;
    }

    document.getElementById('formularioCliente').addEventListener('submit', async (event) => {
        event.preventDefault();

        const clienteActualizado = {
            nombre: document.getElementById('nombre').value.trim(),
            apellido: document.getElementById('apellido').value.trim(),
            pasaporte: document.getElementById('pasaporte').value.trim(),
            nacionalidad: document.getElementById('nacionalidad').value.trim(),
            telefono: document.getElementById('telefono').value.trim(),
            email: document.getElementById('email').value.trim()
        };

        try {
            await clienteAPI.actualizarClienteParcialmente(clienteId, clienteActualizado);
            alert('Los datos del cliente se han actualizado correctamente.');
            window.location.href = 'clienteLista.html';
        } catch (error) {
            alert(`Error al actualizar el cliente: ${error.message}`);
        }
    });
});