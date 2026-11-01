import { crearBarraNavegacion } from '../components/navbar.js';
import { hotelAPI } from '../api/hotelAPI.js';
import { ryokanAPI } from '../api/ryokanAPI.js';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4 mb-5" style="max-width: 800px;">
            <div class="card shadow">
                <div class="card-header bg-dark text-white">
                    <h4 class="mb-0">Registrar Nuevo Establecimiento</h4>
                </div>
                <div class="card-body p-4">
                    <form id="formEstablecimiento">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Tipo de Alojamiento</label>
                            <select id="tipoEstablecimiento" class="form-select" required>
                                <option value="" disabled selected>Selecciona una opción...</option>
                                <option value="hotel">Hotel Occidental / Moderno</option>
                                <option value="ryokan">Ryokan Tradicional Japonés</option>
                            </select>
                        </div>

                        <div class="row g-3 mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Nombre</label>
                                <input type="text" id="nombre" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Región</label>
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
                            <div class="col-12">
                                <label class="form-label">Dirección</label>
                                <input type="text" id="direccion" class="form-control" required>
                            </div>
                            <div class="col-12">
                                <label class="form-label">Descripción</label>
                                <textarea id="descripcion" class="form-control" rows="3" required></textarea>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Estrellas / Categoría</label>
                                <input type="number" id="estrellas" class="form-control" min="1" max="5" required>
                            </div>
                        </div>

                        <div id="bloqueHotel" class="d-none card bg-light p-3 mb-3 border-0">
                            <h5 class="text-primary mb-3">Servicios de Hotel</h5>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="tieneGimnasio">
                                <label class="form-check-label">¿Tiene Gimnasio?</label>
                            </div>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="tieneRestaurante">
                                <label class="form-check-label">¿Tiene Restaurante Interno?</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="tieneServicioHabitaciones">
                                <label class="form-check-label">¿Tiene Servicio de Habitaciones 24h?</label>
                            </div>
                        </div>

                        <div id="bloqueRyokan" class="d-none card bg-light p-3 mb-3 border-0">
                            <h5 class="text-danger mb-3">Servicios Tradicionales de Ryokan</h5>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="tieneOnsen">
                                <label class="form-check-label">¿Tiene Aguas Termales (Onsen)?</label>
                            </div>
                            <div class="form-check mb-2">
                                <input class="form-check-input" type="checkbox" id="tieneKimono">
                                <label class="form-check-label">¿Ofrece Servicio de Yukata / Kimono?</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="tieneJardin">
                                <label class="form-check-label">¿Tiene Jardín Zen / Tradicional?</label>
                            </div>
                        </div>

                        <div class="mt-4 text-end">
                            <a href="index.html" class="btn btn-secondary me-2">Cancelar</a>
                            <button type="submit" class="btn btn-dark">Guardar Alojamiento</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;

    const selectorTipo = document.getElementById('tipoEstablecimiento');
    const bloqueHotel = document.getElementById('bloqueHotel');
    const bloqueRyokan = document.getElementById('bloqueRyokan');

    selectorTipo.addEventListener('change', (evento) => {
        if (evento.target.value === 'hotel') {
            bloqueHotel.classList.remove('d-none');
            bloqueRyokan.classList.add('d-none');
        } else if (evento.target.value === 'ryokan') {
            bloqueRyokan.classList.remove('d-none');
            bloqueHotel.classList.add('d-none');
        }
    });

    document.getElementById('formEstablecimiento').addEventListener('submit', async (evento) => {
        evento.preventDefault();

        const tipo = selectorTipo.value;

        const datosBase = {
            nombre: document.getElementById('nombre').value,
            region: document.getElementById('region').value,
            direccion: document.getElementById('direccion').value,
            descripcion: document.getElementById('descripcion').value,
            estrellas: parseInt(document.getElementById('estrellas').value, 10),
            puntuaciones: [],
            puntuacionMedia: 0.0
        };

        try {
            if (tipo === 'hotel') {
                const datosHotel = {
                    ...datosBase,
                    tipo: 'hotel',
                    tieneGimnasio: document.getElementById('tieneGimnasio').checked,
                    tieneRestaurante: document.getElementById('tieneRestaurante').checked,
                    tieneServicioHabitaciones: document.getElementById('tieneServicioHabitaciones').checked
                };
                await hotelAPI.crearHotel(datosHotel);
            } else if (tipo === 'ryokan') {
                const datosRyokan = {
                    ...datosBase,
                    tipo: 'ryokan',
                    tieneOnsen: document.getElementById('tieneOnsen').checked,
                    tieneKimono: document.getElementById('tieneKimono').checked,
                    tieneJardin: document.getElementById('tieneJardin').checked
                };
                await ryokanAPI.crearRyokan(datosRyokan);
            }

            alert('¡Establecimiento registrado con éxito!');
            window.location.href = 'index.html';
        } catch (error) {
            alert(`Hubo un error al guardar: ${error.message}`);;
        }
    });
});