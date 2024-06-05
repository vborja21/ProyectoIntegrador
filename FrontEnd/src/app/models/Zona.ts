import { Ubicacion } from "./Ubicacion";

export interface Zona {
    idZona?     : number;
    nombre?     : string;
    prestamo?   : any[];
    ubicacion?  : Ubicacion[];
}