import { Zona } from "./Zona";

export interface Ubicacion {
    idUbicacion?    : number;
    nombre?         : string;
    direccion?      : string;
    idZona?         : Zona[];
}