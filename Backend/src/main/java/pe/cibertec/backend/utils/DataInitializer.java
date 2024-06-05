    package pe.cibertec.backend.utils;

    import jakarta.annotation.PostConstruct;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    import pe.cibertec.backend.models.Monto;
    import pe.cibertec.backend.models.Rol;
    import pe.cibertec.backend.models.Ubicacion;
    import pe.cibertec.backend.models.Zona;
    import pe.cibertec.backend.repository.MontoRepository;
    import pe.cibertec.backend.repository.RolRepository;
    import pe.cibertec.backend.repository.UbicacionRepository;
    import pe.cibertec.backend.repository.ZonaRepository;

    @Component
    public class DataInitializer {
        @Autowired
        private RolRepository rolRepository;

        @Autowired
        private UbicacionRepository ubicacionRepository;

        @Autowired
        private ZonaRepository zonaRepository;

        @Autowired
        private MontoRepository montoRepository;

        @PostConstruct
        public void initData() {
            // Insertar roles si la tabla está vacía
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol("Inversionista"));
                rolRepository.save(new Rol("JefePrestamista"));
                rolRepository.save(new Rol("Prestamista"));
                rolRepository.save(new Rol("Prestatario"));
                rolRepository.save(new Rol("Administrador"));
            }


            if (montoRepository.count() == 0) {
                montoRepository.save(new Monto(150, 0));
                montoRepository.save(new Monto(200, 0));
                montoRepository.save(new Monto(300, 0));
                montoRepository.save(new Monto(400, 0));
                montoRepository.save(new Monto(500, 0));
                //
                montoRepository.save(new Monto(154.11, 15));
                montoRepository.save(new Monto(205.49, 15));
                montoRepository.save(new Monto(308.23, 15));
                montoRepository.save(new Monto(410.98, 15));
                montoRepository.save(new Monto(513.72, 15));
                //
                montoRepository.save(new Monto(155.49, 20));
                montoRepository.save(new Monto(207.32, 20));
                montoRepository.save(new Monto(310.98, 20));
                montoRepository.save(new Monto(414.64, 20));
                montoRepository.save(new Monto(518.30, 20));
                //
                montoRepository.save(new Monto(156.86, 25));
                montoRepository.save(new Monto(209.15, 25));
                montoRepository.save(new Monto(313.72, 25));
                montoRepository.save(new Monto(418.30, 25));
                montoRepository.save(new Monto(522.88, 25));
                //
                montoRepository.save(new Monto(157.23, 30));
                montoRepository.save(new Monto(210.98, 30));
                montoRepository.save(new Monto(316.47, 30));
                montoRepository.save(new Monto(421.96, 30));
                montoRepository.save(new Monto(527.45, 30));
                //
                montoRepository.save(new Monto(159.61, 35));
                montoRepository.save(new Monto(212.81, 35));
                montoRepository.save(new Monto(319.22, 35));
                montoRepository.save(new Monto(425.62, 35));
                montoRepository.save(new Monto(532.03, 35));
            }


            // Insertar ubicaciones si la tabla está vacía
            if (ubicacionRepository.count() == 0) {
                ubicacionRepository.save(new Ubicacion("Cercado Lima", "Cercado Lima"));
                ubicacionRepository.save(new Ubicacion("Zarate", "Zarate"));
                ubicacionRepository.save(new Ubicacion("Cono norte", "Cono norte"));
                ubicacionRepository.save(new Ubicacion("Miraflores", "Miraflores"));
                ubicacionRepository.save(new Ubicacion("San Isidro", "San Isidro"));
                ubicacionRepository.save(new Ubicacion("Barranco", "Barranco"));
                ubicacionRepository.save(new Ubicacion("Surco", "Surco"));
                ubicacionRepository.save(new Ubicacion("San Borja", "San Borja"));
                ubicacionRepository.save(new Ubicacion("La Molina", "La Molina"));
                ubicacionRepository.save(new Ubicacion("Callao", "Callao"));
                ubicacionRepository.save(new Ubicacion("Lince", "Lince"));
                ubicacionRepository.save(new Ubicacion("Pueblo Libre", "Pueblo Libre"));
                ubicacionRepository.save(new Ubicacion("Magdalena del Mar", "Magdalena del Mar"));
            }

            // Insertar zonas si la tabla está vacía y las ubicaciones están disponibles
            if (zonaRepository.count() == 0) {
                insertZonas();
            }

        }

        private void insertZonas() {
            insertZona("Zona 1 Cercado", "Cercado Lima");
            insertZona("Zona 2 Cercado", "Cercado Lima");
            insertZona("Zona 3 Cercado", "Cercado Lima");
            insertZona("Zona 1 Zarate", "Zarate");
            insertZona("Zona 2 Zarate", "Zarate");
            insertZona("Zona 3 Zarate", "Zarate");
            insertZona("Zona 1 Cono Norte", "Cono norte");
            insertZona("Zona 2 Cono Norte", "Cono norte");
            insertZona("Zona 3 Cono Norte", "Cono norte");
            insertZona("Zona 4 Cono Norte", "Cono norte");
            insertZona("Zona 5 Cono Norte", "Cono norte");
            insertZona("Zona 1 Miraflores", "Miraflores");
            insertZona("Zona 2 Miraflores", "Miraflores");
            insertZona("Zona 3 Miraflores", "Miraflores");
            insertZona("Zona 1 San Isidro", "San Isidro");
            insertZona("Zona 2 San Isidro", "San Isidro");
            insertZona("Zona 3 San Isidro", "San Isidro");
            insertZona("Zona 1 Barranco", "Barranco");
            insertZona("Zona 2 Barranco", "Barranco");
            insertZona("Zona 3 Barranco", "Barranco");
            insertZona("Zona 1 Surco", "Surco");
            insertZona("Zona 2 Surco", "Surco");
            insertZona("Zona 3 Surco", "Surco");
            insertZona("Zona 1 San Borja", "San Borja");
            insertZona("Zona 2 San Borja", "San Borja");
            insertZona("Zona 3 San Borja", "San Borja");
            insertZona("Zona 4 San Borja", "San Borja");
            insertZona("Zona 1 La Molina", "La Molina");
            insertZona("Zona 2 La Molina", "La Molina");
            insertZona("Zona 3 La Molina", "La Molina");
            insertZona("Zona 4 La Molina", "La Molina");
            insertZona("Zona 1 Callao", "Callao");
            insertZona("Zona 2 Callao", "Callao");
            insertZona("Zona 3 Callao", "Callao");
            insertZona("Zona 4 Callao", "Callao");
            insertZona("Zona 1 Lince", "Lince");
            insertZona("Zona 2 Lince", "Lince");
            insertZona("Zona 3 Lince", "Lince");
            insertZona("Zona 1 Pueblo Libre", "Pueblo Libre");
            insertZona("Zona 2 Pueblo Libre", "Pueblo Libre");
            insertZona("Zona 3 Pueblo Libre", "Pueblo Libre");
            insertZona("Zona 4 Pueblo Libre", "Pueblo Libre");
            insertZona("Zona 1 Magdalena", "Magdalena del Mar");
            insertZona("Zona 2 Magdalena", "Magdalena del Mar");
            insertZona("Zona 3 Magdalena", "Magdalena del Mar");
            insertZona("Zona 4 Magdalena", "Magdalena del Mar");
            insertZona("Zona 5 Magdalena", "Magdalena del Mar");
        }

        private void insertZona(String zonaNombre, String ubicacionNombre) {
            Ubicacion ubicacion = ubicacionRepository.findByNombre(ubicacionNombre);
            if (ubicacion != null) {
                Zona zona = new Zona();
                zona.setNombre(zonaNombre);
                zona.setUbicacion(ubicacion);
                zonaRepository.save(zona);
            } else {
                System.out.println("Ubicación no encontrada: " + ubicacionNombre);
            }
        }


    }
