import { crearBarraNavegacion } from '../components/navbar.js';
import { clienteAPI } from '../api/clienteAPI.js';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 750px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-success text-white">
                    <h4 class="mb-0">Registrar Nuevo Cliente</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formularioCliente">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label for="nombre" class="form-label fw-bold">Nombre</label>
                                <input type="text" id="nombre" class="form-control" placeholder="Ej. Taro" required>
                            </div>
                            <div class="col-md-6">
                                <label for="apellido" class="form-label fw-bold">Apellido</label>
                                <input type="text" id="apellido" class="form-control" placeholder="Ej. Tanaka" required>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="pasaporte" class="form-label fw-bold">Pasaporte</label>
                                <input type="text" id="pasaporte" class="form-control" placeholder="Ej. AAA123456" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="nacionalidad" class="form-label fw-bold">Nacionalidad</label>
                                <input type="text" id="nacionalidad" class="form-control" placeholder="Ej. Japón, España, México" required>
                            </div>

                            <div class="col-md-6 mt-4">
                                <label for="telefono" class="form-label fw-bold">Número de Teléfono</label>
                                <input type="tel" id="telefono" class="form-control" placeholder="Ej. [Prefijo]612345678" required>
                            </div>
                            <div class="col-md-6 mt-4">
                                <label for="email" class="form-label fw-bold">Correo Electrónico</label>
                                <input type="email" id="email" class="form-control" placeholder="usuario@correo.com" required>
                            </div>

                            <div class="col-12 mt-5 text-end">
                                <a href="clienteLista.html" class="btn btn-outline-secondary me-2">Cancelar</a>
                                <button type="submit" class="btn btn-success">Guardar Cliente</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    document.getElementById('formularioCliente').addEventListener('submit', async (event) => {
        event.preventDefault();

        const nuevoCliente = {
            nombre: document.getElementById('nombre').value.trim(),
            apellido: document.getElementById('apellido').value.trim(),
            pasaporte: document.getElementById('pasaporte').value.trim(),
            nacionalidad: document.getElementById('nacionalidad').value.trim(),
            telefono: document.getElementById('telefono').value.trim(),
            email: document.getElementById('email').value.trim()
        };

        try {
            await clienteAPI.crearCliente(nuevoCliente);

            alert('¡Cliente registrado con éxito!');
            window.location.href = 'clienteLista.html';

        } catch (error) {
            alert(`Error al registrar cliente: ${error.message}`);
        }
    });
});