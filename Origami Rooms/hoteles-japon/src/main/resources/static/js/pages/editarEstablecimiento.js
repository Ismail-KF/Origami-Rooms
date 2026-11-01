import { crearBarraNavegacion } from '../components/navbar.js';
import { hotelAPI } from '../api/hotelAPI.js';
import { ryokanAPI } from '../api/ryokanAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    const tipo = urlParams.get('tipo');

    if (!id || !tipo) {
        alert('Error: No se ha proporcionado un ID o un tipo de establecimiento valido.');
        window.location.href = 'index.html';
        return;
    }

    let camposEspecificosHTML = '';
    if (tipo === 'hotel') {
        camposEspecificosHTML = `
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneGimnasio">
                    <label class="form-check-label fw-bold" for="tieneGimnasio">Tiene Gimnasio</label>
                </div>
            </div>
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneRestaurante">
                    <label class="form-check-label fw-bold" for="tieneRestaurante">Tiene Restaurante</label>
                </div>
            </div>
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneServicioHabitaciones">
                    <label class="form-check-label fw-bold" for="tieneServicioHabitaciones">Servicio de Habitaciones</label>
                </div>
            </div>
        `;
    } else if (tipo === 'ryokan') {
        camposEspecificosHTML = `
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneOnsen">
                    <label class="form-check-label fw-bold" for="tieneOnsen">Tiene Onsen</label>
                </div>
            </div>
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneKimono">
                    <label class="form-check-label fw-bold" for="tieneKimono">Servicio de Kimono</label>
                </div>
            </div>
            <div class="col-md-4 mt-4">
                <div class="form-check mt-2">
                    <input class="form-check-input" type="checkbox" id="tieneJardin">
                    <label class="form-check-label fw-bold" for="tieneJardin">Jardin Tradicional</label>
                </div>
            </div>
        `;
    }

    const tituloFormulario = tipo === 'hotel' ? 'Modificar Hotel' : 'Modificar Ryokan';

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 800px;">
            <div class="card shadow-sm border-0">
                <div class="card-header bg-warning text-dark">
                    <h4 class="mb-0">${tituloFormulario}</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formEstablecimiento">
                        <div class="row g-3">
                            
                            <div class="col-md-6">
                                <label for="nombre" class="form-label fw-bold">Nombre del Establecimiento</label>
                                <input type="text" id="nombre" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label for="region" class="form-label fw-bold">Region</label>
                                <select id="region" class="form-select" required>
                                    <option value="HOKKAIDOU">Hokkaido</option>
                                    <option value="TOHOKU">Tohoku</option>
                                    <option value="KANTOU">Kanto</option>
                                    <option value="CHUUGOKU">Chugoku</option>
                                    <option value="SHIKOKU">Shikoku</option>
                                    <option value="KANSAI">Kansai</option>
                                    <option value="KYUUSHUU">Kyushu</option>
                                    <option value="CHUUBU">Chubu</option>
                                </select>
                            </div>
                            <div class="col-md-9 mt-4">
                                <label for="direccion" class="form-label fw-bold">Direccion Exacta</label>
                                <input type="text" id="direccion" class="form-control" required>
                            </div>
                            <div class="col-md-3 mt-4">
                                <label for="estrellas" class="form-label fw-bold">Estrellas</label>
                                <input type="number" id="estrellas" class="form-control" min="1" max="5" required>
                            </div>
                            <div class="col-12 mt-4">
                                <label for="descripcion" class="form-label fw-bold">Descripcion</label>
                                <textarea id="descripcion" class="form-control" rows="4" required></textarea>
                            </div>

                            ${camposEspecificosHTML}

                            <div class="col-12 mt-5 d-flex justify-content-between align-items-center">
                                <div>
                                    <a href="index.html" class="btn btn-outline-secondary me-2">Cancelar</a>
                                    <button type="submit" class="btn btn-warning text-dark fw-bold">Guardar Cambios</button>
                                </div>
                            </div>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    const apiSeleccionada = tipo === 'hotel' ? hotelAPI : ryokanAPI;
    let establecimientoOriginal = null;

    try {
        establecimientoOriginal = await apiSeleccionada.buscarPorId(id);

        document.getElementById('nombre').value = establecimientoOriginal.nombre || '';
        document.getElementById('region').value = establecimientoOriginal.region || '';
        document.getElementById('direccion').value = establecimientoOriginal.direccion || '';
        document.getElementById('estrellas').value = establecimientoOriginal.estrellas || '';
        document.getElementById('descripcion').value = establecimientoOriginal.descripcion || '';

        if (tipo === 'hotel') {
            document.getElementById('tieneGimnasio').checked = establecimientoOriginal.tieneGimnasio || false;
            document.getElementById('tieneRestaurante').checked = establecimientoOriginal.tieneRestaurante || false;
            document.getElementById('tieneServicioHabitaciones').checked = establecimientoOriginal.tieneServicioHabitaciones || false;
        } else if (tipo === 'ryokan') {
            document.getElementById('tieneOnsen').checked = establecimientoOriginal.tieneOnsen || false;
            document.getElementById('tieneKimono').checked = establecimientoOriginal.tieneKimono || false;
            document.getElementById('tieneJardin').checked = establecimientoOriginal.tieneJardin || false;
        }

    } catch (error) {
        console.error('Error al cargar la informacion del establecimiento:', error);
        alert('No se pudieron recuperar los datos del establecimiento especificado.');
        window.location.href = 'index.html';
        return;
    }

    document.getElementById('formEstablecimiento').addEventListener('submit', async (evento) => {
        evento.preventDefault();

        const datosActualizados = {
            nombre: document.getElementById('nombre').value.trim(),
            region: document.getElementById('region').value,
            direccion: document.getElementById('direccion').value.trim(),
            descripcion: document.getElementById('descripcion').value.trim(),
            estrellas: parseInt(document.getElementById('estrellas').value, 10),
            puntuaciones: establecimientoOriginal.puntuaciones || [],
            puntuacionMedia: establecimientoOriginal.puntuacionMedia || 0.0,
            tipo: tipo
        };

        if (tipo === 'hotel') {
            datosActualizados.tieneGimnasio = document.getElementById('tieneGimnasio').checked;
            datosActualizados.tieneRestaurante = document.getElementById('tieneRestaurante').checked;
            datosActualizados.tieneServicioHabitaciones = document.getElementById('tieneServicioHabitaciones').checked;
        } else if (tipo === 'ryokan') {
            datosActualizados.tieneOnsen = document.getElementById('tieneOnsen').checked;
            datosActualizados.tieneKimono = document.getElementById('tieneKimono').checked;
            datosActualizados.tieneJardin = document.getElementById('tieneJardin').checked;
        }

        try {
            await apiSeleccionada.actualizarParcialmente(id, datosActualizados);
            alert('El establecimiento ha sido actualizado correctamente.');
            window.location.href = 'index.html';
        } catch (error) {
            alert(`Error al actualizar el establecimiento: ${error.message}`);
        }
    });
});