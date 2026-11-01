import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoHabitacion } from '../components/habitacionComponente.js';
import { habitacionAPI } from '../api/habitacionAPI.js';

let todasLasHabitaciones = [];

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="container mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1>Catálogo de Habitaciones</h1>
                <a href="asignarHabitacion.html" class="btn btn-primary">Nueva Habitación</a>
            </div>

            <div class="card shadow-sm mb-4 border-0 bg-light">
                <div class="card-body">
                    <h5 class="card-title text-secondary mb-3">Buscar y Filtrar</h5>
                    <form id="formularioFiltros" class="row g-3">
                        
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Tipo</label>
                            <select id="filtroTipo" class="form-select">
                                <option value="">Todos los tipos</option>
                                <option value="INDIVIDUAL">Individual</option>
                                <option value="DOBLE">Doble</option>
                                <option value="TRIPLE">Triple</option>
                                <option value="ECONOMICA">Económica</option>
                                <option value="SUITE">Suite</option>
                            </select>
                        </div>
                        
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Capacidad Mínima</label>
                            <input type="number" id="filtroCapacidad" class="form-control" min="1" placeholder="Ej: 2 personas">
                        </div>
                        
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Precio Máximo (€)</label>
                            <input type="number" id="filtroPrecio" class="form-control" min="0" placeholder="Ej: 150">
                        </div>
                        
                        <div class="col-md-3 d-flex align-items-end">
                            <button type="button" id="btnLimpiarFiltros" class="btn btn-outline-secondary w-100">
                                Limpiar Filtros
                            </button>
                        </div>
                        
                    </form>
                </div>
            </div>

            <div id="listaHabitaciones" class="row g-4"></div>
        </div>
    `;

    try {
        todasLasHabitaciones = await habitacionAPI.mostrarTodas();

        mostrarHabitaciones(todasLasHabitaciones);

        document.getElementById('filtroTipo').addEventListener('change', aplicarFiltros);
        document.getElementById('filtroCapacidad').addEventListener('input', aplicarFiltros);
        document.getElementById('filtroPrecio').addEventListener('input', aplicarFiltros);

        document.getElementById('btnLimpiarFiltros').addEventListener('click', () => {
            document.getElementById('formularioFiltros').reset();
            mostrarHabitaciones(todasLasHabitaciones);
        });

    } catch (error) {
        alert(`Error al cargar las habitaciones: ${error.message}`);
        document.getElementById('listaHabitaciones').innerHTML = `
            <div class="col-12">
                <div class="alert alert-danger shadow-sm">
                    Error al conectar con el servidor.
                </div>
            </div>
        `;
    }
});

function aplicarFiltros() {
    const tipoBuscado = document.getElementById('filtroTipo').value;
    const capacidadMinima = parseInt(document.getElementById('filtroCapacidad').value) || 0;
    const precioMaximo = parseFloat(document.getElementById('filtroPrecio').value) || Infinity;
    const habitacionesFiltradas = todasLasHabitaciones.filter(habitacion => {
        const cumpleTipo = tipoBuscado === "" || habitacion.tipoHabitacion === tipoBuscado;
        const cumpleCapacidad = habitacion.capacidad >= capacidadMinima;
        const cumplePrecio = habitacion.precioPorNoche <= precioMaximo;

        return cumpleTipo && cumpleCapacidad && cumplePrecio;
    });

    mostrarHabitaciones(habitacionesFiltradas);
}

function mostrarHabitaciones(arrayHabitaciones) {
    const contenedor = document.getElementById('listaHabitaciones');
    contenedor.innerHTML = '';

    if (arrayHabitaciones.length === 0) {
        contenedor.innerHTML = `
            <div class="col-12">
                <div class="alert alert-warning text-center shadow-sm">
                    No hay ninguna habitación con esas características.
                </div>
            </div>`;
        return;
    }

    arrayHabitaciones.forEach(habitacion => {
        contenedor.innerHTML += crearElementoHabitacion(habitacion);
    });
}