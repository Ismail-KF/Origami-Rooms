import { crearBarraNavegacion } from '../components/navbar.js';
import { establecimientoAPI } from '../api/establecimientoAPI.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const root = document.getElementById('root');

    root.innerHTML = `
        <div class="bg-dark text-white text-center py-5 mb-5" id="banner">
            <div class="container">
                <h1 class="display-4 fw-bold">Gestión de Origami Rooms</h1>
                <h2 class="display-4 fw-bold">折紙ルームズの経営</h2>
                <p class="lead text-white-50">Lleve a cabo la gestión de Origami Rooms</p>

            </div>
        </div>

        <div class="container mb-5">
            <h2 class="mb-4 border-bottom pb-2">Lista de Alojamientos</h2>
            <a href="crearEstablecimiento.html" class="btn btn-primary">Crear establecimiento</a>
            <br>
            <br>
            <div id="listaEstablecimientos" class="row g-4">
                <div class="col-12 text-center py-5">
                    <div class="spinner-border text-primary" role="status">
                    </div>
                </div>
            </div>
        </div>
    `;

    const contenedorLista = document.getElementById('listaEstablecimientos');

    try {
        const establecimientos = await establecimientoAPI.mostrarTodos();

        contenedorLista.innerHTML = '';

        if (establecimientos && establecimientos.length > 0) {
            establecimientos.forEach(est => {
                const nombre = est.nombre;
                const ubicacion = est.region;
                const descripcion = est.descripcion;
                const puntuacion = est.puntuacionMedia ? `${est.puntuacionMedia.toFixed(1)}/5` : 'S/P';
                const tipoBadge = est.tipo === 'hotel' ? 'bg-primary' : 'bg-danger';
                const tipoTexto = est.tipo ? est.tipo.toUpperCase() : 'ALOJAMIENTO';
                const imagenUrl = est.tipo === 'hotel' ?
                    'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.ampersandtravel.com%2Fmedia%2F538575%2FJAPAN-Upgrade-rooms-at-the-Peninsula-Hotel-in-Tokyo-offer-some-of-the-best-views-of-the-Imperial-Palace-grounds.jpg%3Fmode%3Dpad%26quality%3D80%26width%3D2000%26rnd%3D130598363000000000&f=1&nofb=1&ipt=6d4a7b11c114f995ca4f4bde8e8028af253e93fb9ec5ebe343129f4f78845008'
                    : 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Furbanpixxels.com%2Fwp-content%2Fuploads%2F14-11376-post%2FKoyoto-Ryokan-Hiiragiya-Traditional-Wing.jpg&f=1&nofb=1&ipt=6a31822ca17496e3208ac23efc41850c30fa4dda074db82e7b5211155356455b';


                contenedorLista.innerHTML += `
                    <div class="col-md-6 col-lg-4">
                        <div class="card h-100 shadow-sm border-0">
                            <img src="${imagenUrl}" class="card-img-top" alt="${nombre}" style="height: 200px; object-fit: cover;">
                            
                            <div class="card-body d-flex flex-column">
                                <div class="mb-2">
                                    <span class="badge ${tipoBadge} mb-2">${tipoTexto}</span>
                                    <div class="d-flex justify-content-between align-items-start">
                                        <h5 class="card-title fw-bold mb-0">${nombre}</h5>
                                        <span class="badge bg-warning text-dark">⭐ ${puntuacion}</span>
                                    </div>
                                </div>
                                
                                <p class="text-muted small mb-3">Región de ${ubicacion}</p>
                                
                                <p class="card-text text-secondary mb-4" style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden;">
                                    ${descripcion}
                                </p>
                                
                                <div class="mt-auto">
                                    <a href="establecimientoDetalle.html?id=${est.id}" class="btn btn-dark w-100 fw-bold">
                                        Ver Detalles y Habitaciones
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            });
        } else {
            contenedorLista.innerHTML = `
                <div class="col-12 text-center py-5">
                    <h4 class="text-muted">Aún no hay establecimientos registrados en el sistema.</h4>
                </div>
            `;
        }

    } catch (error) {
        alert(`Error al cargar los establecimientos: ${error.message}`);;
        contenedorLista.innerHTML = `
            <div class="col-12">
                <div class="alert alert-danger text-center shadow-sm" role="alert">
                    <h5 class="alert-heading">Error de conexión</h5>
                    <p class="mb-0">No se pudo contactar con el servidor.</p>
                </div>
            </div>
        `;
    }
});